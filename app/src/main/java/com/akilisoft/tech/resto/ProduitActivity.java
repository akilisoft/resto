package com.akilisoft.tech.resto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.akilisoft.tech.resto.Adapter.ProductAdapter;
import com.akilisoft.tech.resto.Model.ProductModel;

import java.util.ArrayList;

public class ProduitActivity extends AppCompatActivity {

    public static ArrayList<ProductModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    ProductAdapter productAdapter;
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);
    }
}
