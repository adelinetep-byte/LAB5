package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView idView;
    EditText productBox;
    EditText skuBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        skuBox = (EditText) findViewById(R.id.productSku);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void newProduct (View view) {

        int sku;
        try {
            sku = Integer.parseInt(skuBox.getText().toString());
        } catch (NumberFormatException e) {
            idView.setText("Invalid SKU: please enter a number");
            return;
        }

        if (!ProductValidator.isValidSku(sku)) {
            idView.setText("Invalid SKU: must be positive");
            return;
        }

        Product product = new Product(productBox.getText().toString(), sku);

        // TODO: add to database

        MyDBHandler dbHandler = new MyDBHandler(this);
        dbHandler.addProduct(product);
        productBox.setText("");
        skuBox.setText("");
    }
    public void lookupProduct (View view) {

        // TODO: get from Database
        MyDBHandler dbHandler = new MyDBHandler(this);
        Product product = dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));
            skuBox.setText(String.valueOf(product.getSku()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct (View view) {

        // TODO: remove from database
        MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            skuBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }
    public void updateProduct (View view) {

        int sku;
        try {
            sku = Integer.parseInt(skuBox.getText().toString());
        } catch (NumberFormatException e) {
            idView.setText("Invalid SKU: please enter a number");
            return;
        }

        if (!ProductValidator.isValidSku(sku)) {
            idView.setText("Invalid SKU: must be positive");
            return;
        }

        Product product = new Product(productBox.getText().toString(), sku);

        MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.updateProduct(product);

        if (result) {
            idView.setText("Record Updated");
        }
        else
            idView.setText("No Match Found");
    }

    public void about(View view) {
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }
}