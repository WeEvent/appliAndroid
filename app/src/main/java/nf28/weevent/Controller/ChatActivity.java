package nf28.weevent.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import nf28.weevent.Model.Message;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 01/06/2015.
 */
public class ChatActivity extends ActionBarActivity {
    private ChatArrayAdapter adapter;
    private ListView lv;
    private EditText editText1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        setTitle("Chat");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lv = (ListView) findViewById(R.id.listView1);

        adapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_list_item);

        lv.setAdapter(adapter);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText1.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Message newMessage = new Message(DataManager.getInstance().getUser().getLogin(),
                                                    editText1.getText().toString());

                    adapter.add(newMessage);
                    DataManager.getInstance().getSelectedEvt().getChat().addMessage(newMessage);

                    editText1.setText("");

                    return true;
                }
                return false;
            }
        });

        addItems();
    }

    private void addItems() {

        // test
        adapter.add(new Message("another login", "hello!"));
        // end test

        List<Message> listMessages = DataManager.getInstance().getSelectedEvt().getChat().getMessages();

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
