package com.akilisoft.tech.resto.Model;

import com.akilisoft.tech.resto.R;

import java.util.ArrayList;

/**
 * Copyright (c) 2020, NIKISS. All Rights Reserved.
 * <p>
 * Save to the extent permitted by law, you may not use, copy, modify, distribute or
 * create derivative works of this material or any part of it without the prior
 * written consent of  OUEDRAOGO ISSOUF NIKISS.email:ouedraogo.nikiss@gmail.com
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the software.
 * Created on 07,octobre,2020
 */
public class Data {

    public static ArrayList<ProductModel> getAllProduit(){
        ArrayList<ProductModel> arrayList = new ArrayList<>();

        arrayList.addAll(getPlats());
        arrayList.addAll(getBoisson());
        return arrayList;
    }

    public static ArrayList<ProductModel> getPlats(){
        ArrayList<ProductModel> arrayList = new ArrayList<>();

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
        return arrayList;
    }

    public static ArrayList<ProductModel> getBoisson(){
        ArrayList<ProductModel> arrayListBoisson = new ArrayList<>();

        ProductModel productModel = new ProductModel("Coca", "10", "20", R.drawable.coca);
        arrayListBoisson.add(productModel);
        ProductModel productModel1 = new ProductModel("Dafani", "20", "10", R.drawable.dafani);
        arrayListBoisson.add(productModel1);
        ProductModel productModel2 = new ProductModel("XXL", "30", "10", R.drawable.xxl);
        arrayListBoisson.add(productModel2);
        return arrayListBoisson;
    }


}
