package nf28.weevent.Controller;

/**
 * Created by KM on 23/05/15.
 */
public class ModelAdapter{
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */
    int votes = 0;
    int tab = 0;

    ModelAdapter(String name, int value,int votes){
        this(name,value,votes,0);
    }
    ModelAdapter(String name, int value,int votes,int tab){
        this.name = name;
        this.value = value;
        this.votes = votes;
        this.tab = tab;
    }
    public String getName(){
        return this.name;
    }
    public int getTab(){return this.tab;}
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