package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class User {
    private String login = null;
    private String cel = null;
    private List<Contact> listeContact= null;
    private List<Event> listeEvent = null;

    public User(String log,String cel){
        this.setLogin(log);
        this.setCel(cel);
        listeContact = new ArrayList<Contact>();
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

    public List<Contact> getListeContact(){
        return listeContact;
    }
    ///

    public List<Event> getListeEvent(){
        return listeEvent;
    }
    ///

    public void addContact(Contact cont){
        listeContact.add(cont);
    }
    ///

    public void addEvent(Event evt){
        listeEvent.add(evt);
    }
    ///

    public void removeContact(Contact cont){
        listeContact.remove(cont);
    }
    ///

    public void removeEvent(Event evt){
        listeEvent.remove(evt);
    }
    ///


}
