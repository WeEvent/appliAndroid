package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 23/05/2015.
 */
public class AddGroupActivity extends ActionBarActivity {
    EditText groupName;
    Button add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        groupName = (EditText)findViewById(R.id.newGroup);

        add = (Button)findViewById(R.id.btn_add_group);
        add.setOnClickListener(addOnClickListener);
    }

    Button.OnClickListener addOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            String l = String.valueOf(groupName.getText());
            String text;

            if(!getGroups().contains(l)){
                DataManager.getInstance().addGroup(l);

                groupName.setText(null);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(groupName.getWindowToken(), 0);

                text = "Group added!";

                onBackPressed();
            }
            else{
                text = "Group already exists!";
            }

            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }};

    public List getGroups() {

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();
        List<String> groupNames = new ArrayList<String>();

        for (String key: groups.keySet()) {
            groupNames.add(key);
        }

        return groupNames;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra("view", "groups");
        startActivity(intent);
        finish();
    }

}
