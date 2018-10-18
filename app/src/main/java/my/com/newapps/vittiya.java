package my.com.newapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import my.com.newapps.R;

public class vittiya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.vittiya);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(vittiya.this, MainActivity.class));
    }
}
