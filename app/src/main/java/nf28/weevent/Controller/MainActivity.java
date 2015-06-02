package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nf28.weevent.Model.User;
import nf28.weevent.R;


public class MainActivity extends ActionBarActivity {

    protected ListView mDrawerList;
    protected DrawerLayout mDrawerLayout;
    //protected ArrayAdapter<String> mAdapter;
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

        btn_events.setOnTouchListener(onTouchListener);

        Button btn_friends = (Button) findViewById(R.id.btn_friends);
        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FriendsActivity.class));
            }
        });

        btn_friends.setOnTouchListener(onTouchListener);
    }

    protected void addDrawerItems() {

        String[] thumbnail = {String.valueOf(R.drawable.ic_event),String.valueOf(R.drawable.ic_friends) ,String.valueOf(R.drawable.ic_historical) ,String.valueOf(R.drawable.ic_profile) , String.valueOf(R.drawable.ic_settings), String.valueOf(R.drawable.ic_settings) };
        String[] text = { "Events", "Friends", "Historical", "Profile", "Settings", "Logout" };

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < text.length; i++) {
            Map<String, Object> datum = new HashMap<String, Object>(2);
            datum.put("thumbnail", thumbnail[i]);
            datum.put("name", text[i]);
            data.add(datum);
        }
        mDrawerList.setAdapter(new SimpleAdapter(this, data, R.layout.drawer_item, new String[] {"thumbnail","name"}, new int[] {R.id.imageView, R.id.titoloTv}));


        //TODO:replace by R.string.item_home, R.string.item_events... but there is a cast problem
        String[] osArray = { "Events", "Friends", "Historical", "Profile", "Settings" };
        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        //mDrawerList.setAdapter(mAdapter);

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
                    case 5:
                        SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.remove("loginRegister");
                        editor.commit();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:
                        break;
                }
                //If we want to display some help in menu
                //Toast.makeText(MainActivity.this, "Some help", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Button.OnTouchListener onTouchListener
            = new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.getBackground().clearColorFilter();
                    v.invalidate();
                    break;
                }
            }
            return false;
        }
    };

    protected void setupDrawer() {
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

    /*@Override
    public void onBackPressed()
    {
        // desactivate the return button
    }*/
}
