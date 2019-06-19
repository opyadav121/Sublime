package com.example.omprakash.sublime.Recharge;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.PaymentHistoryActivity;
import com.example.omprakash.sublime.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.Profile;
import Model.Recharge_History;


public class MobilePostpaidActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    RadioGroup RadioBtnGroup;
    EditText txtMobileNumber,txtOperator,txtAmount,transferAmount;
    TextView myContact,txtSWallet,txtEWallet,btnTransfer;
    ListView listViewOperator,ListRecharges;
    Button btnPayment,transfer;
    List<String> OperatorList= new ArrayList<>();
    HashMap<String,String> OperatorHashMap = new HashMap<>();
    ArrayAdapter<String> adapterOperator;
    String OPType;
    String RandomChildCode="";
    ProgressDialog progressDialog;
    NumberFormat currFormat;
    LinearLayout transfer_E_to_S;
    ArrayList<Recharge_History> rechargeList = new ArrayList<>();
    AdapterRecharge adapterRecharge;
    Profile myProfile;
    String SWallet_Balance,Ewalet_Balance,OperatorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_postpaid);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Mobile Recharge");
        actionBar.show();
        myProfile = Session.GetProfile(this);
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        transfer_E_to_S = findViewById(R.id.transfer_E_to_S);
        transferAmount = findViewById(R.id.transferAmount);
        transfer = findViewById(R.id.transfer);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_E_to_S.setVisibility(View.VISIBLE);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferAmount();
            }
        });
        ListRecharges = findViewById(R.id.ListRecharges);
        adapterRecharge =new AdapterRecharge(MobilePostpaidActivity.this,0,rechargeList);
        ListRecharges.setAdapter(adapterRecharge);
        RechargeHistory();
        ChildCode();
        WalletBalance();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        RadioBtnGroup = findViewById(R.id.RadioBtnGroup);
        RadioBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton =  findViewById(checkedId);
                OPType = checkedRadioButton.getText().toString();
            }
        });
        myContact = findViewById(R.id.myContact);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtOperator = findViewById(R.id.txtOperator);
        txtAmount = findViewById(R.id.txtAmount);
        listViewOperator = findViewById(R.id.listViewOperator);
        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recharge();
            }
        });
        myContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,0);
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS },0);
            return;
        }

        setOperatorSpinner();

    }

    public void setOperatorSpinner()
    {
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/moblie_recharge";
        try{
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        int x = response.length();
                        for (int i = 0; i <x; i++) {

                            JSONObject jObj = response.getJSONObject(i);

                            String OperatorName = jObj.getString("operator_name");
                            String OperatorID = jObj.getString("operator_code");
                            String optype = jObj.getString("OPType");
                          /*  OperatorListPostpaid.add(OperatorName);
                            if (optype.equalsIgnoreCase("PrePaid")) {
                                OperatorHashMap.put(OperatorName,OperatorID );
                                OperatorListPostpaid.add(OperatorName);
                            }else {
                                OperatorHashMap.put(OperatorName,OperatorID );
                                OperatorList.add(OperatorName);
                            } */
                            OperatorHashMap.put(OperatorName,OperatorID );
                            OperatorList.add(OperatorName);
                        }
                        adapterOperator.notifyDataSetChanged();

                    } catch (JSONException e) {
                        int a=1;
                    }
                    catch (Exception ex)
                    {
                        int a=1;
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // progressBar.setVisibility(View.GONE);
                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0, -1, 0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);
        }catch (Exception ex){
            int a=1;
        }

        adapterOperator = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, OperatorList);
        adapterOperator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewOperator.setAdapter(adapterOperator);
        txtOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listViewOperator.setVisibility(View.VISIBLE);

            }
        });

        listViewOperator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Operator = (String) listViewOperator.getItemAtPosition(position);
                txtOperator.setText(Operator);
                OperatorCode = OperatorHashMap.get(Operator);
                listViewOperator.setVisibility(View.GONE);
            }
        });
    }

  public String ChildCode(){
      int range = 9;
      int length = 4;
      String Child="TRID";
      SecureRandom secureRandom = new SecureRandom();
      for (int i = 0; i < length; i++) {
          int number = secureRandom.nextInt(range);
          if (number == 0 && i == 0) {
              i = -1;
              continue;
          }
          Child = Child + number;
      }
      return Child;
  }

  public void Recharge(){
      RandomChildCode = ChildCode() + "A";
     final int Remain = Integer.parseInt(SWallet_Balance) - Integer.parseInt(txtAmount.getText().toString());

      String Recharge_url= "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/API_recharge";
      progressDialog = progressDialog.show(MobilePostpaidActivity.this, "", "Please wait...", false, false);
      StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>()  {
          @Override
          public void onResponse(String response) {
              progressDialog.dismiss();
              try {
                      JSONObject jObj = new JSONObject(response);
                      String Status = jObj.getString("Status");
                      Toast.makeText(MobilePostpaidActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                          String RandomChildCode= jObj.getString("Yourrchid");
                          String Error = jObj.getString("Errormsg");
                          String Remaining = jObj.getString("Remain");
                         // String RandomChildCode = jObj.getString("Yourrchid");
                          String RechargeID = jObj.getString("RechargeID");

                          Intent confirmation = new Intent(MobilePostpaidActivity.this, PaymentHistoryActivity.class);
                          confirmation.putExtra("Yourrchid", RandomChildCode);
                          confirmation.putExtra("Errormsg",Error);
                          confirmation.putExtra("Remain",Remaining);
                          confirmation.putExtra("Status",Status);
                          confirmation.putExtra("RechargeID",RechargeID);
                          startActivity(confirmation);
                      MobilePostpaidActivity.this.finish();

              } catch (JSONException e) {
                  e.printStackTrace();
                  progressDialog.dismiss();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              progressDialog.dismiss();
              Toast.makeText(MobilePostpaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
          }
      })
      {
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<>();
              params.put("email", myProfile.UserLogin);
              params.put("Customernumber", txtMobileNumber.getText().toString());
              params.put("Yourrchid",RandomChildCode);
              params.put("Optname",txtOperator.getText().toString());
              params.put("Optcode","A");
              params.put("operatorname",txtOperator.getText().toString());
              params.put("wallet_bal",SWallet_Balance);
              params.put("remaining_bal",Integer.toString(Remain));
              params.put("Amount",txtAmount.getText().toString());
              params.put("date","18/06/2019");
              return params;
          }
      };
      requestQueue.add(stringRequest);
  }

  public void WalletBalance(){
      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      String Wallet_url= "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/wallet";
      progressDialog = progressDialog.show(MobilePostpaidActivity.this, "", "Please wait...", false, false);
      StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
          @Override
          public void onResponse(String response) {
              progressDialog.dismiss();
              try {
                  currFormat = NumberFormat.getCurrencyInstance();
                  currFormat.setCurrency(Currency.getInstance("INR"));
                  JSONObject jObj = new JSONObject(response);
                  Ewalet_Balance = jObj.getString("E-Wallet");
                  SWallet_Balance = jObj.getString("S-Wallet");
                  txtEWallet.setText(Ewalet_Balance);
                  txtSWallet.setText(SWallet_Balance);
                  //txtEWallet.setText(currFormat.format(EWallet));
                  //txtSWallet.setText(currFormat.format(SWallet));

              } catch (JSONException e) {
                  e.printStackTrace();
                  progressDialog.dismiss();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              progressDialog.dismiss();
              Toast.makeText(MobilePostpaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
          }
      })
      {
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<>();
              params.put("email", myProfile.UserLogin);
              return params;
          }
      };
      queue.add(stringRequest);
  }

    public void TransferAmount(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Transfer_url= "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/add_money_s_wallet";
        progressDialog = progressDialog.show(MobilePostpaidActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    String Status = jObj.getString("status");
                    String MSG = jObj.getString("msg");
                    Toast.makeText(MobilePostpaidActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                    transfer_E_to_S.setVisibility(View.GONE);
                    WalletBalance();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MobilePostpaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                params.put("amount",transferAmount.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void RechargeHistory()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/history";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Recharge_History recharge = new Recharge_History();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        recharge.MobileNO = jObj.getString("mob_no");
                        recharge.Operator = jObj.getString("operator");
                        recharge.Amount = jObj.getString("amount");
                        recharge.Date = jObj.getString("date");
                        recharge.Status = jObj.getString("status");
                        recharge.TransId = jObj.getString("transaction_id");
                        recharge.RemainingBal = jObj.getString("remaining_bal");
                        recharge.WalletBal = jObj.getString("wallet_bal");
                        rechargeList.add(recharge);
                    }
                    adapterRecharge.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MobilePostpaidActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", myProfile.UserLogin);
                return params;
            }
        };
        queue.add(stringRequest);
    }

  //--------------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri=data.getData();
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        while (cursor.moveToNext())
        {
            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)
            {
                Cursor c=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",
                        new String[]{cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))}, null);
                while (c.moveToNext())
                {
                    String phone=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    txtMobileNumber.setText(phone);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class AdapterRecharge extends ArrayAdapter<Recharge_History>{
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterRecharge(Context context, int resource, List<Recharge_History> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return rechargeList.size();
        }

        @Nullable
        @Override
        public Recharge_History getItem(int position) {
            return rechargeList.get(position);
        }

        @Override
        public int getPosition(Recharge_History item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_rechargehistory, null);
                    holder = new ViewHolder();
                    holder.histMobile = convertView.findViewById(R.id.histMobile);
                    holder.histOperator = convertView.findViewById(R.id.histOperator);
                    holder.histAmount = convertView.findViewById(R.id.histAmount);
                    holder.histOrderId = convertView.findViewById(R.id.histOrderId);
                    holder.histDate = convertView.findViewById(R.id.histDate);
                    holder.histStatus = convertView.findViewById(R.id.histStatus);
                    holder.hisTransId = convertView.findViewById(R.id.hisTransId);
                    holder.OperatorIcon = convertView.findViewById(R.id.OperatorIcon);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Recharge_History history= getItem(position);
                holder.histMobile.setText(history.MobileNO);
                holder.histOperator.setText(history.Operator);
                holder.histAmount.setText(history.Amount );
                holder.histOrderId.setText(history.OrderId);
                holder.histDate.setText(history.Date);
                holder.histStatus.setText(history.Status );
                holder.hisTransId.setText(history.TransId);
                holder.OperatorIcon.setVisibility(View.VISIBLE);
                String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/idea.jpg";
                Picasso.with(getApplicationContext()).load(url1).into(holder.OperatorIcon);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load RentData", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

    private class ViewHolder
    {
        TextView histMobile,histOperator,histAmount,histOrderId,histDate,histStatus,hisTransId;
        ImageView OperatorIcon;
    }

}
