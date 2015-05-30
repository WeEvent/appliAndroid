package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;


public class InviteFriendsActivity extends ActionBarActivity {

    private ListView mainListView ;
    private ModelAdapter[] modelItems;
    private CustomAdapter adapter = null;
    private final Context context = this;
    // Search EditText
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friends);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mainListView = (ListView) findViewById( R.id.InviteView );

        Collection<String> friends = DataManager.getInstance().getUser().getContactList();
        modelItems = new ModelAdapter[friends.size()];

        int idx = 0; /// index used to fill the container for friends
        for (String s : friends) {
            modelItems[idx++] = new ModelAdapter(s,(DataManager.getInstance().getSelectedEvt().getContactList().contains(s))?1:0);
        }

        adapter = new CustomAdapter(this, modelItems);
        mainListView.setAdapter(adapter);

        Button btn_friends = (Button) findViewById(R.id.btn_friends_add);
        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(int i=0;i<modelItems.length;i++){
                   System.err.println("------ |  "+modelItems[i].getValue()+" | --------");
                   if(modelItems[i].getValue()==1){
                       DataManager.getInstance().getSelectedEvt().addContact(modelItems[i].getName());
                   }
               }
                //TODO a friend can be inserted only once!!!!!!!
                DataManager.getInstance().addEvent(DataManager.getInstance().getSelectedEvt());
                Toast.makeText(context, "Invitation Sent!", Toast.LENGTH_SHORT).show();
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
}
