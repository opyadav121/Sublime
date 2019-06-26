package com.rapliot.omprakash.sublime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String  Register_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/home/register/1";


    EditText txtUseName,txtMobile,txtPassword,txtReferalCode,txtName;
    RadioGroup radioGroup;
    RadioButton radioLeft,radioRight;
    Button btnSubmit;
    CheckBox checkboxPrivacy;
    String side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requestQueue = Volley.newRequestQueue(this);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(" Register ");
        actionBar.show();

        txtName = findViewById(R.id.txtName);
        txtUseName = findViewById(R.id.txtUseName);
        txtMobile = findViewById(R.id.txtMobile);
        txtPassword = findViewById(R.id.txtPassword);
        txtReferalCode = findViewById(R.id.txtReferalCode);
        radioGroup = findViewById(R.id.radioGroup);
        radioLeft = findViewById(R.id.radioLeft);
        radioRight = findViewById(R.id.radioLeft);
        checkboxPrivacy = findViewById(R.id.checkboxPrivacy);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton =  findViewById(checkedId);
                side = checkedRadioButton.getText().toString();
            }
        });
    }

    public void UserRegister(){
     try {
        progressDialog = progressDialog.show(RegisterActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Register_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                        JSONObject jObj = new JSONObject(response);
                        //Toast.makeText(RegisterActivity.this, ""+jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                        String UserId = jObj.getString("user_id");
                        String password = jObj.getString("Password");
                        String Status = jObj.getString("msg");

                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Status:   "+Status+"\nUser ID:   "+UserId+"\nPassword:  "+password);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            RegisterActivity.this.finish();
                        }
                    });
                    AlertDialog Alert = builder.create();
                    Alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",txtName.getText().toString());
                params.put("email", txtUseName.getText().toString());
                params.put("password", txtPassword.getText().toString());
                params.put("mobile", txtMobile.getText().toString());
                params.put("reffer_by", txtReferalCode.getText().toString());
                params.put("side", side);
                params.put("account", "null");
                params.put("join_user", "on");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }catch (Exception e){
         int a = 1;
     }
    }
}
