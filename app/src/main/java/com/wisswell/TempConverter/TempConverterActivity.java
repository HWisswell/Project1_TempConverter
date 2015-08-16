package com.wisswell.TempConverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.inputmethod.EditorInfo;
import android.view.View.OnClickListener;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements OnEditorActionListener {

    //define variables for the widgets
    private EditText amountEditText;
    private TextView conversionTextView;

    //define the SharedPreferences object
    private SharedPreferences savedValues;

    //define instance variable that should be saved
    private String amountEditString = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get references to the widgets
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        conversionTextView = (TextView) findViewById(R.id.conversionTextView);

        //set the listeners
        amountEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("amountString", amountEditString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //get the instance variables
        amountEditString = savedValues.getString("amountString", "");


        //set bill amount on its widget
        amountEditText.setText(amountEditString);

        //call calculate and display
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        if(i == EditorInfo.IME_ACTION_DONE ||
                i == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }


    public void calculateAndDisplay() {

        amountEditString = amountEditText.getText().toString();
        float conversionAmount;
        if(amountEditString.equals("")){
            conversionAmount = 0;
        }
        else{
            conversionAmount = Float.parseFloat(amountEditString);
        }

        //calculate celcius
        float celciusAmount = (conversionAmount - 32) * 5 / 9 ;


        //display the result with formatting
        NumberFormat currency = NumberFormat.getNumberInstance();
        conversionTextView.setText(currency.format(celciusAmount));



    }
}
