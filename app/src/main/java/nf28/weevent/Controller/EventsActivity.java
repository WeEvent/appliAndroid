package nf28.weevent.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;


public class EventsActivity extends ActionBarActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private final Context context = this;
    // Search EditText
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mainListView = (ListView) findViewById( R.id.ListView );

        Collection<String> events = DataManager.getInstance().getEvents().keySet();

        List<String> list_events = new ArrayList<String>();

        for (String s : events) {
            list_events.add(s);
        }

        listAdapter = new ArrayAdapter(this, R.layout.simplerow,list_events);
        mainListView.setAdapter(listAdapter);

        Button btn_events = (Button) findViewById(R.id.btn_events_add);
        btn_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check user activation
                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.dialog_event, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result

                                Event event = new Event("10",input.getText().toString(),input.getText().toString()+" description");
                                event.addContact(DataManager.getInstance().getUser().getLogin());
                                DataManager.getInstance().setSelectedEvt(event);
                                if(DataManager.getInstance().getEvents().get(event.getNom())==null) {
                                    init(event);
                                    startActivity(new Intent(EventsActivity.this,CreateEventActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),	"Event exists!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,	int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();

            }
        });



        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),	((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                loadEvent(((TextView) view).getText().toString());

            }
        });


        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                EventsActivity.this.listAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void loadEvent(String evt){
        Event event = DataManager.getInstance().getEvents().get(evt);
        if(event != null){
            DataManager.getInstance().setSelectedEvt(event);
            init(event);
            startActivity(new Intent(EventsActivity.this,CategoriesActivity.class));
        }else{
            Toast.makeText(getApplicationContext(),	"Event doesn't exist!", Toast.LENGTH_SHORT).show();

        }
    }
    private void init(Event evt){

        //reset
        ViewPagerAdapter.resetTabs();
        Vector<Integer> order_tabs = new Vector<Integer>();
        System.err.println("-------"+evt.getCategoryList().size());
        for(String i : evt.getCategoryKeys()){
            System.err.println("+++++++++"+i);
            order_tabs.add(Integer.parseInt(i.substring(4)));
        }
        Collections.sort(order_tabs);
        for(int i : order_tabs)
            ViewPagerAdapter.addTab(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }
}
