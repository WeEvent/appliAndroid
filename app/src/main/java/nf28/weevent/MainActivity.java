package nf28.weevent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import Model.Category;
import Model.Event;
import Model.Message;
import Model.PollValue;
import Model.User;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Test Method

    public void test(){
        User user = new User("Chloe","2345235423");
        user.addContact("Kostandin");
        user.addEvent("1","First Event","Something cool :)");
        user.getEvent("First Event").addMessage("Yabadabdoo", user.getLogin());

        user.getEvent("First Event").addCategory("Film","Au cinema");
        user.getEvent("First Event").getCategory("Film").addPollValue("Gladiator");
        user.getEvent("First Event").getCategory("Film").getPoll().addVoterToValue("Gladiator",user.getLogin());

        for(String c : user.getContactList()){
            System.err.println("Contact : " + c);
        }

        for(Event e : user.getListEvents()){
            System.err.println("Event : " + e.getNom());
        }

        for(Message m : user.getEvent("First Event").getChat().getMessages()){
            System.err.println("Event : " + m.getLogin() + " - " + m.getTextMsg() + " - " + m.getDate());
        }

        for(Category c : user.getEvent("First Event").getCategoryList()){
            System.err.println("Category : " + c.getName());
        }

        for(PollValue p : user.getEvent("First Event").getCategory("Film").getPollValues()){
            System.err.println("Pollvalue : " + p.getValue() + " - Nb votants :" + p.getVotersCount() + " :");
            System.err.println(".....Pollvalue : " + p.getValue() + " - Votre vote :" + p.hasVoted(user.getLogin()) + " :");
        }


    }
}
