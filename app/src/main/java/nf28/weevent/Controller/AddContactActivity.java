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

import java.util.List;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 18/05/2015.
 */
public class AddContactActivity extends ActionBarActivity {

    EditText login;
    Button add;

    List<String> logins;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        login = (EditText)findViewById(R.id.loginSearch);

        add = (Button)findViewById(R.id.btn_add_contact);
        add.setOnClickListener(addOnClickListener);

        logins = DataManager.getInstance().getAllLogins();
    }

    Button.OnClickListener addOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            String l = String.valueOf(login.getText());
            CharSequence text;

            if(logins.contains(l)){

                List<String> contacts = DataManager.getInstance().getUser().getContactList();

                if(!contacts.contains(l)){

                    if (!l.equals(DataManager.getInstance().getUser().getLogin())){
                        DataManager.getInstance().addContact(l);

                        login.setText(null);
                        InputMethodManager imm = (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(login.getWindowToken(), 0);

                        text = "Contact added!";
                    }
                    else{
                        text="You can't add your own login!";
                    }

                }
                else{
                    text = "Contact already exists!";
                }
            }
            else{
                text = "Login doesn't exist!";
            }

            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();
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
