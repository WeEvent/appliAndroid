package nf28.weevent.Model;

/**
 * Created by KM on 08/05/15.
 */
public class Message {
    private String login            = null;
    private String date             = null;
    private String texteMsg         = null;

    /// Public methods ///
    public Message(String _login, String _text, String _dt){
        this.login                  = _login;
        this.date                   = _dt;
        this .texteMsg              = _text;
    }
    ///

    public String getLogin(){
        return login;
    }
    ///

    public String getDate(){
        return date;
    }
    ///

    public String getTextMsg(){
        return texteMsg;
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
