package nf28.weevent.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.HashMap;

import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 18/05/15.
 */
public class CreateEventActivity extends ActionBarActivity {
    // All the radio buttons
    private RadioButton radio_desc;
    private RadioButton radio_evt;
    private RadioButton radio_date;
    private RadioButton radio_map;
    private RadioButton radio_transp;
    private RadioButton radio_overview;

    private Button btn_events_create;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        radio_desc = (RadioButton) findViewById(R.id.select_desc);
        radio_evt = (RadioButton) findViewById(R.id.select_evt);
        radio_date = (RadioButton) findViewById(R.id.select_date);
        radio_map = (RadioButton) findViewById(R.id.select_map);
        radio_transp = (RadioButton) findViewById(R.id.select_transp);
        //radio_overview = (RadioButton) findViewById(R.id.select_resume);

        Button btn_events_create = (Button) findViewById(R.id.btn_events_create);

        radio_evt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.isSelected()){v.setSelected(false);}else{v.setSelected(true);}
            }
        });
        radio_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("Date");
                if(radio_date.isSelected()){radio_date.setSelected(false);}else{radio_date.setSelected(true);}
            }
        });
        radio_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("Map");
                if(radio_map.isSelected()){radio_map.setSelected(false);}else{radio_map.setSelected(true);}
            }
        });
        radio_transp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.err.println("Transp");
                if(radio_transp.isSelected()){radio_transp.setSelected(false);}else{radio_transp.setSelected(true);}
            }
        });

        btn_events_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                startActivity(new Intent(CreateEventActivity.this,CategoriesActivity.class));
            }
        });

    }



    private HashMap<String,Event> getEvents() {
        return DataManager.getInstance().getEvents();
    }

    private void init(){
        System.out.println("Select");
        if(radio_desc.isSelected())
            ViewPagerAdapter.addTab(0);
        if(radio_evt.isSelected())
            ViewPagerAdapter.addTab(1);
        if(radio_date.isSelected())
            ViewPagerAdapter.addTab(2);
        if(radio_map.isSelected())
            ViewPagerAdapter.addTab(3);
       // if(radio_overview.isSelected())
         //   ViewPagerAdapter.addTab(4);
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


    }
}
