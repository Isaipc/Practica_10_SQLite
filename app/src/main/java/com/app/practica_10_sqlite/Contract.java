package com.app.practica_10_sqlite;

public class Contract {


    interface TABLES {
        String PRODUCT = "Product";
    }

    interface PRODUCT {
        String CODE = "code";
        String DESCRIPTION = "description";
        String PRICE = "price";
    }

    public static final String CREATE_PRODUCT =
            "CREATE TABLE " + TABLES.PRODUCT + "(" +
                    PRODUCT.CODE + " INT PRIMARY KEY," +
                    PRODUCT.DESCRIPTION + " TEXT," +
                    PRODUCT.PRICE + " REAL)";

    public static final String DROP_PRODUCT =
            "DROP TABLE" + TABLES.PRODUCT;

    public static final String WHERE_CODE = PRODUCT.CODE + " = ?";

    public static final String SELECT_PRODUCT_BY_CODE =
            "SELECT description, price FROM " + Contract.TABLES.PRODUCT + " WHERE " +
                    Contract.PRODUCT.CODE + " = ?";
    public static final String SELECT_PRODUCT_BY_DESC =
            "SELECT code, price FROM " + Contract.TABLES.PRODUCT + " WHERE " +
                    PRODUCT.DESCRIPTION + " = ?";
}
