package my.com.newapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import my.com.newapps.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class feedback extends AppCompatActivity{

    Button submit;
    RatingBar [] r = new RatingBar[7];
    FloatingActionButton fab1,fab2;
    Firebase root;
    StringBuilder rating;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        //Full screen is set for the Window

        setContentView(R.layout.feedback);

        auth = FirebaseAuth.getInstance();
        submit = (Button) findViewById(R.id.submit);

        r[0] = (RatingBar) findViewById(R.id.ratingBar1);
        r[1] = (RatingBar) findViewById(R.id.ratingBar2);
        r[2] = (RatingBar) findViewById(R.id.ratingBar3);
        r[3] = (RatingBar) findViewById(R.id.ratingBar4);
        r[4] = (RatingBar) findViewById(R.id.ratingBar5);
        r[5] = (RatingBar) findViewById(R.id.ratingBar6);
        r[6] = (RatingBar) findViewById(R.id.ratingBar7);

        root = new Firebase("https://firbase-cvs.firebaseio.com" + login.UID);

        root.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String rating_new = map.get("rating");
                rating = new StringBuilder(rating_new);

                for(int i=0;i<7;i++){
                    int y = rating.charAt(i)-'0';
                    r[i].setRating(y);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(feedback.this, login.class);
                    startActivity(intent);
                }
            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0;i<7;i++){
                    char x = (char) (r[i].getRating()+'0');
                    rating.setCharAt(i,x);
                }

                String ss = rating.toString();
                register.rating_db.setValue(ss);

                Intent intent = new Intent(feedback.this, suggestion.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
}
