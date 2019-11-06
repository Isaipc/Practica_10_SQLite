package com.app.practica_10_sqlite;

import android.content.ContentValues;

public class Product
{
    public int code;
    public String description;
    public float price;

    public Product() { }

    public Product(int code, String description, float price) {
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public ContentValues toValues()
    {
        ContentValues values = new ContentValues();
        values.put(Contract.PRODUCT.CODE, code);
        values.put(Contract.PRODUCT.DESCRIPTION, description);
        values.put(Contract.PRODUCT.PRICE, price);

        return values;
    }
}
