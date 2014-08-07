package org.apache.cordova.plugin.irc;

import android.os.AsyncTask;
import android.util.Log;

public class IrcThread extends Thread {

	public void run () {
	    Log.d("GO", "Thread run...");
	    IrcClient.getInstance().Connect();
	}

}
