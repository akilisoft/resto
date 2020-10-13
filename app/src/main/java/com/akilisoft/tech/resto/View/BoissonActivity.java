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

import static com.akilisoft.tech.resto.MainActivity.arrayList;

public class BoissonActivity extends AppCompatActivity implements ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    ProductAdapter productAdapter;
    public static ArrayList<ProductModel> arrayListBoisson = new ArrayList<>();
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boisson);

        if(arrayListBoisson.isEmpty())
            arrayListBoisson= Data.getBoisson();

        productAdapter = new ProductAdapter(arrayListBoisson, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
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

    private void addBoisson() {
        ProductModel productModel = new ProductModel("Coca", "10", "20", R.drawable.coca);
        arrayListBoisson.add(productModel);
        ProductModel productModel1 = new ProductModel("Dafani", "20", "10", R.drawable.dafani);
        arrayListBoisson.add(productModel1);
        ProductModel productModel2 = new ProductModel("XXL", "30", "10", R.drawable.xxl);
        arrayListBoisson.add(productModel2);
    }
}
