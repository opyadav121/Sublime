package com.sublime.sublimecash.sublime;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.History.BankDetailsActivity;
import com.sublime.sublimecash.sublime.History.DailyIncomeActivity;
import com.sublime.sublimecash.sublime.History.DirectBonazaActivity;
import com.sublime.sublimecash.sublime.History.ROIIncomeActivity;
import com.sublime.sublimecash.sublime.History.RewardsActivity;
import com.sublime.sublimecash.sublime.Recharge.BinaryIncomeActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponRequestActivity;
import com.sublime.sublimecash.sublime.Recharge.CouponRequestbySWalletActivity;
import com.sublime.sublimecash.sublime.Recharge.EWalletHistoryActivity;
import com.sublime.sublimecash.sublime.Recharge.PrepaidRechargeActivity;
import com.sublime.sublimecash.sublime.Recharge.SWalletHistoryActivity;
import com.sublime.sublimecash.sublime.Recharge.UserListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.History;
import Model.Profile;

public class HistoryActivity extends AppCompatActivity {
    GridView gridViewHistory;
    ArrayList accountHistory = new ArrayList<>();
    AdapterAccounts adapterAccount;
    ProgressDialog progressDialog;
    String Status;
    LinearLayout Option;
    RequestQueue requestQueue;
    Profile myProfile;
    TextView payOffline,payWithSWallet,payOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" History ");
        actionBar.show();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myProfile = Session.GetProfile(getApplicationContext());
        Option = findViewById(R.id.Option);
        payOnline = findViewById(R.id.payOnline);
        payOffline = findViewById(R.id.payOffline);
        payWithSWallet = findViewById(R.id.payWithSWallet);
        payWithSWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this,CouponRequestbySWalletActivity.class);
                startActivity(intent);
                Option.setVisibility(View.GONE);
            }
        });
        payOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this,CouponRequestActivity.class);
                startActivity(intent);
                Option.setVisibility(View.GONE);
            }
        });
        payOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, CouponRequestbySWalletActivity.class);
                startActivity(intent);
                Option.setVisibility(View.GONE);
            }
        });
        StatusActive();
        gridViewHistory = findViewById(R.id.gridViewHistory);
        accountHistory.add(new History("Become A Partner", R.drawable.share));
        accountHistory.add(new History("My Partner Status", R.drawable.icon1));
        accountHistory.add(new History("Daily Income", R.drawable.icon2));
        accountHistory.add(new History("Rewards", R.drawable.icon3));
        accountHistory.add(new History("ROI", R.drawable.icon4));
        accountHistory.add(new History("Direct Bonaza", R.drawable.icon5));
        accountHistory.add(new History("Binary Income", R.drawable.icon6));
        accountHistory.add(new History("E-Cash", R.drawable.icon7));
        accountHistory.add(new History("S-Cash", R.drawable.icon8s));
        accountHistory.add(new History("Coupons", R.drawable.icon9));
        accountHistory.add(new History("Tree View", R.drawable.icon10));
        accountHistory.add(new History("E to S-cash", R.drawable.icon11));
        accountHistory.add(new History("Bank Details", R.drawable.icon8));
        adapterAccount=new AdapterAccounts(this, R.layout.gridview_history, accountHistory) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        gridViewHistory.setAdapter(adapterAccount);
        gridViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  History operator = (History) accountHistory.get(position);
                  String name = operator.getoptName();
                  if (name.equalsIgnoreCase("My Partner Status")){
                      Intent intent = new Intent(HistoryActivity.this, UserListActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("Daily Income")){
                      if (!Status.equalsIgnoreCase("Active")){

                          AlertDialog.Builder builder= new AlertDialog.Builder(HistoryActivity.this);
                          builder.setMessage("You Need to Become a Partner First!");
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                          AlertDialog Alert = builder.create();
                          Alert.show();
                      }else {
                          Intent intent = new Intent(HistoryActivity.this, DailyIncomeActivity.class);
                          startActivity(intent);
                      }
                  }else if (name.equalsIgnoreCase("Rewards")){

                      if (!Status.equalsIgnoreCase("Active")){

                          AlertDialog.Builder builder= new AlertDialog.Builder(HistoryActivity.this);
                          builder.setMessage("You Need to Become a Partner First!");
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                          AlertDialog Alert = builder.create();
                          Alert.show();
                      }else {
                          Intent intent = new Intent(HistoryActivity.this, RewardsActivity.class);
                          startActivity(intent);
                      }
                  }else if (name.equalsIgnoreCase("ROI")){
                      if (!Status.equalsIgnoreCase("Active")){

                          AlertDialog.Builder builder= new AlertDialog.Builder(HistoryActivity.this);
                          builder.setMessage("You Need to Become a Partner First!");
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                          AlertDialog Alert = builder.create();
                          Alert.show();
                      }else {
                          Intent intent = new Intent(HistoryActivity.this, ROIIncomeActivity.class);
                          startActivity(intent);
                      }
                  }else if (name.equalsIgnoreCase("Direct Bonaza")){
                      if (!Status.equalsIgnoreCase("Active")){
                          AlertDialog.Builder builder= new AlertDialog.Builder(HistoryActivity.this);
                          builder.setMessage("You Need to Become a Partner First!");
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                          AlertDialog Alert = builder.create();
                          Alert.show();
                      }else {
                          Intent intent = new Intent(HistoryActivity.this, DirectBonazaActivity.class);
                          startActivity(intent);
                      }
                  }else if (name.equalsIgnoreCase("Binary Income")) {
                      if (!Status.equalsIgnoreCase("Active")){
                          AlertDialog.Builder builder= new AlertDialog.Builder(HistoryActivity.this);
                          builder.setMessage("You Need to Become a Partner First!");
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          });
                          AlertDialog Alert = builder.create();
                          Alert.show();
                      }else {
                          Intent intent = new Intent(HistoryActivity.this, BinaryIncomeActivity.class);
                          startActivity(intent);
                      }
                  }else if (name.equalsIgnoreCase("E-Cash")){
                      Intent intent = new Intent(HistoryActivity.this, EWalletHistoryActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("S-Cash")){
                      Intent intent = new Intent(HistoryActivity.this, SWalletHistoryActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("Coupons")){
                      Intent intent = new Intent(HistoryActivity.this, CouponActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("Tree View")){
                      Intent intent = new Intent(HistoryActivity.this, TreeViewActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("E to S-cash")){
                      showChangeLangDialog();
                  }else if (name.equalsIgnoreCase("Bank Details")){
                      Intent intent = new Intent(HistoryActivity.this, BankDetailsActivity.class);
                      startActivity(intent);
                  }else if (name.equalsIgnoreCase("Become A Partner")){
                      Option.setVisibility(View.VISIBLE);
                  }
            }
        });
    }
    public class AdapterAccounts extends ArrayAdapter {
        ArrayList accountHistory = new ArrayList<>();

        public AdapterAccounts(Context context, int textViewResourceId, ArrayList objects) {
            super(context, textViewResourceId, objects);
            accountHistory = objects;
        }
        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_history, null);
            TextView textView = convertView.findViewById(R.id.testName);
            ImageView imageView = convertView.findViewById(R.id.image);
            History tempOpt = (History) accountHistory.get(position);
            textView.setText(tempOpt.getoptName());
            imageView.setImageResource(tempOpt.getoptImage());
            return convertView;
        }
    }
    public void StatusActive(){
        String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Recharge/status";
        progressDialog = progressDialog.show(HistoryActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Status = jObj.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(HistoryActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(stringRequest);
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
                progressDialog = progressDialog.show(HistoryActivity.this, "", "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Transfer_url, new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String Status = jObj.getString("status");
                            String MSG = jObj.getString("msg");
                            Toast.makeText(HistoryActivity.this, ""+Status, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HistoryActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
