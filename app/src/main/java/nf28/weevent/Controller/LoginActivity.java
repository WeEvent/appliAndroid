package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import nf28.weevent.Model.User;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

public class LoginActivity extends Activity implements OnClickListener {

    Button btnSignIn;
    Button btnSignUp;

    private float xCurrentPos, yCurrentPos;
    private ImageView logoFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        logoFocus = (ImageView) findViewById(R.id.logo_weeevent);
        xCurrentPos = logoFocus.getLeft();
        yCurrentPos = logoFocus.getTop();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        final Animation anim= new TranslateAnimation(xCurrentPos, xCurrentPos, yCurrentPos, yCurrentPos+(height/4));
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {}

            @Override
            public void onAnimationRepeat(Animation arg0) {}

            @Override
            public void onAnimationEnd(Animation arg0) {
                xCurrentPos -= 150;
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                logoFocus.startAnimation(anim);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        }, 1000);




        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        SharedPreferences sharedPref = getSharedPreferences("global", Context.MODE_PRIVATE);
        String loginRegister = sharedPref.getString(("loginRegister"), null);
        if (loginRegister != null){
            User tmp = DataManager.getInstance().setUser(loginRegister);
            if (tmp != null){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
        finish();
    }


}
