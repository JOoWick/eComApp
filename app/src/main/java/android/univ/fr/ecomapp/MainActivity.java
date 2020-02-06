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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    private Button registerButton, loginButton;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.main_register_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent   = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent   = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);

            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPasswordKey!= "" && UserPasswordKey!= ""){
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                allowAccess(UserPhoneKey, UserPasswordKey);
                loadingBar.setTitle("Already logged in");
                loadingBar.setMessage("Please wait a moment ...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
            
        }
    }

    private void allowAccess(final String phone, final String passwd) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()){

                    userModel usersData = dataSnapshot.child("Users").child(phone).getValue(userModel.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(passwd)){

                            Toast.makeText(MainActivity.this,"Logged in", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent   = new Intent(MainActivity.this, homeActivity.class);
                            startActivity(intent);


                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this,"Incorrect password !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(MainActivity.this,"Incorrect phone number !", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(MainActivity.this,"You entered wrong phone number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this,"Please enter a valid phone number", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

    }
}
