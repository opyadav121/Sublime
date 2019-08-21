package Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.Recharge.RechargeHistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Model.EcomShopHistory;
import Model.Profile;
import Model.Recharge_History;


public class RechargeBillsFragment extends Fragment {
    ArrayList<Recharge_History> rechargeList = new ArrayList<>();
    AdapterRecharge adapterRecharge;
    ListView ListRecharges;
    RequestQueue requestQueue;
    Profile myProfile;
    public RechargeBillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recharge_bills, container, false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        ListRecharges = rootView.findViewById(R.id.ListRecharges);
        myProfile = Session.GetProfile(getActivity().getApplicationContext());
        adapterRecharge =new AdapterRecharge(RechargeBillsFragment.this,0,rechargeList);
        ListRecharges.setAdapter(adapterRecharge);
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
               // Toast.makeText(RechargeHistoryActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
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
        return rootView;
      }
    public class AdapterRecharge extends ArrayAdapter<Recharge_History> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterRecharge(RechargeBillsFragment context, int resource, List<Recharge_History> objects) {

            super(context.getActivity(), resource,objects);
            // TODO Auto-generated constructor stub
            inflat= (LayoutInflater) LayoutInflater.from(context.getActivity());
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
                return convertView;
            }
            catch (Exception ex)
            {
                int a=1;
                // Toast.makeText(getApplicationContext(),"Could not Load RentData", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }
    private class ViewHolder
    {
        TextView histMobile,histOperator,histAmount,histOrderId,histDate,histStatus,hisTransId;
    }
}
