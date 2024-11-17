package com.vw.carpooling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }

    public void verifyemailotp(View view) {
        EditText et1 = findViewById(R.id.enterotp1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for(int i=1;i<=5;i++) {
            DatabaseReference myRef = database.getReference("otp"+i);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(et1.getText().toString().equals(snapshot.getValue().toString())){

                        adjust_view();
                       EditText et1 = findViewById(R.id.email);
                        TextView tv1 = findViewById(R.id.tv1);
                        tv1.setText("Verified");
                        et1.setEnabled(false);
                    }
                    else{
                        Toast.makeText(AddDetails.this,"Incorrect OTP", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public void verifyphoneotp(View view) {
        EditText et1 = findViewById(R.id.enterotp2);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for(int i=1;i<=5;i++) {
            DatabaseReference myRef = database.getReference("otp"+i);
            int finalI = i;
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(et1.getText().toString().equals(snapshot.getValue().toString())){
                        EditText et1 = findViewById(R.id.phone);
                        TextView tv1 = findViewById(R.id.tv2);
                        tv1.setText("Verified");
                        et1.setEnabled(false);
                        adjust_view();

                    }
                    else if(finalI ==5){
                        Toast.makeText(AddDetails.this,"Incorrect OTP", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
    public void adjust_view(){
        LinearLayout ll1 = findViewById(R.id.emailotp);
        LinearLayout ll2 = findViewById(R.id.phoneotp);
        LinearLayout ll3 = findViewById(R.id.signup1);
        LinearLayout ll4 = findViewById(R.id.pay1);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll4.setVisibility(View.GONE);
        ll3.setVisibility(View.VISIBLE);

    }

    public void verifyemail(View view) {
        LinearLayout ll1 = findViewById(R.id.emailotp);
        ll1.setVisibility(View.VISIBLE);
    }
    public void verifyphone(View view) {
        LinearLayout ll1 = findViewById(R.id.phoneotp);
        ll1.setVisibility(View.VISIBLE);
    }

    public void paybtn(View view) {
        EditText et1 = findViewById(R.id.enterupi);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for(int i=1;i<=3;i++) {

            DatabaseReference myRef = database.getReference("upi"+i);
            int finalI = i;
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(et1.getText().toString().equals(snapshot.getValue().toString())){
                        EditText email=findViewById(R.id.email);
                        String e=email.getText().toString();
                        EditText pswrd  =findViewById(R.id.pswrd);
                        String tmp="";
                        for(int i=0;i<e.length();i++){
                            if(e.charAt(i)!='.'){
                                tmp+=e.charAt(i);
                            }
                            else{

                            }
                        }
                        FirebaseDatabase mData = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = mData.getReference(tmp+"pswrd");
                        myRef.setValue(pswrd.getText().toString());
                        adjust_view();
                        paysuccess();

                    }
                    else if (finalI ==3){
                        //Toast.makeText(AddDetails.this,"Payment failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void verify(View view) {
        LinearLayout ll1 = findViewById(R.id.pay1);
        ll1.setVisibility(View.VISIBLE);
    }

    public void paysuccess(){



        Intent i =new Intent(AddDetails.this,MainActivity2.class);
        startActivity(i);

        finish();

    }
}