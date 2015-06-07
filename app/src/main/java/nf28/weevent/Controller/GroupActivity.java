package nf28.weevent.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 14/05/2015.
 */
public class GroupActivity extends ActionBarActivity{
    String group;
    ImageButton delete;
    Button add;
    ListView contactsList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final TextView groupName = (TextView) this.findViewById(R.id.groupName);
        group = getIntent().getStringExtra("group");
        groupName.setText(group);

        delete = (ImageButton) this.findViewById(R.id.btn_delete_group);
        add = (Button) this.findViewById(R.id.btn_add_contact_to_group);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(GroupActivity.this)
                        .setMessage("Are you sure you want to delete this group ?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new DeleteGroup().execute();
                            }
                        })
                        .setPositiveButton("No", null)
                        .show();

            }
        });
        add.setOnClickListener(addContactToGroupListener);

        delete.setOnTouchListener(onTouchListener);
        add.setOnTouchListener(onTouchListener);

        contactsList = (ListView)this.findViewById(R.id.contactsWithTrashList);

        ListWithDeleteOptionAdapter adapter = buildData(group);
        contactsList.setAdapter(adapter);
    }

    public ListWithDeleteOptionAdapter buildData(String groupName) {

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();
        Group group = groups.get(groupName);

        ArrayList<String> values = group.getContactsList();

        return new ListWithDeleteOptionAdapter(values, GroupActivity.this, groupName);
    }

    Button.OnClickListener addContactToGroupListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(GroupActivity.this, AddContactToGroupSelectContactActivity.class);
            intent.putExtra("group", group);
            startActivity(intent);
            finish();
        }};

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

    private class DeleteGroup extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean ret = DataManager.getInstance().removeGroup(group);
            if (ret){
                Intent intent = new Intent(GroupActivity.this, FriendsActivity.class);
                intent.putExtra("view", "groups");
                startActivity(intent);
                finish();
            }
            return ret;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        contactsList.setAdapter(buildData(group));
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
