package nf28.weevent.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CD on 08/05/2015.
 */
public class Group {

    private String name;
    private ArrayList<String> contactsList;

    /// Constructor ///
    public Group(String n){
        name = n;
        contactsList = new ArrayList<String>();
    }

    /// Public methods ///
    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public ArrayList<String> getContactsList(){
        return contactsList;
    }

    public void addContact(String login){
        contactsList.add(login);
    }

    public void removeContact(String login){
        contactsList.remove(login);
    }

    public void parseServer(){
        // To be implemented
    }

    public void updateServer(){
        // To be implemented
    }
}
