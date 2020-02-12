package android.univ.fr.ecomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class adminProductsActivity extends AppCompatActivity {

    private ImageView tShirt, sportThirt, femaleDresses, sweater, pursesBags, glasses, shoes, hats, laptops, watches, headPhoness, mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        tShirt = (ImageView) findViewById(R.id.tshirt) ;
        sportThirt = (ImageView) findViewById(R.id.sportTshirt);
        femaleDresses = (ImageView) findViewById(R.id.femaleDresses);
        sweater = (ImageView) findViewById(R.id.sweater);
        pursesBags = (ImageView) findViewById(R.id.purses_bags);
        glasses=(ImageView)findViewById(R.id.glasses);
        shoes=(ImageView)findViewById(R.id.shoes);
        hats=(ImageView)findViewById(R.id.hats);
        laptops=(ImageView)findViewById(R.id.laptops);
        watches=(ImageView)findViewById(R.id.watches);
        headPhoness=(ImageView)findViewById(R.id.headphoness);
        mobiles=(ImageView)findViewById(R.id.mobiles);

        tShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "T-Shirts");
                startActivity(intent);
            }
        });

        sportThirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Sport T-Shirts");
                startActivity(intent);
            }
        });

        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Female dresses");
                startActivity(intent);
            }
        });

        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Sweaters");
                startActivity(intent);
            }
        });

        pursesBags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Purses & Bags");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Hats");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });

        headPhoness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Head Phones");
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( adminProductsActivity.this, adminActivity.class);
                intent.putExtra("category", "Mobiles");
                startActivity(intent);
            }
        });

    }
}
