package nf28.weevent.Tools;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import nf28.weevent.Model.Event;
import nf28.weevent.Model.User;

/**
 * Created by clement on 10/05/2015.
 */
public class DataManager extends Activity {
    private static DataManager ourInstance = new DataManager();
    private User user = null;
    private HashMap<String,Event> events = null;

    public static DataManager getInstance() {
        return ourInstance;
    }

    public User getUser(String login) {
        //if (user == null)
        RestClient client = new RestClient("http://clement-mercier.fr/server/users");
        client.AddParam("login", login);

        try {
            client.Execute(RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = null;
        try{
            json = new JSONObject(client.getResponse());
            JSONArray res = json.getJSONArray("result");
            user = new Gson().fromJson(res.get(0).toString(), User.class);
        }
        catch (Exception e){
            e.printStackTrace();
            user=null;
        }

        return user;
    }



    public static boolean addUser(User newUser) {
        String userJson = new Gson().toJson(newUser);

        RestClient client = new RestClient("http://clement-mercier.fr/server/users");
        client.setObject(userJson);

        try {
            client.Execute(RequestMethod.POST);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
        //String response = client.getResponse();
    }

    private DataManager() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
