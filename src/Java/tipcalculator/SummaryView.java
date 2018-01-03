package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

/**
 * To return a summary view.
 */
public class SummaryView extends Activity{

    private TextView summaryText;
    TipCalculatorDbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_summary_view);

        summaryText = (TextView)findViewById(R.id.summarytextView);

        db = new TipCalculatorDbAdapter(this);

        db.open();
        summaryText.setText(db.dataSummary().toString());

        db.close();
    }
}
