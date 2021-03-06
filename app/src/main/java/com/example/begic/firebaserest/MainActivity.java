package com.example.begic.firebaserest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView viewSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if ( firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        viewSignIn = (TextView) findViewById(R.id.viewSignIn);

        buttonRegister.setOnClickListener(this);
        viewSignIn.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email ...!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password ...!",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this,"Could not registere, please try again...",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister) {
            registerUser();
        }

        if ( v == viewSignIn) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
