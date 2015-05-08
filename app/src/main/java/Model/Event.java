package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class Event {
    private int id                      = -1;
    private String nom                  = null;
    private String desc                 = null;

    private List<String> contactsList   = null;
    private Chat chat                   = null;
    private List<Category> categoryList = null;


    /// Public methods ///
    public Event(int id, String nom, String desc){
        this.setID(id);
        this.setNom(nom);
        this.setDesc(desc);
        init();
    }
    ///

    public void init(){
        chat                            = new Chat();
        contactsList                    = new ArrayList<>();
        categoryList                    = new ArrayList<Category>();
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

    public void setID(int _id){
        this.id = _id;
    }
    ///

    public int getID(){
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
        return categoryList;
    }
    ///

    public void addCategory(Category categ){
        categoryList.add(categ);
    }
    ///

    public void removeCategory(Category categ){
        categoryList.remove(categ);
    }
    ///

    public void addMessage(String txt){
       // String login = self.getLogin();
        chat.addMessage(new Message("",txt,"01/01/2015"));
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
