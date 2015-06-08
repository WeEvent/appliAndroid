package nf28.weevent.Controller.Gcm;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShareExternalServer {

	public String SendNotification(final ArrayList<String> list_contact, String event_name, String event_creator) {

		String result = "";
		Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("nbr_invitation",String.valueOf(list_contact.size()));

        for(int i=0; i<list_contact.size(); i++){
            paramsMap.put("login"+i,list_contact.get(i));
        }
        paramsMap.put("event_creator",event_creator);
        paramsMap.put("event_name",event_name);

		try {
			URL serverUrl = null;
			try {
				serverUrl = new URL(Config.APP_SERVER_URL_SEND_NOTIF);
			} catch (MalformedURLException e) {
				Log.e("AppUtil", "URL Connection Error: "
						+ Config.APP_SERVER_URL_SEND_NOTIF, e);
				result = "Invalid URL: " + Config.APP_SERVER_URL_SEND_NOTIF;
			}

			StringBuilder postBody = new StringBuilder();
			Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				postBody.append(param.getKey()).append('=')
						.append(param.getValue());
				if (iterator.hasNext()) {
					postBody.append('&');
				}
			}
			String body = postBody.toString();
			byte[] bytes = body.getBytes();
			HttpURLConnection httpCon = null;
			try {
				httpCon = (HttpURLConnection) serverUrl.openConnection();
				httpCon.setDoOutput(true);
				httpCon.setUseCaches(false);
				httpCon.setFixedLengthStreamingMode(bytes.length);
				httpCon.setRequestMethod("POST");
				httpCon.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStream out = httpCon.getOutputStream();
				out.write(bytes);
				out.close();

				int status = httpCon.getResponseCode();
				if (status == 200) {
					result = "RegId shared with Application Server";
				} else {
					result = "Post Failure." + " Status: " + status;
				}
			} finally {
				if (httpCon != null) {
					httpCon.disconnect();
				}
			}

		} catch (IOException e) {
			result = "Post Failure. Error in sharing with App Server.";
			Log.e("AppUtil", "Error in sharing with App Server: " + e);
		}
		return result;
	}

    public String SendNotificationForChat(final ArrayList<String> list_contact, String event_id, String event_creator, String event_name) {

        String result = "";
        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("nbr_invitation",String.valueOf(list_contact.size()));

        for(int i=0; i<list_contact.size(); i++){
            paramsMap.put("login"+i,list_contact.get(i));
        }
        paramsMap.put("event_creator",event_creator);
        paramsMap.put("event_id",event_id);
        paramsMap.put("event_name",event_name);
        paramsMap.put("chatStatus","newMessage");


        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.APP_SERVER_URL_SEND_NOTIF);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.APP_SERVER_URL_SEND_NOTIF, e);
                result = "Invalid URL: " + Config.APP_SERVER_URL_SEND_NOTIF;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
                    .iterator();

            while (iterator.hasNext()) {
                Entry<String, String> param = iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "RegId shared with Application Server";
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }

    public String SendNotificationForClosedEvent(final ArrayList<String> list_contact, String event_id, String event_name) {

        String result = "";
        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("nbr_invitation",String.valueOf(list_contact.size()));

        for(int i=0; i<list_contact.size(); i++){
            paramsMap.put("login"+i,list_contact.get(i));
        }
        paramsMap.put("event_id",event_id);
        paramsMap.put("event_name",event_name);
        paramsMap.put("close_event","true");

        try {
            URL serverUrl = null;
            try {
                serverUrl = new URL(Config.APP_SERVER_URL_SEND_NOTIF);
            } catch (MalformedURLException e) {
                Log.e("AppUtil", "URL Connection Error: "
                        + Config.APP_SERVER_URL_SEND_NOTIF, e);
                result = "Invalid URL: " + Config.APP_SERVER_URL_SEND_NOTIF;
            }

            StringBuilder postBody = new StringBuilder();
            Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
                    .iterator();

            while (iterator.hasNext()) {
                Entry<String, String> param = iterator.next();
                postBody.append(param.getKey()).append('=')
                        .append(param.getValue());
                if (iterator.hasNext()) {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();
            HttpURLConnection httpCon = null;
            try {
                httpCon = (HttpURLConnection) serverUrl.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setUseCaches(false);
                httpCon.setFixedLengthStreamingMode(bytes.length);
                httpCon.setRequestMethod("POST");
                httpCon.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = httpCon.getOutputStream();
                out.write(bytes);
                out.close();

                int status = httpCon.getResponseCode();
                if (status == 200) {
                    result = "RegId shared with Application Server";
                } else {
                    result = "Post Failure." + " Status: " + status;
                }
            } finally {
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }

        } catch (IOException e) {
            result = "Post Failure. Error in sharing with App Server.";
            Log.e("AppUtil", "Error in sharing with App Server: " + e);
        }
        return result;
    }

}
