package nf28.weevent.Controller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import nf28.weevent.Controller.Gcm.ShareExternalServer;
import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 05/06/2015.
 */
public class SendInvitationActivity extends ActionBarActivity{
    Button all, groups;
    Button sendInvitation;
    String view;

    LinearLayout layout;
    LinearLayout friend_list;

    List<ModelAdapter> contactsToInvite;
    List<ModelAdapter> groupsToInvite;

    CustomAdapter contactsAdapter;
    CustomAdapter groupsAdapter;

    CustomAdapter getContactsAdapter(){
        return contactsAdapter;
    }

    CustomAdapter getGroupsAdapter(){
        return groupsAdapter;
    }

    //gcm
    ShareExternalServer appUtil;
    AsyncTask<Void, Void, String> shareRegidTask;

    private ArrayList<String> list_contact = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_invitation);
        setTitle("Invitation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        contactsAdapter = buildContacts();
        groupsAdapter = buildGroups();


        friend_list = (LinearLayout) findViewById(R.id.guests_list);
        layout = (LinearLayout) findViewById(R.id.progressbar_view);

        all = (Button)findViewById(R.id.btn_guests_all);
        groups = (Button)findViewById(R.id.btn_guests_groups);
        sendInvitation = (Button) findViewById(R.id.btn_send_invitation);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        InviteContactsFragment fragment = new InviteContactsFragment();
        fragmentTransaction.add(R.id.guests_list, fragment);
        fragmentTransaction.commit();
        view = "all";

        all.setOnClickListener(changeListOnClickListener);
        groups.setOnClickListener(changeListOnClickListener);
        sendInvitation.setOnClickListener(onSendInvitationListener);

        all.setOnTouchListener(onTouchListener);
        groups.setOnTouchListener(onTouchListener);
        sendInvitation.setOnTouchListener(onTouchListener);

    }

    Button.OnClickListener onSendInvitationListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {

            List<String> eventContacts = DataManager.getInstance().getSelectedEvt().getContactList();

            for (int i = 0; i < contactsToInvite.size(); i++) {
                //System.err.println("------ |  " + contactsToInvite.get(i).getValue() + " | --------");
                if (contactsToInvite.get(i).getValue() == 1) {
                    list_contact.add(contactsToInvite.get(i).getName());
                }
            }

            for (int i = 0; i < groupsToInvite.size(); i++) {
                //System.err.println("------ |  " + groupsToInvite.get(i).getValue() + " | --------");
                if (groupsToInvite.get(i).getValue() == 1) {
                    List<String> contactsInGroup = DataManager.getInstance()
                            .getUser()
                            .getGroup(groupsToInvite.get(i).getName())
                            .getContactsList();
                    for (String c : contactsInGroup){
                        if(!list_contact.contains(c) &&
                                !eventContacts.contains(c)) // On enleve les personnes deja invitees qui sont dans ce groupe
                        {
                            list_contact.add(c);
                        }
                    }
                }
            }

            new Task().execute();

            // add contacts included in groups

            //TODO a friend can be inserted only once!!!!!!!

        }
    };

    Button.OnClickListener changeListOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            if(v == all){
                view = "all";
                InviteContactsFragment newFragment = new InviteContactsFragment();
                all.setBackgroundColor(0xFF08AE9E);     //green
                groups.setBackgroundColor(0xFFB2B2B2);  //light gray

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.guests_list, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }else{
                view = "groups";
                InviteGroupsFragment newFragment = new InviteGroupsFragment();
                all.setBackgroundColor(0xFFB2B2B2);     //light gray
                groups.setBackgroundColor(0xFF08AE9E);  //green

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.guests_list, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }


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

    private CustomAdapter buildContacts(){
        contactsToInvite = new ArrayList<ModelAdapter>();
        Collection<String> friends = DataManager.getInstance().getUser().getContactList();

        //int idx = 0; /// index used to fill the container for friends
        for (String s : friends) {
            if (!DataManager.getInstance().getSelectedEvt().getContactList().contains(s)){
                contactsToInvite.add(new ModelAdapter(s, 0,0,0));
            }
        }

        return new CustomAdapter(SendInvitationActivity.this, contactsToInvite);
    }

    private CustomAdapter buildGroups(){
        groupsToInvite = new ArrayList<ModelAdapter>();
        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();

        List<String> values = new ArrayList<String>();

        for (String key: groups.keySet()) {
            values.add(key);
        }

        for (String s : values) {
            groupsToInvite.add(new ModelAdapter(s, 0,0,0));
        }

        return new CustomAdapter(SendInvitationActivity.this, groupsToInvite);
    }

    public void shareRegisterIdWithServer(final ArrayList<String> list_contact){

        appUtil = new ShareExternalServer();
        shareRegidTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = appUtil.SendNotification(list_contact, DataManager.getInstance().getSelectedEvt().getNom(), DataManager.getInstance().getUser().getLogin());
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                shareRegidTask = null;
                /*Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();*/
            }

        };
        shareRegidTask.execute(null, null, null);
    }

    public void sendSMS(List<String> list_contact){
        for (String contact : list_contact) {
            String mobilePhone = DataManager.getInstance().getUser(contact).getMobile();
            if (mobilePhone != null){
                String sms = DataManager.getInstance().getUser().getLogin() + " invite you to "+DataManager.getInstance().getSelectedEvt().getNom();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobilePhone, null, sms, null, null);
                    /*.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();*/
                } catch (Exception e) {
                    /*Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();*/
                    e.printStackTrace();
                }
            }
        }
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
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
        finish();
    }


    private class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            layout.setVisibility(View.VISIBLE);
            friend_list.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(SendInvitationActivity.this, "Invitation Sent!", Toast.LENGTH_SHORT).show();
            shareRegisterIdWithServer(list_contact);
            sendSMS(list_contact);
            onBackPressed();

            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            for (String c : list_contact){
                DataManager.getInstance().addContactToEvent(c);
            }
            return null;
        }
    }
}
