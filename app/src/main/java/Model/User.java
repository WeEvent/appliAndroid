package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class User {
    private String login = null;
    private String cel = null;
    private List<String> listeContact= null;
    private List<Group> listeGroup = null;
    private List<Event> listeEvent = null;

    public User(String log,String cel){
        this.setLogin(log);
        this.setCel(cel);
        listeContact = new ArrayList<String>();
        listeEvent = new ArrayList<Event>();
    }

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

    public List<String> getListeContact(){
        return listeContact;
    }
    ///

    public List<Event> getListeEvent(){
        return listeEvent;
    }
    ///

    public List<Group> getGroup(){
        return listeGroup;
    }
    ///

    public void addContact(String cont){
        listeContact.add(cont);
    }
    ///

    public void removeContact(Contact cont){
        listeContact.remove(cont);
    }
    ///

    public void addEvent(Event evt){
        listeEvent.add(evt);
    }
    ///

    public void removeEvent(Event evt){
        listeEvent.remove(evt);
    }
    ///

    public void addGroup(Group gr){
        listeGroup.add(gr);
    }
    ///

    public void removeGroup(Group gr){
        listeGroup.remove(gr);
    }
    ///


    public Group getGroup(String gr){
        int idx = listeGroup.indexOf(gr);
        if(idx >=0){
            return listeGroup.get(idx);
        }

        return null;
    }
    ///

    public Event getEvent(String evt){
        int idx = listeEvent.indexOf(evt);
        if(idx >=0){
            return listeEvent.get(idx);
        }
        return null;
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
