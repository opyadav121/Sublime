package Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.LoginActivity;
import com.example.omprakash.sublime.R;
import com.example.omprakash.sublime.RegisterActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PersonalFragment extends Fragment {
    RequestQueue requestQueue;
    EditText txtName,txtEmail,txtMobile,txtGender,txtDob,txtMotherName,txtFatherName,txtNomineeName,
            txtNomineeRelation,txtOccupation,txtPan,txtAadhar,txtAddress,txtPincode,txtState,txtDistrict;
    Button btnSubmit;
    ProgressDialog progressDialog;
    String Edit_url="http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/updateprofile";

    public PersonalFragment() {


        UpdateProfile();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtEmail =view.findViewById(R.id.txtEmail);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtGender = view.findViewById(R.id.txtGender);
        txtDob =view.findViewById(R.id.txtDob);
        txtMotherName = view.findViewById(R.id.txtMotherName);
        txtFatherName = view.findViewById(R.id.txtFatherName);
        txtNomineeName =view.findViewById(R.id.txtNomineeName);
        txtNomineeRelation = view.findViewById(R.id.txtNomineeRelation);
        txtOccupation = view.findViewById(R.id.txtOccupation);
        txtPan =view.findViewById(R.id.txtPan);
        txtAadhar = view.findViewById(R.id.txtAadhar);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPincode = view.findViewById(R.id.txtPincode);
        txtState =view.findViewById(R.id.txtState);
        txtDistrict = view.findViewById(R.id.txtDistrict);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        requestQueue =  Volley.newRequestQueue(getContext());
        return view;

    }

    public void UpdateProfile(){
        try {
            progressDialog = progressDialog.show(getContext(), "", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Edit_url, new Response.Listener<String>()  {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jObj = new JSONObject(response);
                        //Toast.makeText(RegisterActivity.this, ""+jObj.getString("msg"), Toast.LENGTH_SHORT).show();
                        String UserId = jObj.getString("user_id");
                        String password = jObj.getString("Password");
                        String Status = jObj.getString("msg");
                    } catch (Exception e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name",txtName.getText().toString());
                    params.put("email", txtEmail.getText().toString());
                    params.put("occupation", txtOccupation.getText().toString());
                    params.put("mobile", txtMobile.getText().toString());
                    params.put("father_name", txtFatherName.getText().toString());
                    params.put("mother_name", txtMotherName.getText().toString());
                    params.put("dob", txtDob.getText().toString());
                    params.put("state", txtState.getText().toString());
                    params.put("pan_no", txtPan.getText().toString());
                    params.put("district", txtDistrict.getText().toString());
                    params.put("pincode", txtPincode.getText().toString());
                    params.put("address", txtAddress.getText().toString());
                    params.put("nominee", txtNomineeName.getText().toString());
                    params.put("nominee_relation", txtNomineeRelation.getText().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }catch (Exception e){
            int a = 1;
        }
    }

}
