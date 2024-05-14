package com.example.ikitama_larose_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    /*
     *NOMS:EBONGA IKITAMA
     * PRENOMS:Bellevie Larose
     * Classe:Master-PRG18-Sup'Info
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private  void initializeComponents(){
       EditText edtName= findViewById(R.id.edtName);
       EditText edtPrice= findViewById(R.id.edtPrice);

        Button btnCreateProduct=findViewById(R.id.btnCreate);
        Button btnListProducts = findViewById(R.id.btnList);

        RetrofitService retrofitService=new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=String.valueOf(edtName.getText());
                Double price=Double.valueOf(edtPrice.getText().toString());

                Product product=new Product();
                product.setName(name);
                product.setPrice(price);

                productApi.createProduct(product)
                        .enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                Toast.makeText(MainActivity.this, "Creation réussit", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(MainActivity.this, ProductListActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Echec de la création", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,"Erreur de creation",t);
                            }
                        });
            }
        });

        btnListProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToProductList(); // Redirection vers la liste des produits
            }
        });
    }

    private void redirectToProductList() {
        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
        startActivity(intent);
    }
}