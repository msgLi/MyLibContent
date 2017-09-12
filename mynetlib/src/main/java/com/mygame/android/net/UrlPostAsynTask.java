package com.mygame.android.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.mygame.android.model.Model;
import com.mygame.android.net.handle.INetResponseDataParse;
import com.mygame.android.net.ui.INetPostAsyncProgress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UrlPostAsynTask {

	private Handler handler = null;

	private static UrlPostAsynTask instance;

	private UrlPostAsynTask(){
		handler = new NetPostHandler();
	}

	public static UrlPostAsynTask getInstance(){
		synchronized (UrlPostAsynTask.class){
			if(instance == null){
				instance = new UrlPostAsynTask();
			}
			return instance;
		}
	}

	protected  void sendMessage(int code, NetPostTask task) {
		Message msg = Message.obtain(handler);
		msg.what = code;
		msg.obj = task;
		msg.sendToTarget();
	}

	public void doNetPost(String url, NetRequest request,
			Class<? extends NetResponse> responseClass) {
		if (null == request || null == url
				|| "".equals(url.trim())) {
			return;
		}
		NetPostTask task = new NetPostTask();
		task.url = url;
		task.request = request;
		task.responseClass = responseClass;
		sendMessage(NetConst.HANDLE_MESSAGE_FLAG_START_TASK, task);
		NetPostThread thread = new NetPostThread(task);
		thread.start();
	}

	private  class NetPostThread extends Thread {
		protected NetPostTask task;

		public NetPostThread(NetPostTask task) {
			super();
			this.task = task;
		}

		public void run() {
			String result = null;
			//HttpResponse httpResponse = null;
			String httpparams = task.request.complieGet();

			try {
				URL url = new URL(task.url);
				URLConnection rulConnection = url.openConnection();
				HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
				httpUrlConnection.setUseCaches(false);
				httpUrlConnection.setInstanceFollowRedirects(true);
				httpUrlConnection.setConnectTimeout(10000);
				httpUrlConnection.setReadTimeout(10000);
				httpUrlConnection.setRequestMethod("POST");
				httpUrlConnection.setRequestProperty("connection", "Keep-Alive");
				httpUrlConnection.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
				if(!TextUtils.isEmpty(httpparams)){
					httpUrlConnection.setRequestProperty("Content-Length",httpparams.getBytes().length + "");
				}
				if(task.request instanceof HttpPostHeader){
					Map<String,String> headers = ((HttpPostHeader)task.request).getHeaders();
					if(!headers.isEmpty()){
						Set<String> headerKeys = headers.keySet();
						for(String key : headerKeys){
							if(headers.get(key) != null){
								httpUrlConnection.setRequestProperty(key,headers.get(key));
							}
						}
					}
				}
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.setDoInput(true);
				httpUrlConnection.connect();
				if(!TextUtils.isEmpty(httpparams)){
					OutputStream outStrm = httpUrlConnection.getOutputStream();
					outStrm.write(httpparams.getBytes());
					outStrm.flush();
					outStrm.close();
				}

				InputStream inStrm = httpUrlConnection.getInputStream();

				int responseStatusCode = httpUrlConnection.getResponseCode();
				if (responseStatusCode == 200) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(inStrm));
					String line = null;
					String content = "";
					while((line = reader.readLine()) != null){
						content += line;
					}
					result = content;
					System.out.println("URL:"+task.url);
					System.out.println("result:" + result);
					try {
						INetResponseDataParse dataParse = task.request.dataParseHandle.newInstance();
						task.response = dataParse.responseDataParse(task.request, result, task.responseClass);
						sendMessage(NetConst.HANDLE_MESSAGE_FLAG_200, task);
					} catch (Exception e) {
						e.printStackTrace();
						sendMessage(NetConst.HANDLE_MESSAGE_FLAG_ERROR, task);
					}
				} else if (responseStatusCode == 500) {
					sendMessage(NetConst.HANDLE_MESSAGE_FLAG_500, task);
				} else if (responseStatusCode == 400
						|| responseStatusCode == 404) {
					sendMessage(NetConst.HANDLE_MESSAGE_FLAG_400, task);
				}
				if(inStrm != null){
					inStrm.close();
				}
				httpUrlConnection.disconnect();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				sendMessage(NetConst.HANDLE_MESSAGE_FLAG_ERROR, task);
			} catch (IOException e) {
				e.printStackTrace();
				sendMessage(NetConst.HANDLE_MESSAGE_FLAG_NETWORK_ERROR, task);
			}
		}
	}
}
