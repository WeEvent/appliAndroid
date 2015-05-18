package nf28.weevent.Tools;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nf28.weevent.Model.Event;
import nf28.weevent.Model.Group;
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

    public User getUser(){
        return user;
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

    public List<String> getAllLogins() {
        //if (user == null)
        RestClient client = new RestClient(serverAddress + "users");

        List<String> logins = new ArrayList<>();

        try {
            client.Execute(RequestMethod.GET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = null;
        try{
            json = new JSONObject(client.getResponse());
            JSONArray res = json.getJSONArray("result");
            for (int i = 0; i < res.length(); i++) {
                Log.i("user", res.getJSONObject(i).toString());
                User u = new Gson().fromJson(res.getJSONObject(i).toString(), User.class);
                logins.add(u.getLogin());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            logins=null;
        }

        return logins;
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
    }

    public boolean addContact(String login) {
        if (user.getContactList().contains(login))
            return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        JSONObject action = new JSONObject();
        JSONObject contact = new JSONObject();

        try {
            contact.put("listContacts", login);
            action.put("$addToSet", contact);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        user.addContact(login);
        return true;
    }

    public boolean removeContact(String login) {
        if (!user.getContactList().contains(login))
            return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        JSONObject action = new JSONObject();
        JSONObject contact = new JSONObject();

        try {
            contact.put("listContacts", login);
            action.put("$pull", contact);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        user.removeContact(login);
        return true;
    }

    public boolean addGroup(String name) {
        if (user.getGroups().containsKey(name))
            return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        String group = new Gson().toJson(new Group(name));
        JSONObject action = new JSONObject();
        try {
            JSONObject groupObj = new JSONObject(group);
            JSONObject hashMap = new JSONObject();
            hashMap.put("listGroups." + name, groupObj);
            action.put("$set", hashMap);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        user.addGroup(name);
        return true;
    }

    public boolean removeGroup(String name) {
        if (!user.getGroups().containsKey(name))
            return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        JSONObject action = new JSONObject();
        try {
            JSONObject hashMap = new JSONObject();
            hashMap.put("listGroups." + name, "");
            action.put("$unset", hashMap);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        user.removeGroup(name);
        return true;
    }

    public boolean addGroupUser(String nameGroup, String loginUser) {
        if (user.getGroup(nameGroup).getContactsList().contains(loginUser))
            return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        JSONObject action = new JSONObject();
        JSONObject contact = new JSONObject();

        try {
            contact.put("listGroups." + nameGroup + ".contactsList", loginUser);
            action.put("$addToSet", contact);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        user.getGroup(nameGroup).addContact(loginUser);
        return true;
    }

    public boolean removeGroupUser(String nameGroup, String loginUser) {
        if (!user.getGroup(nameGroup).getContactsList().contains(loginUser))
        return true;

        RestClient client = new RestClient(serverAddress + "users");
        client.AddParam("login", user.getLogin());
        JSONObject action = new JSONObject();
        JSONObject contact = new JSONObject();

        try {
            contact.put("listGroups." + nameGroup + ".contactsList", loginUser);
            action.put("$pull", contact);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        client.setObject(action.toString());

        try {
            client.Execute(RequestMethod.PUT);
            if (client.getResponseCode() != 200)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        user.getGroup(nameGroup).removeContact(loginUser);
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
