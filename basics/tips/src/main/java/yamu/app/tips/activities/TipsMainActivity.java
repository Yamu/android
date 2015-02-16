package yamu.app.tips.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

import yamu.app.tips.R;


public class TipsMainActivity extends ActionBarActivity {

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double customPercent = 0.18;

    private EditText editTxtAmount;
    private TextView txtViewPctCustomLabel;
    private TextView txtViewPct15Tip;
    private TextView txtViewPct15Total;
    private TextView txtViewPctCustomTip;
    private TextView txtViewPctCustomTotal;
    private SeekBar seekBarTipPercentage;

    private AmountTextWatcher amountWatcher;
    private TipsSeekBarChangeListener tipsSeekBarListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_main);

        txtViewPctCustomLabel = (TextView) findViewById(R.id.txtViewPctCustomLabel);
        txtViewPct15Tip = (TextView) findViewById(R.id.txtViewPct15Tip);
        txtViewPct15Total = (TextView) findViewById(R.id.txtViewPct15Total);
        txtViewPctCustomTip = (TextView) findViewById(R.id.txtViewPctCustomTip);
        txtViewPctCustomTotal = (TextView) findViewById(R.id.txtViewPctCustomTotal);

        editTxtAmount = (EditText) findViewById(R.id.editTxtAmount);
        seekBarTipPercentage = (SeekBar) findViewById(R.id.seekBarTipPercentage);

        txtViewPctCustomLabel.setText(percentFormat.format(customPercent));

        updateStandardTip();
        updateCustomTip();

        amountWatcher = new AmountTextWatcher();
        editTxtAmount.addTextChangedListener(amountWatcher);
        tipsSeekBarListener = new TipsSeekBarChangeListener();
        seekBarTipPercentage.setOnSeekBarChangeListener(tipsSeekBarListener);
    }

    private void updateStandardTip() {
        double tip = billAmount * 0.15;
        double total = billAmount + tip;

        txtViewPct15Tip.setText(currencyFormat.format(tip));
        txtViewPct15Total.setText(currencyFormat.format(total));
    }

    private void updateCustomTip() {
        double tip = billAmount * customPercent;
        double total = billAmount + tip;

        txtViewPctCustomTip.setText(currencyFormat.format(tip));
        txtViewPctCustomTotal.setText(currencyFormat.format(total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tips_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AmountTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                billAmount = 0.0;
            }

            if (billAmount < 0.0) {
                billAmount = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            updateStandardTip();
            updateCustomTip();

        }
    }

    private class TipsSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            customPercent = progress / 100.0;
            updateCustomTip();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


}
