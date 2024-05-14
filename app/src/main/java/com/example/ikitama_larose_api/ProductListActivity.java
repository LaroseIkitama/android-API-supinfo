package com.example.ikitama_larose_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    ListView lvProducts;
    List<HashMap<String, String>> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        lvProducts = findViewById(R.id.lvProducts);

        loadProducts();
    }

    private void loadProducts(){

        RetrofitService retrofitService=new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        productApi.getProducts()

                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                        if (response.isSuccessful()) {
                            List<Product> products = response.body();

                            if (products != null) {

                                for (Product product : products) {
                                    HashMap<String, String> dataMap = new HashMap<>();
                                    dataMap.put("name", product.getName());
                                    dataMap.put("price", String.valueOf(product.getPrice()));
                                    productList.add(dataMap);
                                }

                                populateListView(productList);
                            } else {
                                Toast.makeText(ProductListActivity.this, "Aucun produit trouvé", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ProductListActivity.this, "Erreur de chargement de produits", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(ProductListActivity.this, "Erreur de chargement de produits", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<HashMap<String, String>> dataList) {

        // Create the Adpater to link the data on the ListView
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "price"},
                new int[]{android.R.id.text1, android.R.id.text2}
        )
        {
            @Override
            public void setViewText(TextView v, String text) {
                if (v.getId() == android.R.id.text2) {
                    text = text + " CFA"; // adding  CFA on the price
                }
                super.setViewText(v, text);
            }
        };

        // define the adapter on the ListView
        lvProducts.setAdapter(adapter);
    }
}