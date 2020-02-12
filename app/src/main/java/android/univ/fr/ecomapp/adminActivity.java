package android.univ.fr.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class adminActivity extends AppCompatActivity {

    //private Button logoutButton;
    private String categoryName;
    private Button addProduct;
    private EditText productName, productDescription, productPrice;
    private ImageView addPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addProduct=(Button)findViewById(R.id.addProduct);
        addPicture=(ImageView)findViewById(R.id.selectImage);
        productName=(EditText)findViewById(R.id.productName);
        productDescription=(EditText)findViewById(R.id.productDescription);
        productPrice=(EditText)findViewById(R.id.productPrice);




        //logoutButton = (Button) findViewById(R.id.Logout);
        //logoutButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //Intent intent = new Intent(adminActivity.this, MainActivity.class);
                //startActivity(intent);
            //}
        //});

        categoryName=getIntent().getExtras().get("category").toString();

        Toast.makeText(this,categoryName, Toast.LENGTH_SHORT).show();

    }
}
