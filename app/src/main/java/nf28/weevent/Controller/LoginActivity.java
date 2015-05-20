package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import nf28.weevent.Model.User;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

public class LoginActivity extends Activity implements OnClickListener {

    Button btnSignIn;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
        String loginRegister = sharedPref.getString(("loginRegister"), null);
        if (loginRegister != null){
            User tmp = DataManager.getInstance().getUser(loginRegister);
            if (tmp != null){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onClick(View v) {
        Intent i = null;
        switch(v.getId()){
            case R.id.btnSingIn:
                i = new Intent(this,SignInActivity.class);
                break;
            case R.id.btnSignUp:
                i = new Intent(this,SignUpActivity.class);

                break;
        }
        startActivity(i);
    }



}
