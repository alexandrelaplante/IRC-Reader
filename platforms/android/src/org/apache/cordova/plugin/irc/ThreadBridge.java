package org.apache.cordova.plugin.irc;

import java.util.HashMap;

import org.json.JSONObject;

public interface ThreadBridge {
	
	public abstract void message(JSONObject args);
    public abstract void message(String args);

}
