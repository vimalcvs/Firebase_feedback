package my.com.newapps;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.com.newapps.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class register extends AppCompatActivity {

    EditText name,phone,clg,email;
    Button register;
    String n,p,c = "", sugg="", rating="0000000";
    Firebase root;

    public static DatabaseReference databaseReference , db, email_db , name_db, rating_db, sugg_db, clg_db, phone_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.form_2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        register = (Button) findViewById(R.id.register);

        name = (EditText)findViewById(R.id.name);
        clg = (EditText)findViewById(R.id.clg);
        phone = (EditText)findViewById(R.id.num);
        email = (EditText)findViewById(R.id.email);

        login.UID = login.firebaseUser.getUid().trim();
        login.email = login.firebaseUser.getEmail();
        login.username = login.firebaseUser.getDisplayName();

        name.setText(login.username);
        email.setText(login.email);

        final ProgressDialog progress = new ProgressDialog(register.this);
        progress.setMessage("Loading Details");
        progress.show();

        boolean a = true;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(register.this.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        a = ni!=null && ni.isConnected();

        if(!a){
            progress.dismiss();
            Toast.makeText(register.this, "No Network", Toast.LENGTH_SHORT).show();
        }
        else{
            root = new Firebase("https://firbase-cvs.firebaseio.com" + login.UID);
            root.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    try{
                        c = map.get("clg");
                        clg.setText(c);

                        p = map.get("phone");
                        phone.setText(p);

                        n = map.get("name");
                        name.setText(login.username);

                        String sg = map.get("suggestion");
                        if(!sg.equals(null)){
                            sugg = sg;
                        }

                        String ra = map.get("rating");
                        if(!sg.equals(null)){
                            rating = ra;
                        }

                    }
                    catch (Exception e){

                    }

                    progress.dismiss();

                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    progress.dismiss();
                }
            });
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                n = name.getText().toString().trim();
                p = phone.getText().toString().trim();
                c = clg.getText().toString().trim();

                boolean a = true;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(register.this.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                a = ni!=null && ni.isConnected();


                if(c.equals(""))
                     Toast.makeText(register.this, "Institute field can't be empty", Toast.LENGTH_SHORT).show();

                else if(n.equals(""))
                    Toast.makeText(register.this, "Name field can't be empty", Toast.LENGTH_SHORT).show();

                else if(p.equals(""))
                    Toast.makeText(register.this, "Contact field can't be empty", Toast.LENGTH_SHORT).show();

                else if(!a){
                    Toast.makeText(register.this, "No Network", Toast.LENGTH_SHORT).show();
                }

                else if(p.length()<10){
                    Toast.makeText(register.this, "Enter valid contact number", Toast.LENGTH_SHORT).show();
                }
                else {
                    db = databaseReference.child(login.UID);

                    email_db = db.child("email");
                    email_db.setValue(login.email);

                    name_db = db.child("name");
                    name_db.setValue(n);

                    rating_db = db.child("rating");
                    rating_db.setValue(rating);

                    sugg_db = db.child("suggestion");
                    sugg_db.setValue(sugg);

                    clg_db = db.child("clg");
                    clg_db.setValue(c);

                    phone_db = db.child("phone");
                    phone_db.setValue(p);


                    final ProgressDialog progress2 = new ProgressDialog(register.this);
                    progress2.setMessage("Uploading Details");
                    progress2.show();


                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(register.this, feedback.class);
                            progress2.dismiss();
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(register.this, MainActivity.class));
    }
}

