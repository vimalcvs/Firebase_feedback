package my.com.newapps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Thanku extends Activity {

    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //No title bar is set for the activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen is set for the Window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.thanku);

        textView = (TextView)findViewById(R.id.text);
        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"cinzel.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Thanku.this, MainActivity.class));
                finish();
            }
        }, 1300);

    }
}
