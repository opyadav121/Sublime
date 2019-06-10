package com.example.omprakash.sublime;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Model.Profile;

public class RegisterActivity extends AppCompatActivity {

    EditText txtUseName,txtMobile,txtPassword,txtReferalCode;
    RadioGroup radioGroup;
    RadioButton radioLeft,radioRight;
    Button btnSubmit;
    CheckBox checkboxPrivacy;

    Profile newRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(" Register ");
        actionBar.show();

        txtUseName = findViewById(R.id.txtUseName);
        txtMobile = findViewById(R.id.txtMobile);
        txtPassword = findViewById(R.id.txtPassword);
        txtReferalCode = findViewById(R.id.txtReferalCode);
        radioGroup = findViewById(R.id.radioGroup);
        radioLeft = findViewById(R.id.radioLeft);
        radioRight = findViewById(R.id.radioLeft);
        checkboxPrivacy = findViewById(R.id.checkboxPrivacy);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    public void ValidData(){
        newRegister = new Profile();
        newRegister.MobileNumber = txtMobile.getText().toString();
        if (newRegister.MobileNumber.equals("") ) {
            txtMobile.setError("Please Enter Mobile No.");
            return;
        }
        newRegister.UserLogin = txtUseName.getText().toString();
        if (newRegister.UserLogin.equals("") ) {
            txtUseName.setError("Please Enter Email");
            return;
        }
        newRegister.UserPassword = txtPassword.getText().toString();
        if (newRegister.UserPassword.equals("") ) {
            txtPassword.setError("Please Enter First Name");
            return;
        }
        newRegister.refer_Id = txtReferalCode.getText().toString();
        if (newRegister.refer_Id.equals("") ) {
            txtReferalCode.setError("Please Enter Last Name");
            return;
        }

        UserValidate();
    }

    private void UserValidate()
    {

        String url = "http://recharge4ushop.online/Recharge/Recharge";
        try {
            String reqBody = "{\"Email\":\"" +newRegister.UserLogin+"\",\"Mobile\":\""+ newRegister.MobileNumber+ "\", \"Password\":\"Password@123\",  \"RegistrationID\":\"\"}";


            JSONObject jsRequest = new JSONObject(reqBody);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, url,jsRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String result = response.getString("result");
                        if (result.matches("Fail")) {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }else {
                            JSONObject userData = response.getJSONObject("UserData");
                            Profile user = new Profile();
                        /*    user.NAME = userData.getString("FirstName") + " " + userData.getString("LastName");
                            user.MOB_NUMBER = userData.getString("MobileNo");
                            user.UserID = userData.getString("UserID");
                            user.E_MAIL = userData.getString("EmailId");
                            user.Gender = userData.getString("Gender");
                            user.ParentName = userData.getString("Parentname");
                            user.LOCATION = userData.getString("Address");

                            NewRegisterActivity.this.finish();  */
                        }

                    } catch (JSONException je) {

                        btnSubmit.setEnabled(true);
                    }
                   // progressBar.setVisibility(View.GONE);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  //  progressBar.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);
            //*******************************************************************************************************
        }
        catch (JSONException jex)
        {
            int a=1;
        }

        catch (Exception js) {
            int a=1;
        } finally {

        }

    }
}
