package nf28.weevent.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CD on 08/05/2015.
 */
public class PollValue {
    private String value;
    private List<String> voters;

    /// Constructor ///
    public PollValue(String v){
        value = v;
        voters = new ArrayList<String>();
    }

    /// Public methods ///
    public void addVoter(String v){
        if(!voters.contains(v))
            voters.add(v);
    }

    public void removeVoter(String v){
        voters.remove(v);
    }

    public String getValue(){
        return value;
    }

    public int getVotersCount(){
        return voters.size();
    }


    public List<String> getVoters(){
        return voters;
    }

    public boolean hasVoted(String log){
        return voters.contains(log);
    }

    public void parseServer(){
        // To be implemented
    }

    public void updateServer(){
        // To be implemented
    }
}
