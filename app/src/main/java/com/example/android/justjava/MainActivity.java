package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameEditText = (EditText) findViewById(R.id.name_edittext);
        String name = nameEditText.getText().toString();


        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox =(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate =  chocolateCheckBox.isChecked();

       // Log.v("MainActivity","Name : " + nameEditText);

        int price = calculatePrice(hasWhippedCream,hasChocolate);


        createOrderSummary(price,hasWhippedCream,hasChocolate,name);

    }

    /**
     * This method calculate the price
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){

        // Price of 1 cup of coffe
        int basePrice = 5;

        // Add $1 if the user wants Wnipped Cream
        if (hasWhippedCream){
            basePrice = basePrice +1;
        }

        // Add $2 if the user wants chocolate
        if (hasChocolate){
            basePrice = basePrice +2;
        }
        //Calculate the total order price
        return quantity * basePrice;
    }

    /**
     * This method create a summary order
     *
     * @return text summary
     */
    private void createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String message =  getString(R.string.order_summary_name,name);
        message = message + "\n" + getString(R.string.order_summary_cream) + hasWhippedCream;
        message = message + "\n" + getString(R.string.order_summary_chocolate) + hasChocolate;
        message = message + "\n" + getString(R.string.order_summary_quantity) + quantity;
        message = message + "\n" + getString(R.string.order_summary_total) + NumberFormat.getCurrencyInstance().format(price);
        message = message + "\n" + getString(R.string.thank_you);
        composeEmail(message,name);
    }


    public void composeEmail(String message,String subject){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view){

        if(quantity == 100){
            //Show message error
            Toast.makeText(this,"You cannot have more than 100 coffes", Toast.LENGTH_SHORT).show();
            // Exit this method early
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view){

        if(quantity ==1){
            //Show message error
            Toast.makeText(this,"You cannot have less than 1 coffe", Toast.LENGTH_SHORT).show();
            // Exit this method early
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}