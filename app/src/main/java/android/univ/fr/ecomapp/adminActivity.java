package android.univ.fr.ecomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class adminActivity extends AppCompatActivity {

    //private Button logoutButton;
    private String categoryName, Price, Description, ProductName, saveCurrentDate, saveCurrentTime, productDateAndTime, downloadImageUrl;
    private Button addProduct;
    private EditText productName, productDescription, productPrice;
    private ImageView addPicture;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    private StorageReference productImageRef;
    private DatabaseReference productsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addProduct=(Button)findViewById(R.id.addProduct);
        addPicture=(ImageView)findViewById(R.id.selectImage);
        productName=(EditText)findViewById(R.id.productName);
        productDescription=(EditText)findViewById(R.id.productDescription);
        productPrice=(EditText)findViewById(R.id.productPrice);
        loadingBar = new ProgressDialog(this);



        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProduct();
            }
        });




        //logoutButton = (Button) findViewById(R.id.Logout);
        //logoutButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //Intent intent = new Intent(adminActivity.this, MainActivity.class);
                //startActivity(intent);
            //}
        //});

        categoryName=getIntent().getExtras().get("category").toString();
        productImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        Toast.makeText(this,categoryName, Toast.LENGTH_SHORT).show();

    }

    private void validateProduct() {
        Description=productDescription.getText().toString();
        ProductName=productName.getText().toString();
        Price=productPrice.getText().toString();

        if(imageUri==null){
            Toast.makeText(this,"Please add an image for the product",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ProductName)){
            Toast.makeText(this,"Please add a name for the product",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this,"Please add a description for the product",Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty(Price)){
            Toast.makeText(this,"Please add a price for the product",Toast.LENGTH_SHORT).show();
        }
        else{
            storeProductInformation();
        }
    }

    private void storeProductInformation() {

        loadingBar.setTitle("Adding new product");
        loadingBar.setMessage("Please wait a moment ...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-YYYY");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productDateAndTime = saveCurrentDate + saveCurrentTime;
        final StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment()+productDateAndTime + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String failureMessage = e.toString();
                Toast.makeText(adminActivity.this,"Error"+failureMessage,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(adminActivity.this,"Successfully uploading image ",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }

                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downloadImageUrl=task.getResult().toString();
                        Toast.makeText(adminActivity.this,"Product's image saved in database",Toast.LENGTH_SHORT).show();
                        saveProductImageDatabase();
                    }
                });
            }
        });

    }

    private void saveProductImageDatabase() {
        HashMap<String ,Object> productMap = new HashMap<>();
        productMap.put("pid",productDateAndTime);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",categoryName);
        productMap.put("price",Price);
        productMap.put("Product name",productName);

        productsRef.child(productDateAndTime).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent   = new Intent(adminActivity.this, adminProductsActivity.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(adminActivity.this,"Product is added successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    String errorMessage = task.getException().toString();
                    Toast.makeText(adminActivity.this,"error: " + errorMessage,Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void openGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode == RESULT_OK && data!=null){
            imageUri=data.getData();
            addPicture.setImageURI(imageUri);
        }
    }
}
