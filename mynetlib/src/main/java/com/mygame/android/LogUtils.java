package com.mygame.android;

import android.util.Log;

/**
 * 调试工具
 * @author  
 *
 */
public class LogUtils {

	public static boolean debug = true;

	public static int e(String tag, String msg) {
		if (debug)
			return Log.e(tag, msg);
		return 0;
	}
	
	public static int e(String tag, String msg,Throwable tr) {
		if (debug)
			return Log.e(tag, msg, tr);
		return 0;
	}

	public static int v(String tag, String msg) {
		if (debug)
			return LogUtils.v(tag, msg);
		return 0;
	}

	public static int i(String tag, String msg) {
		if (debug)
			return LogUtils.i(tag, msg);
		return 0;
	}

	public static int d(String tag, String msg) {
		if (debug)
			return LogUtils.d(tag, msg);
		return 0;
	}

	public static int w(String tag, String msg) {
		if (debug)
			return LogUtils.w(tag, msg);
		return 0;
	}
	
	public static int w(String tag, String msg,Throwable tr) {
		if (debug)
			return Log.w(tag, msg, tr);
		return 0;
	}
 

}
