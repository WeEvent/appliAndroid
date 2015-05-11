package nf28.weevent.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class User {
    private String login                        = null;
    private String cel                          = null;
    private List<String> listContacts           = null;
    private HashMap<String,Group> listGroups    = null;
    private HashMap<String,Event> listEvents    = null;

    public User(){
        listGroups                              = new HashMap<String,Group>();;
        listContacts                            = new ArrayList<String>();
        listEvents                              = new HashMap<String,Event>();
    }

    public User(String log,String cel){
        this();
        this.setLogin(log);
        this.setCel(cel);
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
        return listContacts;
    }
    ///

    public Collection<Event> getListEvents(){
        return listEvents.values();
    }
    ///

    public List<Group> getGroup(){
        return (List<Group>)(listGroups.values());
    }
    ///

    public void addContact(String cont){
        listContacts.add(cont);
    }
    ///

    public void removeContact(String cont){
        listContacts.remove(cont);
    }
    ///

    public void addEvent(String id,String nom, String desc){
        Event evt = new Event(id,nom,desc);
        listEvents.put(nom, evt);
    }
    ///

    public void removeEvent(String evt){
        listEvents.remove(evt);
    }
    ///

    public void addGroup(String nom){
        Group gr = new Group(nom);
        listGroups.put(nom, gr);
    }
    ///

    public void removeGroup(String gr){
        listGroups.remove(gr);
    }
    ///


    public Group getGroup(String gr){
       return listGroups.get(gr);
    }
    ///

    public Event getEvent(String evt){
       return listEvents.get(evt);
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
