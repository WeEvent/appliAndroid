package nf28.weevent.Model;

import java.util.Collection;

/**
 * Created by CD on 08/05/2015.
 */
public class Category {

    private String name;
    private String description;
    private Poll poll;

    /// Constructor ///
    public Category(String n, String d){
        name = n;
        description = d;
        poll = new Poll();
    }

    /// Public methods ///
    public String getName(){
        return name;
    }

    public int getTabIdx(){
        return Integer.parseInt(getName().substring(4));
    }
    public void setName(String n){
        name = n;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String d){
        description = d;
    }

    public void addPollValue(String v){
        poll.addPollValue(v);
    }

    public void removePollValue(String v) {
        poll.removePollValue(v);
    }

    public Collection<PollValue> getPollValues(){
        return poll.getPollValues();
    }

    public PollValue getPollValue(String v){
        return poll.getPollValue(v);
    }

    public PollValue getPreferedPoll(){return poll.getPreferedPoll();}

    public Poll getPoll(){
        return poll;
    }

    public void parseServer(){
        // To be implemented
    }

    public void updateServer(){
        // To be implemented
    }

}
