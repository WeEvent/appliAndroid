package nf28.weevent.Controller;

/**
 * Created by KM on 23/05/15.
 */
public class ModelAdapter{
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */
    int votes = 0;

    ModelAdapter(String name, int value,int votes){
        this.name = name;
        this.value = value;
        this.votes = votes;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public int getVotes(){
        return this.votes;
    }
    public void setValue(int val){
        this.value = val;
    }



}