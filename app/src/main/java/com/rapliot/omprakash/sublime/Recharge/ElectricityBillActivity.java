package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rapliot.omprakash.sublime.R;

public class ElectricityBillActivity extends AppCompatActivity {
    ListView listViewState,listViewBoard;
    EditText txtState,txtBoard,txtServiceNumber;
    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Electricity Bill Payment");
        actionBar.show();
        listViewState = findViewById(R.id.listViewState);
        listViewBoard = findViewById(R.id.listViewBoard);
        txtState = findViewById(R.id.txtState);
        txtBoard = findViewById(R.id.txtBoard);
        txtServiceNumber = findViewById(R.id.txtServiceNumber);
        btnPay = findViewById(R.id.btnPay);
      /*  txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewState.setVisibility(View.VISIBLE);
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewState.setAdapter(adapter);
        listViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String State = (String) listViewState.getItemAtPosition(position);
                txtState.setText(State);
                listViewBoard.setVisibility(View.GONE);
            }
        });  */
    }
}
