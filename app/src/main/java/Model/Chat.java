package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KM on 08/05/15.
 */
public class Chat {
    private List<Message> messageList   = null;
    public Chat(){
        messageList                     = new ArrayList<>();
    }

    public List<Message> getMessages(){
        return messageList;
    }
    ///

    public void addMessage(Message m){
        messageList.add(m);
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
