package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class Event {
    private String id                   = null;
    private String nom                  = null;
    private String desc                 = null;

    private List<String> contactsList   = null;
    private Chat chat                   = null;
    private HashMap<String, Category> mapCategory = null;


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
        contactsList                    = new ArrayList<>();
        mapCategory                     = new HashMap<String, Category>();
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

    public List<String> getContactList(){
        return contactsList;
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

    public List<Category> getCategoryList(){
        return (List<Category>)(mapCategory.values());
    }
    ///

    public void addCategory(String nom, String desc){
        Category cat = new Category(nom, desc);
        mapCategory.put(nom,cat);

    }
    ///

    public void removeCategory(String categ){
        mapCategory.remove(categ);
    }
    ///

    public void addMessage(String txt){
       // String login = self.getLogin();
        chat.addMessage(new Message("", txt, "01/01/2015"));
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
