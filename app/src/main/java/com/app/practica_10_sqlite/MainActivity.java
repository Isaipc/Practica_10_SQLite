package com.app.practica_10_sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    EditText et_code;
    EditText et_description;
    EditText et_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_code = findViewById(R.id.et_code);
        et_description = findViewById(R.id.et_description);
        et_price = findViewById(R.id.et_price);
    }

    public void add(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Product product = new Product();

        if (isEmptyCode())
            return;
        product.code = Integer.parseInt(et_code.getText().toString());

        if(isEmptyDescription())
            return;
        product.description = et_description.getText().toString();

        if(isEmptyPrice())
            return;
        product.price = Float.parseFloat(et_price.getText().toString());

        long result = db.insert(Contract.TABLES.PRODUCT, null, product.toValues());

        if(result > 0)
        {
            snack("Se guardó el item", R.color.colorSuccess);
            clear();
        }
        else
            snack("Ocurrió un error", R.color.colorError);

        db.close();
    }

    public void searchByCode(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        Product product = new Product();

        if (isEmptyCode())
            return;
        product.code = Integer.parseInt(et_code.getText().toString());

        Cursor result = db.rawQuery(Contract.SELECT_PRODUCT_BY_CODE,
                new String [] { String.valueOf( product.code )});

        if(result.moveToFirst())
        {
            et_description.setText(result.getString(0));
            et_price.setText(result.getString(1));
            snack("Se encontró el item", R.color.colorSuccess);
        }
        else
            snack("No se encontró el item", R.color.colorError);

        result.close();
        db.close();
    }

    public void searchByDescription(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();
        Product product = new Product();

        if(isEmptyDescription())
            return;
        product.description = et_description.getText().toString();


        Cursor result = db.rawQuery(Contract.SELECT_PRODUCT_BY_DESC,
                new String [] { String.valueOf( product.description )});

        if(result.moveToFirst())
        {
            et_code.setText(result.getString(0));
            et_price.setText(result.getString(1));
            snack("Se encontró el item", R.color.colorSuccess);
        }
        else
            snack("No se encontró el item", R.color.colorError);

        result.close();
        db.close();
    }


    public void remove(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Product product = new Product();

        if (isEmptyCode())
            return;
        product.code = Integer.parseInt(et_code.getText().toString());

        int result = db.delete(Contract.TABLES.PRODUCT,
                Contract.WHERE_CODE,
                new String [] { String.valueOf( product.code )});

        if(result > 0)
        {
            snack("Se eliminó el item", R.color.colorSuccess);
            clear();
        }
        else
            snack("No se encontró el item", R.color.colorError);


        db.close();
        clear();
    }

    public void edit(View view)
    {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Product product = new Product();

        if (isEmptyCode())
            return;
        product.code = Integer.parseInt(et_code.getText().toString());

        if(isEmptyDescription())
            return;
        product.description  = et_description.getText().toString();

        if(isEmptyPrice())
            return;
        product.price = Float.parseFloat(et_price.getText().toString());


        int result = db.update(Contract.TABLES.PRODUCT,
                product.toValues(),
                Contract.WHERE_CODE,
                new String [] { String.valueOf( product.code )});

        if(result > 0)
        {
            snack("Se guardó el item", R.color.colorSuccess);
            clear();
        }
        else
            snack("Ocurrió un error", R.color.colorError);

        db.close();
    }

    private boolean isEmptyCode() {
        if (et_code.getText().toString().isEmpty()) {
            snack("Debe agregar un código", R.color.colorError);
            et_code.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEmptyDescription() {
        if (et_description.getText().toString().isEmpty()) {
            snack("Debe agregar un description" , R.color.colorError);

            et_description.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEmptyPrice() {
        if (et_price.getText().toString().isEmpty()) {
            snack("Debe agregar un precio" , R.color.colorError);
            et_price.requestFocus();
            return true;
        }
        return false;
    }

    private void clear() {
        et_code.setText("");
        et_description.setText("");
        et_price.setText("");
    }


    private void snack(String msg, int bgColorId)
    {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content),
                msg,
                Snackbar.LENGTH_SHORT);
        View view = snack.getView();
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//        params.gravity = Gravity.TOP;
//        view.setLayoutParams(params);

        view.setBackgroundColor(ContextCompat.getColor(getBaseContext(), bgColorId));
        hideKeyboardwithoutPopulate();
        snack.show();
    }

    public void hideKeyboardwithoutPopulate() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
    }

}
