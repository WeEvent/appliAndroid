package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class User {
    private String login = null;
    private String cel = null;
    private List<String> contactsList= null;
    private HashMap<String,Group> listeGroup = null;
    private HashMap<String,Event> listeEvent = null;

    public User(String log,String cel){
        this.setLogin(log);
        this.setCel(cel);
        init();
    }

    private void init(){
        listeGroup = new HashMap<String,Group>();;
        contactsList = new ArrayList<String>();
        listeEvent = new HashMap<String,Event>();
    }
    ///


    /// Public methods ///
    public void setLogin(String _login){
        login = _login;
    }
    public String getLogin(){
        return login;
    }
    ///

    public void setCel(String _cel){
        cel = _cel;
    }
    public String getCel(){
        return cel;
    }
    ///

    public List<String> getContactList(){
        return contactsList;
    }
    ///

    public List<Event> getListeEvent(){
        return (List<Event>)(listeEvent.values());
    }
    ///

    public List<Group> getGroup(){
        return (List<Group>)(listeGroup.values());
    }
    ///

    public void addContact(String cont){
        contactsList.add(cont);
    }
    ///

    public void removeContact(String cont){
        contactsList.remove(cont);
    }
    ///

    public void addEvent(String id,String nom, String desc){
        Event evt = new Event(id,nom,desc);
        listeEvent.put(nom,evt);
    }
    ///

    public void removeEvent(String evt){
        listeEvent.remove(evt);
    }
    ///

    public void addGroup(String nom){
        Group gr = new Group(nom);
        listeGroup.put(nom,gr);
    }
    ///

    public void removeGroup(String gr){
        listeGroup.remove(gr);
    }
    ///


    public Group getGroup(String gr){
       return listeGroup.get(gr);
    }
    ///

    public Event getEvent(String evt){
       return listeEvent.get(evt);
    }
    ///

    public void parseServer(){
        // To be implemented
    }
    ///

    public void updateServer(){
        // To be implemented
    }
    ///
}
