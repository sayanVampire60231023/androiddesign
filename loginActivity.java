package com.example.firebasesayan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
     EditText inputemail,inputpass;
     TextView btn;
     Button signinbtn;
     private FirebaseAuth mauth;
    private ProgressDialog mloadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         btn=findViewById(R.id.signnu);
        inputpass=findViewById(R.id.password1);
        inputemail=findViewById(R.id.inputusername);
        signinbtn=findViewById(R.id.bottom);
        mauth=FirebaseAuth.getInstance();
        mloadingbar=new ProgressDialog(loginActivity.this);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredential();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,registerActivity.class));
            }

        });
    }
    private void checkCredential() {

        String email=inputemail.getText().toString();
        String passt=inputpass.getText().toString();

         if (email.isEmpty()|| email.contains("@gmail.com"))
        {
            showError(inputemail,"mail is not valid");
        }
        else if (passt.isEmpty()||passt.length()<8)
        {
            showError(inputpass,"pass is invalid");

        }

        else
        {mloadingbar.setTitle("login");
            mloadingbar.setMessage("please wait while check your credential");
            mloadingbar.setCanceledOnTouchOutside(false);
            mloadingbar.show();

            mauth.signInWithEmailAndPassword(email,passt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(loginActivity.this,"r successful",Toast.LENGTH_SHORT).show();
                        mloadingbar.dismiss();
                        Intent intent=new Intent(loginActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);



                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();

    }
}
