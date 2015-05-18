package nf28.weevent.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */

public class CategoriesActivity extends ActionBarActivity {


    // The selected Event
    static public Event evt = null;


    //
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Desc","Who","When","Where","Sum"};
    int Numboftabs =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //
        init();
        System.err.println("Category");
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // fill the adapter with the proper values of drawables

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


        // Get all the events and init the first one by default as current Event
        HashMap<String,Event> evts = getEvents();

        for(String s : evts.keySet()){
            System.out.println("Events -- " +s);
            evt = evts.get(s);
        }
    }



    private HashMap<String,Event> getEvents() {
        return DataManager.getInstance().getEvents();
    }

    static public void setSelectedEvt(Event e){
        evt = e;
    }

    static public Event getSelectedEvt(){
        return evt;
    }
    private void init(){
        System.out.println("Init");
        ViewPagerAdapter.addTab(0);
        ViewPagerAdapter.addTab(1);
        ViewPagerAdapter.addTab(2);
        ViewPagerAdapter.addTab(3);
        ViewPagerAdapter.addTab(4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        onBackPressed();
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        */
    }
}