package nf28.weevent.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class Event {
    private String id                               = null;
    private String nom                              = null;
    private String desc                             = null;

    private List<String> listContacts               = null;
    private Chat chat                               = null;
    private HashMap<String, Category> mapCategories = null;


    /// Public methods ///
    public Event(String id, String nom, String desc){
        this.setID(id);
        this.setNom(nom);
        this.setDesc(desc);
        init();
    }
    ///

    private void init(){
        chat                            = new Chat();
        listContacts                    = new ArrayList<>();
        mapCategories                   = new HashMap<String, Category>();
    }
    ///

    public void setNom(String _nom){
        nom = _nom;
    }
    ///

    public String getNom(){
        return nom;
    }
    ///

    public void setDesc(String _desc){
        this.desc = _desc;
    }
    ///

    public String getDesc(){
        return desc;
    }
    ///

    public void setID(String _id){
        this.id = _id;
    }
    ///

    public String getID(){
        return id;
    }
    ///

    public void setChat(Chat ch){
        this.chat = null;
        this.chat = ch;
    }
    ///

    public Chat getChat(){
        return chat;
    }
    ///

    public List<String> getContactList(){
        return listContacts;
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

    public Collection<Category> getCategoryList(){
        return mapCategories.values();
    }
    ///

    public Category getCategory(String cat){
        return mapCategories.get(cat);
    }
    ///

    public void addCategory(String nom, String desc){
        Category cat = new Category(nom, desc);
        mapCategories.put(nom,cat);

    }
    ///

    public void removeCategory(String categ){
        mapCategories.remove(categ);
    }
    ///

    public void addMessage(String txt,String login){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        chat.addMessage(new Message(login, txt, date));
    }

    public void parseServer(){
        // To be implemented
    }
    ///

    public void updateServer(){
        // To be implemented
    }
    ///
}
