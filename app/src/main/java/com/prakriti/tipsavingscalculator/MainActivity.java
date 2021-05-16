package com.prakriti.tipsavingscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView txtTipPercent, txtTipAmount, txtTotalToPay, txtSavingsPercent, txtTotalSavings;
    EditText edtBillAmount, edtSalary;
    SeekBar sbTipPercent, sbSavingPercent;

    private double billAmount = 0.0, tipPercent = 0.20;
    private double totalSalary = 0.0, savingsPercent = 0.25;

    private static final NumberFormat currencyFormatValue = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue = NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTipPercent = findViewById(R.id.txtTipPercent);
        txtTipAmount = findViewById(R.id.txtTipAmount);
        txtTotalToPay = findViewById(R.id.txtTotalToPay);
        txtSavingsPercent = findViewById(R.id.txtSavingsPercent);
        txtTotalSavings = findViewById(R.id.txtTotalSavings);

        edtBillAmount = findViewById(R.id.edtBillAmount);
        edtSalary = findViewById(R.id.edtSalary);

        sbTipPercent = findViewById(R.id.sbTipPercent);
        sbSavingPercent = findViewById(R.id.sbSavingPercent);

        // --------------

        txtTipAmount.setText(currencyFormatValue.format(0));
        txtTotalToPay.setText(currencyFormatValue.format(0));
        txtTotalSavings.setText(currencyFormatValue.format(0));

        // ------ TIP CALC --------

        edtBillAmount.addTextChangedListener(billAmountChangedTextWatcher);
        sbTipPercent.setOnSeekBarChangeListener(tipPercentChangedListener);

        // ------ SAVINGS CALC -------

        edtSalary.addTextChangedListener(salaryChangedTextWatcher);
        sbSavingPercent.setOnSeekBarChangeListener(savingsPercentChangedListener);

    }

    // TIP

    private final TextWatcher billAmountChangedTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString());
                txtTotalToPay.setText(currencyFormatValue.format(billAmount));
            } catch (NumberFormatException e) {
                txtTotalToPay.setText("");
                billAmount = 0.0;
            }
            calculateTip();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final SeekBar.OnSeekBarChangeListener tipPercentChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            tipPercent = progress / 100.0;
            calculateTip();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private void calculateTip() {
        txtTipPercent.setText(percentFormatValue.format(tipPercent));
        double tipValue = tipPercent * billAmount;
        double totalValue = billAmount + tipValue;
        txtTipAmount.setText(currencyFormatValue.format(tipValue));
        txtTotalToPay.setText(currencyFormatValue.format(totalValue));
    }

    // SAVINGS

    private final TextWatcher salaryChangedTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                totalSalary  = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                totalSalary = 0.0;
            }
            calculateSavings();
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final SeekBar.OnSeekBarChangeListener savingsPercentChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            savingsPercent = progress / 100.0;
            calculateSavings();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private void calculateSavings() {
        txtSavingsPercent.setText(percentFormatValue.format(savingsPercent));
        double savingsValue = savingsPercent * totalSalary;
        txtTotalSavings.setText(currencyFormatValue.format(savingsValue));
    }
}