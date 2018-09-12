package com.example.begic.firebaserest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private EditText editName;
    private EditText editSurname;
    private EditText editAddress;
    private EditText editPhone;
    private TextView textEmail;

    private Button buttonLogOut;
    private  Button buttonSaveInfo;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        editAddress = (EditText) findViewById(R.id.editAddress);
        editName = (EditText) findViewById(R.id.editName);
        editSurname = (EditText) findViewById(R.id.editSurname);
        editPhone = (EditText) findViewById(R.id.editPhone);
        textEmail = (TextView) findViewById(R.id.textEmail);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        buttonSaveInfo = (Button) findViewById(R.id.buttonSaveInfo);

        textEmail.setText("" +user.getEmail());

        buttonLogOut.setOnClickListener(this);
        buttonSaveInfo.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,surname,address,phone);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved...!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (v == buttonSaveInfo) {
            saveUserInformation();
        }
    }
}