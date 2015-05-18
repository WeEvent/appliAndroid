package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

            if(logins.contains(l)){
                // add contact

                login.setText(null);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(login.getWindowToken(), 0);

                Context context = getApplicationContext();
                CharSequence text = "Contact added!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else{
                Context context = getApplicationContext();
                CharSequence text = " Login doesn't exist!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }};
}
