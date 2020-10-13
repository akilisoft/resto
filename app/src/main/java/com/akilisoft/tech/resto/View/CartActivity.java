package com.akilisoft.tech.resto.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akilisoft.tech.resto.Adapter.CartAdapter;
import com.akilisoft.tech.resto.MainActivity;
import com.akilisoft.tech.resto.Model.ProductImage;
import com.akilisoft.tech.resto.R;
import com.akilisoft.tech.resto.util.PdfUtils;
import com.itextpdf.text.DocumentException;

import static com.akilisoft.tech.resto.Adapter.ProductAdapter.cartModels;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    public static TextView grandTotal;
    public static int grandTotalplus;
    // create a temp list and add cartitem list
    public static ArrayList<ProductImage> temparraylist;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    LinearLayout proceedToBook;
    Context context;
    private Toolbar mToolbar;
    private PdfUtils pdfUtils;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
        temparraylist = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);
        pdfUtils = new PdfUtils(this,temparraylist,grandTotalplus,""+getDateTime());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cart");


        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // these lines of code for show the same  cart for future refrence
                grandTotalplus = 0;
                for (int i = 0; i < temparraylist.size(); i++) {

                }
                cartModels.addAll(temparraylist);
                MainActivity.cart_count = (temparraylist.size());
//                addItemInCart.clear();
                finish();
            }
        });
        MainActivity.cart_count = 0;

        //addInCart();

        Log.d("sizecart_1", String.valueOf(temparraylist.size()));
        Log.d("sizecart_2", String.valueOf(cartModels.size()));

        // from these lines of code we remove the duplicacy of cart and set last added quantity in cart
        // for replace same item
        for (int i = 0; i < cartModels.size(); i++) {
            for (int j = i + 1; j < cartModels.size(); j++) {
                if (cartModels.get(i).getProductImage().equals(cartModels.get(j).getProductImage())) {
                    cartModels.get(i).setProductQuantity(cartModels.get(j).getProductQuantity());
                    cartModels.get(i).setTotalCash(cartModels.get(j).getTotalCash());
                    cartModels.remove(j);
                    j--;
                    Log.d("remove", String.valueOf(cartModels.size()));

                }
            }

        }
        temparraylist.addAll(cartModels);
        cartModels.clear();
        Log.d("sizecart_11", String.valueOf(temparraylist.size()));
        Log.d("sizecart_22", String.valueOf(cartModels.size()));
        // this code is for get total cash
        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
        }
        grandTotal.setText("Rs. " + String.valueOf(grandTotalplus));
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartAdapter = new CartAdapter(temparraylist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(cartAdapter);


        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "choisir table", Toast.LENGTH_SHORT).show();
                //bookMyOrder();

                final Dialog dialog = new Dialog(CartActivity.this);
                dialog.setContentView(R.layout.dialog_client);
                dialog.setTitle("Title...");
                // set the custom dialog components - text, image and button
                LinearLayout mylinear =(LinearLayout) dialog.findViewById(R.id.dialog_table);
                //mylinear.getLayoutParams().width=(SCREEN_WIDTH*4)/5;
                TextInputLayout textInputLayoutNom = (TextInputLayout) findViewById(R.id.text_input_layout_nom);
                TextInputLayout textInputLayoutMontant = (TextInputLayout) findViewById(R.id.text_input_layout_table);
                EditText inputNom = (EditText) dialog.findViewById(R.id.input_nom_client);
                EditText inputTable = (EditText) dialog.findViewById(R.id.input_numero_table);
                Button dialogConfirmeButton = (Button) dialog.findViewById(R.id.buttonConfirmer);


                dialogConfirmeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("CONFIRME TRANSACTION");
                        //pdfUtils = new PdfUtils(CartActivity.this,temparraylist,grandTotalplus,""+getDateTime());
                        try {
                            pdfUtils.createPDF();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        grandTotalplus = 0;
        for (int i = 0; i < temparraylist.size(); i++) {
            MainActivity.cart_count = (temparraylist.size());

        }
        cartModels.addAll(temparraylist);
        //cartModels.clear();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}