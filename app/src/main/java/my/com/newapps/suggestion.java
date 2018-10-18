package my.com.newapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import my.com.newapps.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class suggestion extends AppCompatActivity {

    String s = "";
    EditText suggestion;
    Button submit;
    Firebase root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.suggestion);

        submit = (Button) findViewById(R.id.submit);
        suggestion = (EditText)findViewById(R.id.sug);

        root = new Firebase("https://firbase-cvs.firebaseio.com" + login.UID);
        root.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Map<String,String> map = dataSnapshot.getValue(Map.class);
                s = map.get("suggestion");
                suggestion.setText(s);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //for previously saved suggestion
        suggestion.setText(s);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.sugg_db.setValue(suggestion.getText().toString().trim());
                startActivity(new Intent(suggestion.this , Thanku.class));
            }
        });

    }
}
