package com.akilisoft.tech.resto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.akilisoft.tech.resto.Adapter.ProductAdapter;
import com.akilisoft.tech.resto.Model.Data;
import com.akilisoft.tech.resto.Model.ProductModel;
import com.akilisoft.tech.resto.View.BoissonActivity;
import com.akilisoft.tech.resto.View.CartActivity;
import com.akilisoft.tech.resto.View.PlatsActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ProductAdapter.CallBackUs, ProductAdapter.HomeCallBack {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3, R.drawable.image_5};

    public static ArrayList<ProductModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    ProductAdapter productAdapter;
    RecyclerView productRecyclerView;
    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA =1;

    CardView boisson_card, plats_card, grillade_card, dessert_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mesPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (arrayList.isEmpty())
        //addProduct();

            arrayList=Data.getAllProduit();

        productAdapter = new ProductAdapter(arrayList, this, this);
        productRecyclerView = findViewById(R.id.product_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        productRecyclerView.setLayoutManager(gridLayoutManager);
        productRecyclerView.setAdapter(productAdapter);

        boisson_card = findViewById(R.id.card_view_boisson);

        boisson_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), BoissonActivity.class));
            }
        });

        plats_card = findViewById(R.id.card_view_plat);
        plats_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),PlatsActivity.class));
            }
        });
    }


    private void mesPermission() {
        //01
        isPermissionStorage();
        //02

    }


    private void isPermissionStorage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_MEDIA);
            }
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "there is no item in cart", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, CartActivity.class));
                }
                break;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            // Handle the camera action
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addProduct() {

        ProductModel productModel2 = new ProductModel("Springroll", "30", "10", R.drawable.springrolls);
        arrayList.add(productModel2);

        ProductModel productModel3 = new ProductModel("burger", "40", "20", R.drawable.burger);
        arrayList.add(productModel3);
        ProductModel productModel12 = new ProductModel("chicken", "50", "10", R.drawable.chicken);
        arrayList.add(productModel12);
        ProductModel productModel23 = new ProductModel("colddrink", "60", "10", R.drawable.colddrink);
        arrayList.add(productModel23);

        ProductModel productModel4 = new ProductModel("momos", "70", "20", R.drawable.momos);
        arrayList.add(productModel4);
        ProductModel productModel14 = new ProductModel("noodles", "80", "10", R.drawable.noodles);
        arrayList.add(productModel14);
        ProductModel productModel25 = new ProductModel("pizza", "90", "10", R.drawable.pizza);
        arrayList.add(productModel25);

        ProductModel productModel5 = new ProductModel("roll", "100", "20", R.drawable.roll);
        arrayList.add(productModel5);
        ProductModel productModel16 = new ProductModel("sandwich", "200", "10", R.drawable.sandwich);
        arrayList.add(productModel16);

    }

    @Override
    public void addCartItemView() {
        //addItemToCartMethod();

    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_MEDIA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed
                    //mymethod(); //a sample method called

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }
}
