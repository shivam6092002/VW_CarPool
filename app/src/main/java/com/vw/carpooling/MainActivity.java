package com.vw.carpooling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText et1 = findViewById(R.id.username);
        EditText et2 = findViewById(R.id.password);
        et1.setText("");
        et2.setText("");

    }


    public void continuecommuter(View view) {
        LinearLayout ll1 = findViewById(R.id.ll1);
        LinearLayout ll2 = findViewById(R.id.ll2);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("isdriver", "no");
        myEdit.commit();

    }

    public void continueowner(View view) {
        LinearLayout ll1 = findViewById(R.id.ll1);
        LinearLayout ll2 = findViewById(R.id.ll2);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("isdriver", "yes");
        myEdit.commit();

    }

    public void showsignup(View view) {
        Intent i =new Intent(MainActivity.this,Register.class);
        startActivity(i);
        finish();
    }

    public void login(View view) {
        EditText et1 = findViewById(R.id.username);
        EditText et2 = findViewById(R.id.password);

        String pw=et2.getText().toString();
        String e=et1.getText().toString();

        String tmp="";
        for(int i=0;i<e.length();i++){
            if(e.charAt(i)!='.'){
                tmp+=e.charAt(i);
            }
            else{

            }
        }
        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference(tmp+"pswrd");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue().toString().equals(pw)){
                    open1();
                }
                else{
                   // Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void open1(){

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

       if(sharedPreferences.getString("isdriver","no").equals("yes")){
           Intent i =new Intent(MainActivity.this,MainActivity3.class);
           startActivity(i);
       }
       else{
           Intent i =new Intent(MainActivity.this,MainActivity2.class);
           startActivity(i);
       }

    }
}