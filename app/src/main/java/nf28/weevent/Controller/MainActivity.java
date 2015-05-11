package nf28.weevent.Controller;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import nf28.weevent.R;

import nf28.weevent.Model.Category;
import nf28.weevent.Model.Event;
import nf28.weevent.Model.Message;
import nf28.weevent.Model.PollValue;
import nf28.weevent.Model.User;
import nf28.weevent.Tools.DataManager;


public class MainActivity extends ActionBarActivity {

    protected ListView mDrawerList;
    protected DrawerLayout mDrawerLayout;
    protected ArrayAdapter<String> mAdapter;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button btn_events = (Button) findViewById(R.id.btn_events);
        btn_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventsActivity.class));
            }
        });

        Button btn_friends = (Button) findViewById(R.id.btn_friends);
        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            }
        });

        User u = test();
        DataManager.getInstance().addUser(u);
    }

    private void addDrawerItems() {

        //TODO:replace by R.string.item_home, R.string.item_events... but there is a cast problem
        String[] osArray = { "Events", "Friends", "Historical", "Profile", "Settings" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, EventsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,FriendsActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,HistoricalActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                        break;
                    default:
                        break;
                }
                //If we want to display some help in menu
                //Toast.makeText(MainActivity.this, "Some help", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Home");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // TODO : Configure the search info and add any event listeners
        //...
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public User test(){
        User user = new User("Chloe","2345235423");
        user.addContact("Kostandin");
        user.addEvent("1","First Event","Something cool :)");
        user.getEvent("First Event").addMessage("Yabadabdoo", user.getLogin());

        user.getEvent("First Event").addCategory("Film","Au cinema");
        user.getEvent("First Event").getCategory("Film").addPollValue("Gladiator");
        user.getEvent("First Event").getCategory("Film").getPoll().addVoterToValue("Gladiator",user.getLogin());

        /*for(String c : user.getContactList()){
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
        }*/

        return user;

    }
}