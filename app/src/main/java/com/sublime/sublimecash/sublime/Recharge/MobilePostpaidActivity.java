package com.sublime.sublimecash.sublime.Recharge;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
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
    EditText txtMobileNumber,txtAmount;
    TextView myContact,txtSWallet,txtEWallet,btnTransfer,txtrecentRecharge,btnPay,txtOperator,txtBWallet;
    ListView listViewPostpaid,listViewPrepaid,ListRecharges;
    CircleImageView imageOperator;
    Button transfer;
    String RandomChildCode="";
    ProgressDialog progressDialog;
    LinearLayout transfer_E_to_S;
    ArrayList<Recharge_History> rechargeList = new ArrayList<>();
    AdapterRecharge adapterRecharge;
    final Context context = this;
    Profile myProfile;
    String SWallet_Balance,Ewalet_Balance,Pending_Balance,optImage,optName,OptId,optType;
    Button browsePlan,Offer;
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

        Intent intent = getIntent();
        optImage = intent.getStringExtra("Image");
        optName = intent.getStringExtra("optName");
        OptId = intent.getStringExtra("OptId");
        optType = intent.getStringExtra("optType");
        txtBWallet = findViewById(R.id.txtBWallet);
        myProfile = Session.GetProfile(getApplicationContext());
        imageOperator = findViewById(R.id.imageOperator);
        browsePlan = findViewById(R.id.browsePlan);
        txtEWallet = findViewById(R.id.txtEWallet);
        txtSWallet = findViewById(R.id.txtSWallet);
        transfer_E_to_S = findViewById(R.id.transfer_E_to_S);
        transfer = findViewById(R.id.transfer);
        btnPay = findViewById(R.id.btnPay);
        Offer = findViewById(R.id.Offer);
        txtOperator = findViewById(R.id.txtOperator);

        txtBWallet.setText(" \u20B9"+myProfile.PendingWallet);
        txtEWallet.setText(" \u20B9"+myProfile.EWallet);
        txtSWallet.setText(" \u20B9"+myProfile.SWallet);

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
        String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" +optImage;
        Picasso.with(getApplicationContext()).load(url1).into(imageOperator);
        txtOperator.setText(optName);
        ChildCode();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myContact = findViewById(R.id.myContact);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtOperator = findViewById(R.id.txtOperator);
        txtAmount = findViewById(R.id.txtAmount);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
      /*  myContact.setOnClickListener(new View.OnClickListener() {
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
        }  */
    }

  public String ChildCode() {
      int range = 9;
      int length = 4;
      String Child = "TRID";
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
    public void customDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);

        // set the custom dialog components - text, image and button
        TextView txtAmt = dialog.findViewById(R.id.txtAmt);
        TextView txtopt = dialog.findViewById(R.id.txtopt);
        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
        String amt = txtAmount.getText().toString();
        String mobile= txtMobileNumber.getText().toString();
        txtAmt.setText("\u20B9 "+amt);
        txtopt.setText(optName);
        txtMobile.setText(mobile);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" +optImage;
        Picasso.with(getApplicationContext()).load(url1).into(image);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.btnCancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView Button = (TextView) dialog.findViewById(R.id.btnContinue);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recharge();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
  public void Recharge() {
      String Mobile = txtMobileNumber.getText().toString();
      String Amount = txtAmount.getText().toString();
      if (Mobile.equals("")) {
          txtMobileNumber.setError("Enter Mobile no.");
      } else if (Amount.equals("")) {
          txtAmount.setError("Enter Amount.");
      } else {
          RandomChildCode = ChildCode() + "A";
          final Date currentTime = Calendar.getInstance().getTime();
          final int Remain = Integer.parseInt(SWallet_Balance) - Integer.parseInt(txtAmount.getText().toString());

          String Recharge_url = Constants.Application_URL + "/users/index.php/Recharge/API_recharge";
          progressDialog = progressDialog.show(MobilePostpaidActivity.this, "", "Please wait...", false, false);
          StringRequest stringRequest = new StringRequest(Request.Method.POST, Recharge_url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  progressDialog.dismiss();
                  try {
                      JSONObject jObj = new JSONObject(response);
                      String Status = jObj.getString("Status");
                      Toast.makeText(MobilePostpaidActivity.this, "" + Status, Toast.LENGTH_SHORT).show();
                      String RandomChildCode = jObj.getString("Yourrchid");
                      String Error = jObj.getString("Errormsg");
                      String Remaining = jObj.getString("Remain");
                      String RechargeID = jObj.getString("RechargeID");
                      Intent confirmation = new Intent(MobilePostpaidActivity.this, PaymentHistoryActivity.class);
                      confirmation.putExtra("Yourrchid", RandomChildCode);
                      confirmation.putExtra("Errormsg", Error);
                      confirmation.putExtra("Remain", Remaining);
                      confirmation.putExtra("Status", Status);
                      confirmation.putExtra("RechargeID", RechargeID);
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
          }) {
              @Override
              protected Map<String, String> getParams() {
                  Map<String, String> params = new HashMap<>();
                  params.put("email", myProfile.UserLogin);
                  params.put("Customernumber", Mobile);
                  params.put("Yourrchid", RandomChildCode);
                  params.put("Optname", txtOperator.getText().toString());
                  params.put("Optcode", OptId);
                  params.put("operatorname", txtOperator.getText().toString());
                  params.put("wallet_bal", SWallet_Balance);
                  params.put("remaining_bal", Integer.toString(Remain));
                  params.put("Amount", Amount);
                  params.put("date", String.valueOf(currentTime));
                  return params;
              }
          };
          requestQueue.add(stringRequest);
      }
  }

  //--------------------------------------------------------------------------------------------------
  //  @Override
 /*   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }   */

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
}
