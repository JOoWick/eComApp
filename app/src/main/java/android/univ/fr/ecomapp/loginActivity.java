package android.univ.fr.ecomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.univ.fr.ecomapp.Model.userModel;
import android.univ.fr.ecomapp.Prevalent.Prevalent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText  phoneNumber, password;
    private ProgressDialog loadingBar;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        phoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        password = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        rememberMe = (CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });


    }

    private void LoginUser() {

        String phone = phoneNumber.getText().toString();
        String passwd = password.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"Please enter phone number ...",Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(passwd)) {
            Toast.makeText(this,"Please enter password ...",Toast.LENGTH_SHORT).show();
        }

        else{
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Please wait a moment ...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            allowAccesToAccount(phone, passwd);
        }

    }

    private void allowAccesToAccount(final String phone, final String passwd){

        if (rememberMe.isChecked()){

            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, passwd);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){

                    userModel usersData = dataSnapshot.child("Users").child(phone).getValue(userModel.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(passwd)){

                            Toast.makeText(loginActivity.this,"Logged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent   = new Intent(loginActivity.this, homeActivity.class);
                            startActivity(intent);


                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(loginActivity.this,"Incorrect password !", Toast.LENGTH_SHORT).show();
                    }

                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(loginActivity.this,"Incorrect phone number !", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(loginActivity.this,"You entered wrong phone number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(loginActivity.this,"Please enter a valid phone number", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
