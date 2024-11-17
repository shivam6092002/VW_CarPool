package com.vw.carpooling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        btn1=findViewById(R.id.sendotpbtn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void sendotpbtn(View view){
        EditText et1 = findViewById(R.id.aadhar);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();



        EditText enterotp = findViewById(R.id.enterotp);
        if(btn1.getText().toString().equals("Send OTP")) {

            enterotp.setVisibility(View.VISIBLE);
            btn1.setText("Verify");
        }
        else if (btn1.getText().toString().equals("Verify")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            for(int i=1;i<=5;i++) {
                DatabaseReference myRef = database.getReference("otp"+i);
                int finalI = i;
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(enterotp.getText().toString().equals(snapshot.getValue().toString())){

                            myEdit.putString("aadhar",et1.getText().toString());
                            Intent intent = new Intent(Register.this,AddDetails.class);

                            startActivity(intent);
                            finish();

                        }
                        else if(finalI ==5){
                           // Toast.makeText(Register.this,"Incorrect OTP", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    public void resend_login_btn(View view) {

            Intent i =new Intent(Register.this,MainActivity.class);
            startActivity(i);
            finish();


    }
}