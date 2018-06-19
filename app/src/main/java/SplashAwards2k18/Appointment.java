package SplashAwards2k18;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Appointment extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();

    private Button comments;
    private Button status;

    private EditText reason;
    private EditText date;
    private EditText time;
    private Button submit;
    private Calendar myCalendar;
    private Appointment a=this;
    private RelativeLayout layout;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        int num=getIntent().getIntExtra("num",0);
        open(num);
        comments=(Button)findViewById(R.id.doctorcomment);
        status=(Button)findViewById(R.id.status);
        reason=(EditText)findViewById(R.id.reason);
        date=(EditText)findViewById(R.id.date);
        time=(EditText)findViewById(R.id.time);
        submit=(Button)findViewById(R.id.createappointment);
        layout=(RelativeLayout)findViewById(R.id.apptlayout);
        text=(TextView)findViewById(R.id.appointementtext);

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener picker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(a, picker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(a, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reason.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("Please enter the reason for appointment!")
                            .setTitle("Input error").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(date.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("Please enter the date of appointment!")
                            .setTitle("Input error").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(reason.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("Please enter the start time of appointment!")
                            .setTitle("Input error").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    DatabaseReference ref = dbRef.child("doctor_panel").child("appointment");
                    ref.child("request").setValue("Request: "+reason.getText().toString()+"\nDate: "+date.getText().toString()+"\nTime: "+time.getText().toString());
                    ref.child("status").setValue("Processing");
                    AlertDialog.Builder builder = new AlertDialog.Builder(a);
                    builder.setMessage("Appointment Sent!")
                            .setTitle("Appointment Info").setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(2);
            }
        });
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(1);
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
    }
    public void open(int num){
        if (num==1){//doctor's comments
            layout.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
            dbRef.child("doctor_panel").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    text.setText("Doctor's advice:\n"+dataSnapshot.child("doctor_comments").getValue().toString()+"\n\nRecommended Calorie Intake:\n"+dataSnapshot.child("daily_calorie").getValue().toString());
                    Calories.Scalories=Double.parseDouble(dataSnapshot.child("daily_calorie").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(num==2){
            layout.setVisibility(View.INVISIBLE);
            text.setVisibility(View.VISIBLE);
            dbRef.child("doctor_panel").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    text.setText(dataSnapshot.child("appointment").child("request").getValue().toString()+"\n\nAppointment Status:\n"+dataSnapshot.child("appointment").child("status").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        if(text.getVisibility()==View.VISIBLE){
            text.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
        }
        else{
        super.onBackPressed();}
    }
}
