package org.apache.cordova.plugin.irc;

import java.util.HashMap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
//TODO remove next PluginResult
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CordovaIrc extends CordovaPlugin implements ThreadBridge {
	
	private IrcClient client;
	private static final String CONNECT = "connect";
	private static final String SEND = "send";
	private static final String MESSAGE = "message";
	private CallbackContext callbackContext = null; 
	
	public CordovaIrc () {}

	public IrcClient getIrcClient(){
		if (client == null){
			client = IrcClient.getInstance();
		}
		return client;
	}
	

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (this.callbackContext == null){
			this.callbackContext = callbackContext;	
		}
		if (action.contentEquals(CONNECT)){

			// Initialize the IRC client if not already done
			if (!this.getIrcClient().isInit()){
				try {
					JSONObject obj = args.getJSONObject(0);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put(constants.USERNAME, obj.getString(constants.USERNAME));
					map.put(constants.PASSWORD, obj.getString(constants.PASSWORD));
					map.put(constants.PORT, Integer.valueOf(obj.getString(constants.PORT)));
					map.put(constants.CHANNEL, obj.getString(constants.CHANNEL));
					map.put(constants.SERVER, obj.getString(constants.SERVER));
					this.getIrcClient().init(map, this);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Then start it
			if (!this.getIrcClient().isConnected()) {
				new IrcThread().start();
			}

			return true;

		} else if (action.contentEquals(SEND)) {
			JSONObject obj;
			try {
				obj = args.getJSONObject(0);
				obj.put("type", "send_message");
				this.getIrcClient().message(obj);
				
				return true;

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

        callbackContext.error("error: " + action);
        return false;
	}

	public void message(JSONObject args) {
		PluginResult res = new PluginResult(PluginResult.Status.OK, args);
		res.setKeepCallback(true);
		this.callbackContext.sendPluginResult(res);
	}

	public void message(String args) {
		PluginResult res = new PluginResult(PluginResult.Status.OK, args);
		res.setKeepCallback(true);
		this.callbackContext.sendPluginResult(res);
	}

}
