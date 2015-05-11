package nf28.weevent.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import nf28.weevent.Controller.List.ActionSlideExpandableListView;
import nf28.weevent.R;

public class FriendsActivity extends MainActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        final Button btn_friends_all = (Button) findViewById(R.id.btn_friends_all);
        final Button btn_friends_groups = (Button) findViewById(R.id.btn_friends_groups);

        btn_friends_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_friends_all.setBackgroundColor(Color.rgb(8, 174, 158));
                btn_friends_groups.setBackgroundColor(Color.rgb(178,178,178));

            }
        });

        btn_friends_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_friends_groups.setBackgroundColor(Color.rgb(8,174,158));
                btn_friends_all.setBackgroundColor(Color.rgb(178,178,178));

            }
        });




        ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.list);

        // fill the list with data
        list.setAdapter(buildDummyData());

        // listen for events in the two buttons for every list item.
        // the 'position' var will tell which list item is clicked
        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

            @Override
            public void onClick(View listView, View buttonview, int position) {

                /**
                 * Normally you would put a switch
                 * statement here, and depending on
                 * view.getId() you would perform a
                 * different action.
                 */
                String actionName = "";
                if(buttonview.getId()==R.id.buttonA) {
                    actionName = "buttonA";
                } else {
                    actionName = "ButtonB";
                }
                /**
                 * For testing sake we just show a toast
                 */
                Toast.makeText(
                        FriendsActivity.this,
                        "Clicked Action: " + actionName + " in list item " + position,
                        Toast.LENGTH_SHORT
                ).show();
            }

            // note that we also add 1 or more ids to the setItemActionListener
            // this is needed in order for the listview to discover the buttons
        }, R.id.buttonA, R.id.buttonB);
    }



    /**
     * Builds dummy data for the test.
     * In a real app this would be an adapter
     * for your data. For example a CursorAdapter
     */
    public ListAdapter buildDummyData() {
        final int SIZE = 20;
        String[] values = new String[SIZE];
        for(int i=0;i<SIZE;i++) {
            values[i] = "Item "+i;
        }
        return new ArrayAdapter<String>(
                this,
                R.layout.expandable_list_item,
                R.id.text,
                values
        );
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