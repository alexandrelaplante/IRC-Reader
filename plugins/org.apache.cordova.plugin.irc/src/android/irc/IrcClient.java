package org.apache.cordova.plugin.irc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.jibble.pircbot.*;
import org.json.JSONException;
import org.json.JSONObject;

public class IrcClient extends PircBot implements ThreadBridge {
	
	private static IrcClient client = null;
	private Boolean isInit = false;
	private String channel;
	private String server;
	private int port;
	private String password;
	private ThreadBridge bridge;
	
	private IrcClient () {
		super();
	}
	
	public static IrcClient getInstance () {
		if (client == null) {
			client = new IrcClient();
		}
		return client;
	}
	
	public Boolean isInit () {
		return this.isInit;
	}
	
	public void init (HashMap<String, Object> map, ThreadBridge callback) {
		this.setName((String) map.get(constants.USERNAME));
		this.server = (String) map.get(constants.SERVER);
		this.port = (Integer) map.get(constants.PORT);
		this.password = (String) map.get(constants.PASSWORD);
		this.channel = (String) map.get(constants.CHANNEL);
		this.bridge = callback;
		this.isInit = true;
	}
	
	public void Connect () {
		try {
			bridge.message("trying to connect...");
			this.connect(this.server);
			this.joinChannel(this.channel);
			bridge.message("connection established");
		} catch (NickAlreadyInUseException e) {
			bridge.message("ERROR: Nick is already in use.");
			e.printStackTrace();
		} catch (IOException e) {
			bridge.message("IOException");
			e.printStackTrace();
		} catch (IrcException e) {
			bridge.message("IrcException");
			e.printStackTrace();
		}
	}
	
	public void sendMessage (String message) {
		if (this.isConnected()){
			this.sendMessage(this.channel, message);
		}
	}
	
	public void onMessage(String channel,
            String sender,
            String login,
            String hostname,
            String message) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("type", "message");
			obj.put("channel", channel);
			obj.put("login", login);
			obj.put("sender", sender);
			obj.put("hostname", hostname);
			obj.put("message", message);
			bridge.message(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void message(JSONObject args) {
		try {
			if (args.getString("type").equalsIgnoreCase("send_message")){
				this.sendMessage(args.getString("contents"));
			}
		} catch (Exception ex){
			//TODO
			ex.printStackTrace();
		}
	}

	public void message(String msg){
		// not used
	}
}
