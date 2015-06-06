package nf28.weevent.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Vector;

import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 18/05/15.
 */
public class CreateEventActivity extends ActionBarActivity {
    // All the radio buttons
    private CheckBox radio_date;
    private CheckBox radio_map;
    private CheckBox radio_transp;
    private CheckBox radio_type;

    private Button btn_events_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        radio_date = (CheckBox) findViewById(R.id.select_date);
        radio_map = (CheckBox) findViewById(R.id.select_map);
        radio_transp = (CheckBox) findViewById(R.id.select_transp);
        radio_type = (CheckBox)findViewById(R.id.select_type);


        btn_events_create = (Button) findViewById(R.id.btn_events_create);


        btn_events_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                DataManager.getInstance().addEvent(DataManager.getInstance().getSelectedEvt());
                System.err.println("Added");
                startActivity(new Intent(CreateEventActivity.this,CategoriesActivity.class));
                finish();
            }
        });

    }

    private void init(){
        Vector<Integer> tabs = new Vector<Integer>();
        //reset
        ViewPagerAdapter.resetTabs();
        Event event = DataManager.getInstance().getSelectedEvt();

        tabs.add(0);
        event.addCategory("Cat_0", "Categ_0");

        if(radio_type.isChecked()) {
            tabs.add(1);
            event.addCategory("Cat_1", "Categ_1");
        }
        if(radio_date.isChecked()) {
            tabs.add(2);
            event.addCategory("Cat_2", "Categ_2");
        }
        if(radio_map.isChecked()) {
            tabs.add(3);
            event.addCategory("Cat_3","Categ_3");
        }
        if(radio_transp.isChecked()) {
            tabs.add(4);
            event.addCategory("Cat_4", "Categ_4");
        }

        if(tabs.size()<5)
            tabs.add(5);
        event.addCategory("Cat_5","Categ_5");

        tabs.add(6);

        event.addCategory("Cat_6", "Categ_6");
        updateEventCategories(tabs);
        //init_categ_event(event);

    }

    private void updateEventCategories(Vector<Integer> tabs){
        for(Integer i : tabs){
            ViewPagerAdapter.addTab(i);
        }
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
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
        finish();
    }
}
