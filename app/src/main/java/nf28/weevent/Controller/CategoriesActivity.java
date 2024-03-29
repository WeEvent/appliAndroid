package nf28.weevent.Controller;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */

public class CategoriesActivity extends ActionBarActivity {

    //
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    MenuItem chatIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Receiver for local broadcast to update chat after notif
        registerReceiver(mMessageReceiver, new IntentFilter("updateChat"));

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),ViewPagerAdapter.getTiles(),ViewPagerAdapter.getSizeTab());

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);

        chatIcon = menu.findItem(R.id.action_chat);

        SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
        String chatRegister = sharedPref.getString(DataManager.getInstance().getSelectedEvt().getID(), null);
        if (chatRegister != null){
            menu.findItem(R.id.action_chat).setIcon(R.drawable.ic_chat_newmessage);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_chat:
                // app icon in action bar clicked; go home
                Intent chat = new Intent(this, ChatActivity.class);
                startActivity(chat);
                finish();
                return true;
            default:
                onBackPressed();
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //UPDATE VUE
            chatIcon.setIcon(R.drawable.ic_chat_newmessage);
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
        finish();
    }

}