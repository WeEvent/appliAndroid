package nf28.weevent.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import nf28.weevent.R;

public class FriendsActivity extends MainActivity {

    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        initializeListEvents();

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

    }

    public void initializeListEvents(){

        mainListView = (ListView) findViewById( R.id.ListView );

        String[] planets = new String[] { "Jean", "Martin", "Nicolas", "Clément", "Manon", "Thomas", "Lucie", "Stéphanie", "Théo", "Kévin", "Alexandra","Caroline"};
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(planets) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );
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
