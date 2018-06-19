package SplashAwards2k18;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Weight extends AppCompatActivity {
    //Database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    private EditText weight;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        weight=(EditText)findViewById(R.id.weight);
        enter=(Button)findViewById(R.id.enterweight);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference val=dbRef.child("Weight").push();
                val.child("kg").setValue(Double.parseDouble(weight.getText().toString()));
                val.child("timestamp").setValue(Calendar.getInstance().getTime());
            }
        });
    }
}
