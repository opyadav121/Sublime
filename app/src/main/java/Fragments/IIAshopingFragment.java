package Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;
import com.sublime.sublimecash.sublime.E_Commerce.ShophistoryActivity;
import com.sublime.sublimecash.sublime.R;
import com.sublime.sublimecash.sublime.Recharge.RechargeHistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Session;
import Model.EcomShopHistory;
import Model.Profile;
import Model.Recharge_History;


public class IIAshopingFragment extends Fragment {
    ArrayList<EcomShopHistory> ItemList = new ArrayList<>();
    AdapterHistory adapterHistory;
    ListView ShopingOrders;
    Profile myProfile;

    public IIAshopingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_iiashoping, container, false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        ShopingOrders = rootView.findViewById(R.id.ShopingOrders);
        myProfile = Session.GetProfile(getActivity().getApplicationContext());

        adapterHistory =new AdapterHistory(IIAshopingFragment.this,0,ItemList);
        ShopingOrders.setAdapter(adapterHistory);
        ShopingOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EcomShopHistory itemName= adapterHistory.getItem(position);
                String Name = itemName.name;
                Intent intent = new Intent(getActivity(), ShophistoryActivity.class);
                startActivity(intent);

             /*   Fragment orderFragment = new OrderDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentshop, orderFragment);
                transaction.addToBackStack(null);
                transaction.commit(); */
            }
        });
        String url= "http://www.sublimecash.com/ws2/index.php/front_controller/get_order_histroy";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jOb = new JSONObject(response);
                    String Address = jOb.getString("add");
                    String Item = jOb.getString("item");
                    JSONArray jsonArray = new JSONArray(Address);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        EcomShopHistory shop = new EcomShopHistory();
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        shop.name = jObj.getString("name");
                        shop.email = jObj.getString("email");
                        shop.address = jObj.getString("address");
                        shop.created = jObj.getString("created");
                        shop.amount = jObj.getString("amount");
                        shop.phone = jObj.getString("phone");
                       // ItemList.add(shop);
                    }
                    JSONArray jsArray = new JSONArray(Item);
                    for (int j = 0; j < jsArray.length(); j++) {
                        EcomShopHistory shop1 = new EcomShopHistory();
                        JSONObject jObj1 = jsArray.getJSONObject(j);
                        shop1.order_id = jObj1.getString("order_id");
                        shop1.product_id = jObj1.getString("product_id");
                        shop1.quantity = jObj1.getString("quantity");
                        shop1.prod_name = jObj1.getString("prod_name");
                        shop1.sku_number = jObj1.getString("sku_number");
                        shop1.image = jObj1.getString("image");
                        shop1.Vendor_name = jObj1.getString("Vendor_name");
                        shop1.status = jObj1.getString("status");
                        shop1.sub_total = jObj1.getString("sub_total");
                        shop1.user_email = jObj1.getString("user_email");
                        ItemList.add(shop1);
                    }
                    adapterHistory.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        }) {
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
   public class AdapterHistory extends ArrayAdapter<EcomShopHistory> {
        LayoutInflater inflat;
        ViewHolder holder;
        public AdapterHistory(IIAshopingFragment context, int resource, List<EcomShopHistory> objects) {
            super(context.getActivity(), resource,objects);
            // TODO Auto-generated constructor stub
            inflat= (LayoutInflater) LayoutInflater.from(context.getActivity());
        }
        @Override
        public int getCount() {
            return ItemList.size();
        }

        @Nullable
        @Override
        public EcomShopHistory getItem(int position) {
            return ItemList.get(position);
        }

        @Override
        public int getPosition(EcomShopHistory item) {
            return super.getPosition(item);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_shoping, null);
                    holder = new ViewHolder();
                    holder.txtName = convertView.findViewById(R.id.txtName);
                    holder.txtDate = convertView.findViewById(R.id.txtDate);
                    holder.imgItem = convertView.findViewById(R.id.imgItem);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                EcomShopHistory history= getItem(position);
                holder.txtName.setText(history.prod_name);
                holder.txtDate.setText(history.created);
                String url1 = "http://sublimecash.com/upload/product/" +history.image;
                Picasso.with(getActivity().getApplicationContext()).load(url1).into(holder.imgItem);
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
        TextView txtName,txtDate;
        ImageView imgItem;
    }
}