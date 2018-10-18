package my.com.newapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import my.com.newapps.R;

public class swacha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.swacha);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(swacha.this, MainActivity.class));
    }
}