package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nf28.weevent.Controller.Gcm.ShareExternalServer;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;


public class InviteFriendsActivity extends ActionBarActivity {

    private ListView mainListView ;
    private List<ModelAdapter> modelItems;
    private CustomAdapter adapter = null;
    private final Context context = this;
    // Search EditText
    EditText inputSearch;

    //gcm
    ShareExternalServer appUtil;
    AsyncTask<Void, Void, String> shareRegidTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friends);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mainListView = (ListView) findViewById( R.id.InviteView );

        Collection<String> friends = DataManager.getInstance().getUser().getContactList();
        //friends.add(DataManager.getInstance().getUser().getLogin());
        modelItems = new ArrayList<ModelAdapter>();

        //int idx = 0; /// index used to fill the container for friends
        for (String s : friends) {
            if (!DataManager.getInstance().getSelectedEvt().getContactList().contains(s)){
                modelItems.add(new ModelAdapter(s, 0));
            }
        }

        adapter = new CustomAdapter(this, modelItems);
        mainListView.setAdapter(adapter);

        Button btn_friends = (Button) findViewById(R.id.btn_friends_add);
        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list_contact = new ArrayList<String>();
                for(int i=0;i<modelItems.size();i++){
                   System.err.println("------ |  "+modelItems.get(i).getValue()+" | --------");
                   if(modelItems.get(i).getValue()==1){
                       DataManager.getInstance().addContactToEvent(modelItems.get(i).getName());
                       list_contact.add(modelItems.get(i).getName());
                   }
                }
                //TODO a friend can be inserted only once!!!!!!!
                Toast.makeText(context, "Invitation Sent!", Toast.LENGTH_SHORT).show();

                shareRegisterIdWithServer(list_contact);
                sendSMS(list_contact);

                onBackPressed();
            }

        });


        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //TODO filter not working properly
                InviteFriendsActivity.this.adapter.getFilter().filter(cs);

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
        finish();
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
}
