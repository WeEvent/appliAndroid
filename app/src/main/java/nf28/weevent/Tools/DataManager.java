package nf28.weevent.Tools;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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

    private String serverAddress;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        serverAddress = "http://clement-mercier.fr/server/";

        events = new HashMap<>();
        user = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public User getUser(String login) {
        //if (user == null)
        RestClient client = new RestClient(serverAddress + "users");
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

    public boolean addUser(User newUser) {
        String userJson = new Gson().toJson(newUser);

        RestClient client = new RestClient(serverAddress + "users");
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

    public boolean addContact(String login) {
        user.addContact(login);

        //TODO : partie web, faire la notification en ligne de l'ajout, sauvegarde du user

        return true;
    }

    public boolean removeContact(String login) {
        User tmp = user;
        user.removeContact(login);

        //TODO : partie web, faire la notification en ligne de la suppression, sauvegarde du user
        // si la requete plante, on annule l'édition ?

        return true;
    }

    public boolean addGroup(String name) {
        user.addGroup(name);

        //TODO : partie web, faire la notification en ligne de l'ajout, sauvegarde du user

        return true;
    }

    public boolean removeGroup(String name) {
        user.removeGroup(name);

        //TODO : partie web, faire la notification en ligne de la suppression, sauvegarde du user
        // si la requete plante, on annule l'édition ?

        return true;
    }

    public boolean addGroupUser(String nameGroup, String loginUser) {
        user.getGroup(nameGroup).addContact(loginUser);

        //TODO : partie web, faire la notification en ligne de la suppression, sauvegarde du user
        // si la requete plante, on annule l'édition ?

        return true;
    }

    public boolean removeGroupUser(String nameGroup, String loginUser) {
        user.getGroup(nameGroup).removeContact(loginUser);

        //TODO : partie web, faire la notification en ligne de la suppression, sauvegarde du user
        // si la requete plante, on annule l'édition ?

        return true;
    }

    public boolean addEvent(Event newEvent) {
        String eventJson = new Gson().toJson(newEvent);

        RestClient client = new RestClient(serverAddress + "events");
        client.setObject(eventJson);

        try {
            client.Execute(RequestMethod.POST);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public HashMap<String,Event> getEvents() {

        RestClient client = new RestClient(serverAddress + "events");
        client.AddParam("listContacts", user.getLogin());

        try {
            client.Execute(RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = null;
        try{
            json = new JSONObject(client.getResponse());
            JSONArray array = json.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                Log.i("event", array.getJSONObject(i).toString());
                Event e = new Gson().fromJson(array.getJSONObject(i).toString(), Event.class);
                events.put(e.getNom(), e);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            user=null;
        }

        return events;
    }
}
