package com.akilisoft.tech.resto.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.akilisoft.tech.resto.Adapter.ProductAdapter;
import com.akilisoft.tech.resto.Model.Data;
import com.akilisoft.tech.resto.Model.ProductModel;
import com.akilisoft.tech.resto.R;

import java.util.ArrayList;


public class PlatsActivity extends AppCompatActivity implements ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    ProductAdapter productAdapter;
    public ArrayList<ProductModel> arrayListPlats = new ArrayList<>();
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plats);

        if(arrayListPlats.isEmpty())
            arrayListPlats= Data.getBoisson();

        productAdapter = new ProductAdapter(arrayListPlats, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(mLayoutManager);
        productRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void addCartItemView() {
        //addItemToCartMethod();

    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

}
