package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

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
        EditText text = (EditText)findViewById(R.id.enterName);
        String value = text.getText().toString();
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_cream_checkBox);
        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkBox);
         boolean hasWhippedCream =whippedCream.isChecked();
         boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
//        Log.v("MainActivity","The price is "+ price);
        String priceMessage = createOrderSummary(value,price, hasWhippedCream, hasChocolate);
//        displayMessage(priceMessage);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+ value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
   private String createOrderSummary(String name,int price, boolean addWhippedCream, boolean addChocolate){
       String priceMessage = "Name: "+ name;
       priceMessage += "\nAdd whipped cream? " + addWhippedCream;
       priceMessage += "\nAdd chocolate? "+ addChocolate;
       priceMessage = priceMessage + "\nQuantity: "+ quantity;
       priceMessage = priceMessage+"\nTotal: $"+ price;
       priceMessage = priceMessage +"\n"+getString(R.string.thank_you);
       return priceMessage;

    }


    public int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream){
            basePrice += 1;
        }
        if(addChocolate){
            basePrice += 2;
        }

        int price = quantity * basePrice;
        return price;
    }

//    private void displayMessage(String priceMessage) {
//        TextView orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(priceMessage);
//    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view) {
        if(quantity == 10){
            Toast.makeText(this, "You cannot have more tha 1o coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
}
