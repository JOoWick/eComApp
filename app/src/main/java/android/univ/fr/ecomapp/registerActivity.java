package android.univ.fr.ecomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity
{
    private Button createAccountButton;
    private EditText userName, phoneNumber, password;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.register_btn);
        userName = (EditText) findViewById(R.id.register_user_name_input);
        phoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        password = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {

        String name = userName.getText().toString();
        String phone = phoneNumber.getText().toString();
        String passwd = password.getText().toString();


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,"Please enter user name ...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"Please enter phone number ...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this,"Please enter password ...",Toast.LENGTH_SHORT).show();
        }
        else {

            loadingBar.setTitle("Create account");
            loadingBar.setMessage("Please wait a moment ...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhoneNumber(name,phone,passwd);

        }
    }

    private void validatePhoneNumber(final String name, final String phone, final String passwd){

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Name", name);
                    userDataMap.put("Password", passwd);
                    userDataMap.put("Phone", phone);

                    RootRef.child("Users").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(registerActivity.this, "Your account is added successfully",Toast.LENGTH_SHORT ).show();
                                Intent intent   = new Intent(registerActivity.this, loginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(registerActivity.this, "Network error: Please try again...",Toast.LENGTH_SHORT ).show();


                            }

                        }
                    });


                }
                else{
                    Toast.makeText(registerActivity.this,"This "+phone+" already exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(registerActivity.this,"Please try another phone number",Toast.LENGTH_SHORT).show();
                    Intent intent   = new Intent(registerActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
