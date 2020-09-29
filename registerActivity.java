package com.example.firebasesayan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    TextView btn;
    private   EditText inputusername,inputpass,inputemail,inputconrmpass;
     Button btnregis;
private FirebaseAuth mauth;
private ProgressDialog mloadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputusername=findViewById(R.id.inputusername);
        inputpass=findViewById(R.id.password1);
         inputemail=findViewById(R.id.inputemail);
         inputconrmpass=findViewById(R.id.confirmpass);
         btnregis=findViewById(R.id.bottom);
         mauth=FirebaseAuth.getInstance();
         mloadingbar=new ProgressDialog(registerActivity.this);
        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredential();
            }

        });
         btn=findViewById(R.id.allreadyhave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,loginActivity.class));
            }
        });






    }
    private void checkCredential() {
        String usrnam=inputusername.getText().toString();
        String email=inputemail.getText().toString();
        String passt=inputpass.getText().toString();
        String conp=inputconrmpass.getText().toString();
        if (usrnam.isEmpty()||usrnam.length()<5)
        {
            showError(inputusername,"your user name is not  valid");
        }
        else if (email.isEmpty()|| email.contains("@gmail.com"))
        {
            showError(inputemail,"mail is not valid");
        }
         else if (passt.isEmpty()||passt.length()<8)
        {
            showError(inputpass,"pass is invalid");

        }
        else if (conp.isEmpty()||!conp.equals(passt))
        {
            showError(inputconrmpass,"pass is not equal to confirmpass");
        }
        else
        {
            mloadingbar.setTitle("registration ");
            mloadingbar.setMessage("please wait while check your credential");
            mloadingbar.setCanceledOnTouchOutside(false);
            mloadingbar.show();
            mauth.createUserWithEmailAndPassword(email,passt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(registerActivity.this,"r successful",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(registerActivity.this,loginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);



                    } else
                    {
                        Toast.makeText(registerActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
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
 

