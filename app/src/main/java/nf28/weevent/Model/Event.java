package nf28.weevent.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by KM on 08/05/15.
 */
public class Event {
    private String id                               = null;
    private String nom                              = null;
    private String desc                             = null;
    private String createur                         = " ";
    private boolean locked                          = false;

    private List<String> listContacts               = null;
    private Chat chat                               = null;
    private HashMap<String, Category> mapCategories = null;


    /// Public methods ///
    public Event(){
        chat                            = new Chat();
        listContacts                    = new ArrayList<>();
        mapCategories                   = new HashMap<String, Category>();
    }

    public Event(String createur,String id, String nom, String desc){
        this(id,nom,desc);
        this.createur = createur;
    }

    public Event(String id, String nom, String desc){
        this();
        this.setID(id);
        this.setNom(nom);
        this.setDesc(desc);

        double rand = Math.random();
        this.setID(Double.toString(rand));
    }

    public void setLock(boolean lock){
        this.locked = lock;
    }

    public boolean getLock(){
        return locked;
    }

    public String getCreateur(){
        return createur;
    }

    public Event(String nom, String desc){
        this();
        this.setNom(nom);
        this.setDesc(desc);
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
        if(!listContacts.contains(cont))listContacts.add(cont);
    }
    ///

    public void removeContact(String cont){
        listContacts.remove(cont);
    }
    ///

    public HashMap<String, Category> getCategories(){
        return mapCategories;
    }
    public Collection<Category> getCategoryList(){
        return mapCategories.values();
    }
    ///

    public Collection<String> getCategoryKeys(){
        return mapCategories.keySet();
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

    public void addCategory(Category cat){
        mapCategories.put(cat.getName(),cat);
    }

    public void resetCategory(){
        mapCategories.clear();
    }
    ///

    public void removeCategory(String categ){
        mapCategories.remove(categ);
    }
    ///

    /*public void addMessage(String txt,String login){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        chat.addMessage(new Message(login, txt, date));
    }*/

    /*public void addMessage(Message msg){
        chat.addMessage(msg);
    }*/

    public HashMap <Integer,PollValue> getPreferedPolls(){
        HashMap <Integer,PollValue> preferedPolls = new HashMap<Integer,PollValue>();
        Collection<Category> values = mapCategories.values();
        Vector<Category> vect_categ = new Vector<Category>();

        for(Category categorie : values) {
            Category tmp = categorie;
            for (int j = 0; j < vect_categ.size(); j++) {
                if (vect_categ.get(j).getName().compareToIgnoreCase(categorie.getName()) >0){
                    tmp = vect_categ.get(j);
                    vect_categ.set(j,categorie);
                    categorie = tmp;
                }
            }
            vect_categ.add(categorie);
        }

        for(Category categorie : vect_categ){
            if(!categorie.getName().equalsIgnoreCase("Cat_0"))
                if(!categorie.getName().equalsIgnoreCase("Cat_5"))
                    if(!categorie.getName().equalsIgnoreCase("Cat_6")) {
                        PollValue poll = categorie.getPreferedPoll();
                            preferedPolls.put(Integer.parseInt(categorie.getName().substring(4)),poll);
                    }
        }
        return preferedPolls;
    }

    public void parseServer(){
        // To be implemented
    }
    ///

    public void updateServer(){
        // To be implemented
    }
    ///

    @Override
    public String toString(){
        return this.nom;
    }
}
