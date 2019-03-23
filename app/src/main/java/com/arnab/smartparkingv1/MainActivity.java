package com.arnab.smartparkingv1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView available, unavailable,message;
    private int iAvailable, iUnavailable, total=20  ;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        available = findViewById(R.id.available);
        unavailable = findViewById(R.id.unavailable);
        //totalView = findViewById(R.id.totalView);
        message = findViewById(R.id.message);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("car");
        //DatabaseReference totalFire = database.getReference("total");

        Toast.makeText(this, "Synthesizing data!", Toast.LENGTH_SHORT).show();




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                iUnavailable = dataSnapshot.getValue(int.class);
                iAvailable = total - iUnavailable;

                if(iUnavailable>=total)
                {
                    available.setText("Available:  0");
                    available.setTextColor(Color.RED);
                    message.setTextColor(Color.RED);
                    unavailable.setText("Occupied:  FULL");
                    message.setText("Sorry for the inconvenience, Parking slots are full!");
                    Toast.makeText(MainActivity.this, "Parking FULL!", Toast.LENGTH_LONG).show();
                }

                else if(iUnavailable<20 && iUnavailable>=17)
                {
                    available.setText("Available: " + iAvailable);
                    unavailable.setText("Occupied:  " + iUnavailable);
                    int less = 20-iUnavailable;
                    message.setTextColor(Color.BLUE);
                    message.setText("Hurry up! only "+ less + " slots available!");
                    available.setTextColor(Color.BLUE);
                }

                else if(iUnavailable<=16 )
                {
                    available.setText("Available: " + iAvailable);
                    unavailable.setText("Occupied:  " + iUnavailable);
                    message.setTextColor(Color.GREEN);
                    message.setText("You have lot of space to park your vehicle!");
                    available.setTextColor(Color.GREEN);
                }
                else {


                    available.setTextColor(Color.DKGRAY);
                    message.setText("");
                    available.setText("Available:  " + iAvailable);
                    unavailable.setText("Occupied:  " + iUnavailable);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error 404!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}


