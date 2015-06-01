package nf28.weevent.Model;

/**
 * Created by KM on 08/05/15.
 */
public class Message {
    private String login            = null;
    private String texteMsg         = null;

    public Message(String _login, String _text){
        super();
        this.login                  = _login;
        this .texteMsg              = _text;
    }

    public String getLogin(){
        return login;
    }
    public String getTextMsg(){
        return texteMsg;
    }
}
