package nf28.weevent.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nf28.weevent.Controller.Gcm.ShareExternalServer;
import nf28.weevent.Model.Chat;
import nf28.weevent.Model.Event;
import nf28.weevent.Model.Message;
import nf28.weevent.Model.User;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 01/06/2015.
 */
public class ChatActivity extends ActionBarActivity {
    private ChatArrayAdapter adapter;
    private ListView lv;
    private EditText editText1;


    //gcm
    ShareExternalServer appUtil;
    AsyncTask<Void, Void, String> shareRegidTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        setTitle("Chat");

        //Receiver for local broadcast to update chat after notif
        registerReceiver(mMessageReceiver, new IntentFilter("updateChat"));

        //Check if there is a new message
        SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(DataManager.getInstance().getSelectedEvt().getID());
        editor.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lv = (ListView) findViewById(R.id.listView1);

        adapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_list_item);

        lv.setAdapter(adapter);

        lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        lv.setStackFromBottom(true);

        editText1 = (EditText) findViewById(R.id.editText1);

        editText1.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)
                        && !editText1.getText().toString().equals("")) {

                    Message newMessage = new Message(DataManager.getInstance().getUser().getLogin(),
                                                    editText1.getText().toString());

                    adapter.add(newMessage);
                    DataManager.getInstance().addMessage(newMessage);

                    editText1.setText("");

                    //send notification
                    shareChatMessagedWithServer();

                    return true;
                }
                return false;
            }
        });

        addItems();
    }

    private void addItems() {

        List<Message> listMessages = DataManager.getInstance().getSelectedEvt().getChat().getMessages();
        Event e = DataManager.getInstance().getSelectedEvt();

        for (int i = 0; i<listMessages.size(); i++){
            adapter.add(listMessages.get(i));
        }
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
                finish();
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

    public void shareChatMessagedWithServer(){

        appUtil = new ShareExternalServer();
        shareRegidTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                ArrayList<String> list_contact = (ArrayList<String>) DataManager.getInstance().getSelectedEvt().getContactList();
                User connectedUser = DataManager.getInstance().getUser();
                list_contact.remove(connectedUser.getLogin());
                String result = appUtil.SendNotificationForChat(list_contact,DataManager.getInstance().getSelectedEvt().getID(), connectedUser.getLogin());
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            //ex : int note = intent.getIntExtra("evaluation", 0);

            //UPDATE VUE
            Chat chat = DataManager.getInstance().getChat();
            adapter.clear();
            for (Message m : chat.getMessages())
                adapter.add(m);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}
