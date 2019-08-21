package com.sublime.sublimecash.sublime;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AEPSActivity extends AppCompatActivity {
    EditText txtAadhar,txtBankName,txtAmount;
    ListView ListViewBank;
    Spinner spBank;
    String Bank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeps);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Aeps ");
        actionBar.show();
        txtAadhar = findViewById(R.id.txtAadhar);
        txtAmount = findViewById(R.id.txtAmount);
        txtBankName = findViewById(R.id.txtBankName);
        ListViewBank = findViewById(R.id.ListViewBank);
        spBank = findViewById(R.id.spBank);

        txtBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ListViewBank.setVisibility(View.VISIBLE);
                spBank.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.BankName, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        ListViewBank.setAdapter(adapter);
        ListViewBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Bank  = parent.getItemAtPosition(position).toString();
                txtBankName.setText(Bank);
               // ListViewBank.setVisibility(View.GONE);
                spBank.setVisibility(View.GONE);
                //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }
}
