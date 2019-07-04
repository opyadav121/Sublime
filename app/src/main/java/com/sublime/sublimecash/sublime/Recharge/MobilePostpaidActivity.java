package com.sublime.sublimecash.sublime.Recharge;
import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.PaymentHistoryActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.Oparater;
import Model.Profile;
import Model.Recharge_History;
import de.hdodenhof.circleimageview.CircleImageView;


public class MobilePostpaidActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    RadioGroup RadioBtnGroup;
    RadioButton rbPrepaid,rbPostpaid;
    EditText txtMobileNumber,txtOperator,txtAmount;
    TextView myContact,txtSWallet,txtEWallet,btnTransfer,txtrecentRecharge;
    ListView listViewPostpaid,listViewPrepaid,ListRecharges;
    Button btnPayment,transfer;
    HashMap<String,String> OperatorHashMap1 = new HashMap<>();
    List<String> OperatorList1= new ArrayList<>();
    HashMap<String,String> OperatorHashMap2 = new HashMap<>();
    List<String> OperatorList2= new ArrayList<>();
    ArrayAdapter<String> adapterOperatorHash;
    String OPType;
    String RandomChildCode="";
    ProgressDialog progressDialog;
    NumberFormat currFormat;
    LinearLayout transfer_E_to_S;
    ArrayList<Recharge_History> rechargeList = new ArrayList<>();
    AdapterRecharge adapterRecharge;
   // List<Oparater> OperatorList= new ArrayList<>();
   // AdapterOperator adapterOperator;
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
        rbPostpaid = findViewById(R.id.rbPostpaid);
        rbPrepaid = findViewById(R.id.rbPrepaid);
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        transfer_E_to_S = findViewById(R.id.transfer_E_to_S);
        transfer = findViewById(R.id.transfer);
        txtrecentRecharge = findViewById(R.id.txtrecentRecharge);
        txtrecentRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MobilePostpaidActivity.this,RechargeHistoryActivity.class);
                startActivity(intent);
            }
        });
        btnTransfer = findViewById(R.id.btnTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });
        ListRecharges = findViewById(R.id.ListRecharges);
        adapterRecharge = new AdapterRecharge(MobilePostpaidActivity.this,0,rechargeList);
        ListRecharges.setAdapter(adapterRecharge);
        RechargeHistory();
        ChildCode();
        WalletBalance();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        RadioBtnGroup = findViewById(R.id.RadioBtnGroup);
       /* RadioBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton =  findViewById(checkedId);
                OPType = checkedRadioButton.getText().toString();
            }
        });*/
        myContact = findViewById(R.id.myContact);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtOperator = findViewById(R.id.txtOperator);
        txtAmount = findViewById(R.id.txtAmount);
        listViewPrepaid = findViewById(R.id.listViewPrepaid);
        listViewPostpaid = findViewById(R.id.listViewPostpaid);
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
        if (rbPostpaid.isChecked()) {
            adapterOperatorHash = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OperatorList1);
            adapterOperatorHash.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listViewPostpaid.setAdapter(adapterOperatorHash);
            txtOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listViewPostpaid.setVisibility(View.VISIBLE);
                    listViewPrepaid.setVisibility(View.GONE);

                }
            });
            listViewPostpaid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String opt = (String) listViewPostpaid.getItemAtPosition(position);
                    txtOperator.setText(opt);
                    OperatorCode = OperatorHashMap1.get(opt);
                    listViewPostpaid.setVisibility(View.GONE);
                }
            });
        }else {
            adapterOperatorHash = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OperatorList2);
            adapterOperatorHash.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listViewPrepaid.setAdapter(adapterOperatorHash);
            txtOperator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listViewPrepaid.setVisibility(View.VISIBLE);
                    listViewPostpaid.setVisibility(View.GONE);
                }
            });
            listViewPrepaid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String opt = (String) listViewPrepaid.getItemAtPosition(position);
                    txtOperator.setText(opt);
                    OperatorCode = OperatorHashMap2.get(opt);
                    listViewPrepaid.setVisibility(View.GONE);
                }
            });
        }
       // Operators();
        SetOperators();

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
      final Date currentTime = Calendar.getInstance().getTime();
     final int Remain = Integer.parseInt(SWallet_Balance) - Integer.parseInt(txtAmount.getText().toString());

      String Recharge_url= Constants.Application_URL+"/users/index.php/Recharge/API_recharge";
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
              Toast.makeText(MobilePostpaidActivity.this, "Please Contact to Admin ", Toast.LENGTH_SHORT).show();
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
              params.put("Optcode",OperatorCode);
              params.put("operatorname",txtOperator.getText().toString());
              params.put("wallet_bal",SWallet_Balance);
              params.put("remaining_bal",Integer.toString(Remain));
              params.put("Amount",txtAmount.getText().toString());
              params.put("date", String.valueOf(currentTime));
              return params;
          }
      };
      requestQueue.add(stringRequest);
  }
  public void WalletBalance(){
      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      String Wallet_url= Constants.Application_URL+"/users/index.php/Recharge/wallet";
      progressDialog = progressDialog.show(MobilePostpaidActivity.this, "", "Please wait...", false, false);
      StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
          @Override
          public void onResponse(String response) {
              progressDialog.dismiss();
              try {

                  JSONObject jObj = new JSONObject(response);
                  Ewalet_Balance = jObj.getString("E-Wallet");
                  SWallet_Balance = jObj.getString("S-Wallet");

                  txtEWallet.setText(" \u20B9"+Ewalet_Balance);
                  txtSWallet.setText(" \u20B9"+SWallet_Balance);

              } catch (JSONException e) {
                  e.printStackTrace();
                  progressDialog.dismiss();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              progressDialog.dismiss();
              Toast.makeText(MobilePostpaidActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
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
    public void RechargeHistory()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = Constants.Application_URL+"/users/index.php/Recharge/history";
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
                Toast.makeText(MobilePostpaidActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
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
                    //holder.OperatorIcon = convertView.findViewById(R.id.OperatorIcon);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Recharge_History history= getItem(position);
                holder.histMobile.setText(history.MobileNO);
                holder.histOperator.setText(history.Operator);
                holder.histAmount.setText(" \u20B9"+history.Amount );
                holder.histOrderId.setText(history.OrderId);
                holder.histDate.setText(history.Date);
                holder.histStatus.setText(history.Status );
                holder.hisTransId.setText(history.TransId);
               // holder.OperatorIcon.setVisibility(View.VISIBLE);
               // String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/idea.jpg";
               // Picasso.with(getApplicationContext()).load(url1).into(holder.OperatorIcon);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load Data", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

    private class ViewHolder
    {
        TextView histMobile,histOperator,histAmount,histOrderId,histDate,histStatus,hisTransId,txtOperatorName;
        CircleImageView iconOperator;
    }

    public void SetOperators()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = Constants.Application_URL+"/users/index.php/Recharge/moblie_recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Oparater opt = new Oparater();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        String OperatorName = jObj.getString("operator_name");
                        String OptID = jObj.getString("operator_code");
                        String opType = jObj.getString("OPType");
                        if (opType.equalsIgnoreCase("PostPaid")) {
                            OperatorHashMap1.put(OperatorName, OptID);
                            OperatorList1.add(OperatorName);
                        }else {
                            OperatorHashMap2.put(OperatorName, OptID);
                            OperatorList2.add(OperatorName);
                        }
                    }
                    adapterOperatorHash.notifyDataSetChanged();
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
    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_transfer, null);
        dialogBuilder.setView(dialogView);
        final EditText editAmount = dialogView.findViewById(R.id.editAmount);
        dialogBuilder.setMessage("Enter Amount");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String Transfer_url= Constants.Application_URL+"/users/index.php/Recharge/add_money_s_wallet";
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
                        params.put("amount",editAmount.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


  /*  public void Operators()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String RechargeHitory_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/moblie_recharge";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RechargeHitory_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Oparater opt = new Oparater();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        opt.OperatorName = jObj.getString("operator_name");
                        opt.optImageName = jObj.getString("image");
                        opt.OptID = jObj.getString("operator_code");
                        opt.opType = jObj.getString("OPType");
                       // OperatorHashMap.put(opt.OperatorName, opt.OptID);
                        OperatorList.add(opt);
                    }
                    adapterOperator.notifyDataSetChanged();
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
        adapterOperator = new AdapterOperator(this, android.R.layout.simple_spinner_dropdown_item,OperatorList);
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

                String opt = (String) listViewOperator.getItemAtPosition(position);
                txtOperator.setText(opt);
                listViewOperator.setVisibility(View.GONE);
            }
        });
    }

    class AdapterOperator extends ArrayAdapter{
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterOperator(Context context, int resource, List<Oparater> objects) {

            super(context, resource,objects);
            // TODO Auto-generated constructor stub
            inflat= LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return OperatorList.size();
        }

        @Nullable
        @Override
        public Oparater getItem(int position) {
            return OperatorList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_operator, null);
                    holder = new ViewHolder();
                  //  holder.iconOperator = convertView.findViewById(R.id.iconOperator);
                    holder.txtOperatorName = convertView.findViewById(R.id.txtOperatorName);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Oparater history= getItem(position);
                holder.txtOperatorName.setText(history.OperatorName);
              //  String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" + history.optImageName;
              //  Picasso.with(getApplicationContext()).load(url1).into(holder.iconOperator);
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                Toast.makeText(getApplicationContext(),"Could not Load RentData", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }    */

}
