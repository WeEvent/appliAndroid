package nf28.weevent.Tools;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import nf28.weevent.Model.Event;
import nf28.weevent.Model.User;

/**
 * Created by clement on 10/05/2015.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private User user = null;
    private HashMap<String,Event> events = null;

    public static DataManager getInstance() {
        return ourInstance;
    }

    public User getUser() {
        if (user == null)
            loadData();
        return user;
    }

    private void loadData() {
        HttpClient httpclient= new DefaultHttpClient();
        try {
            HttpGet httpGet=
                    new HttpGet("http://clement-mercier.fr/server/users?name=");
            HttpResponse httpresponse=httpclient.execute(httpGet);
            HttpEntity httpentity=httpresponse.getEntity();

            if (httpentity!=null){
                InputStream inputstream=httpentity.getContent();
                BufferedReader bufferedreader=new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strinbulder=new StringBuilder();
                String ligne=bufferedreader.readLine();
                while (ligne!=null){
                    strinbulder.append(ligne+"n");
                    ligne=bufferedreader.readLine();
                }
                bufferedreader.close();

                JSONObject jso=new JSONObject(strinbulder.toString());
                JSONObject jsomain=jso.getJSONObject("pht");
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public void addUser(User newUser) {
        String userJson = new Gson().toJson(newUser);

        RestClient client = new RestClient("http://clement-mercier.fr/server/users");
        client.AddParam("", userJson);

        try {
            client.Execute(RequestMethod.POST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String response = client.getResponse();
        if (response != null)
            Log.i("reponse", response);
    }


    private DataManager() {
    }
}
