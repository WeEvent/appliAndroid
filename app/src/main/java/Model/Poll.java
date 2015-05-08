package Model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by CD on 08/05/2015.
 */
public class Poll {

    private HashMap<String, PollValue> values;

    /// Constructor ///
    public Poll(){
        values  = new HashMap<String, PollValue>();
    }

    /// Public methods ///
    public void addPollValue(String v){
        values.put(v, new PollValue(v));
    }

    public void removePollValue(String v){
        values.remove(v);
    }

    public void addVoterToValue(String v, String voter){
        values.get(v).addVoter(voter);
    }

    public void removeVoterToValue(String v, String voter){
        values.get(v).removeVoter(voter);
    }

    public Collection<PollValue> getPollValues(){
        return values.values();
    }

    public PollValue getPollValue(String v) {
        return values.get(v);
    }

    public void parseServer(){
        // To be implemented
    }

    public void updateServer(){
        // To be implemented
    }

}
