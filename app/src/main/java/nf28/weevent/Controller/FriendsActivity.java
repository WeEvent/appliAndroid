package nf28.weevent.Controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import nf28.weevent.R;

/**
 * Created by CD on 13/05/2015.
 */
public class FriendsActivity extends ActionBarActivity {

    Button all, groups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        all = (Button)findViewById(R.id.btn_friends_all);
        groups = (Button)findViewById(R.id.btn_friends_groups);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ContactsActivity fragment = new ContactsActivity();
        fragmentTransaction.add(R.id.list, fragment);
        fragmentTransaction.commit();

        all.setOnClickListener(changeListOnClickListener);
        groups.setOnClickListener(changeListOnClickListener);
    }

    Button.OnClickListener changeListOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            Fragment newFragment;

            if(v == all){
                newFragment = new ContactsActivity();
                all.setBackgroundColor(0xFF08AE9E);     //green
                groups.setBackgroundColor(0xFFB2B2B2);  //light gray
            }else{
                newFragment = new GroupsActivity();
                all.setBackgroundColor(0xFFB2B2B2);     //light gray
                groups.setBackgroundColor(0xFF08AE9E);  //green
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.list, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }};

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