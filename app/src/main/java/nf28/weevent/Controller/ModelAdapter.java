package nf28.weevent.Controller;

/**
 * Created by KM on 23/05/15.
 */
public class ModelAdapter{
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    ModelAdapter(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }

    public void setValue(int val){
        this.value = val;
    }

}