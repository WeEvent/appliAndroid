package nf28.weevent.Controller;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nf28.weevent.R;

/**
 * Created by CD on 13/05/2015.
 */
public class FriendsActivity extends Activity{

    Button all, groups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
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
            }else{
                newFragment = new GroupsActivity();
            }

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.list, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }};

}