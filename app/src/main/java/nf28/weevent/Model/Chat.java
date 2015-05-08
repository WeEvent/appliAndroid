package nf28.weevent.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class Chat {
    private List<Message> listMessages = null;

    /// Public methods ///
    public Chat(){
        listMessages = new ArrayList<>();
    }

    public List<Message> getMessages(){
        return listMessages;
    }
    ///

    public void addMessage(Message m){
        listMessages.add(m);
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
