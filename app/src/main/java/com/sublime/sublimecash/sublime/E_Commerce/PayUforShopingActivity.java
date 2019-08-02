package com.sublime.sublimecash.sublime.E_Commerce;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sublime.sublimecash.sublime.MainActivity;
import com.sublime.sublimecash.sublime.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Common.Constants;

public class PayUforShopingActivity extends Activity {

    private ArrayList<String> post_val = new ArrayList<String>();
    private String post_Data="";
    WebView webView ;
    final Activity activity = this;
    private String tag = "PayMentGateWay";
    private String hash,hashSequence;
    ProgressDialog progressDialog ;

    String merchant_key="V70k7TwL"; // live
    String salt="G2tSvurAlX"; // live

    // String merchant_key="kYz2vV"; // test
    //  String salt="zhoXe53j"; // test
    String action1 ="";
    //String base_url="https://test.payu.in";
    //https://secure.payu.in
    String base_url="https://secure.payu.in";//
    int error=0;
    String hashString="";
    Map<String,String> params;
    String txnid ="";

    String SUCCESS_URL = "https://www.payumoney.com/mobileapp/payumoney/success.php" ; // failed
    String FAILED_URL = "https://www.payumoney.com/mobileapp/payumoney/failure.php" ;

    Handler mHandler = new Handler();


    static String getFirstName, getNumber, getEmailAddress, getRechargeAmt, getEmail;


    ProgressDialog pDialog ;

    @SuppressLint("JavascriptInterface") @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(activity);


        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        webView = new WebView(this);
        setContentView(webView);

        Intent oIntent  = getIntent();

        getFirstName    = oIntent.getExtras().getString("FIRST_NAME");
        getNumber       = oIntent.getExtras().getString("PHONE_NUMBER");
        getEmailAddress = oIntent.getExtras().getString("EMAIL_ADDRESS");
        getRechargeAmt  = oIntent.getExtras().getString("RECHARGE_AMT");
        getEmail = oIntent.getExtras().getString("EMAIL");

        params= new HashMap<String,String>();
        params.put("key", merchant_key);

        params.put("amount", getRechargeAmt);
        params.put("firstname", getFirstName);
        params.put("email", getEmailAddress);
        params.put("phone", getNumber);
        params.put("productinfo", "Recharge Wallet");
        params.put("surl", SUCCESS_URL);
        params.put("furl", FAILED_URL);
        params.put("service_provider", "payu_paisa");
        params.put("lastname", "");
        params.put("address1", "");
        params.put("address2", "");
        params.put("city", "");
        params.put("state", "");
        params.put("country", "");
        params.put("zipcode", "");
        params.put("udf1", "");
        params.put("udf2", "");
        params.put("udf3", "");
        params.put("udf4", "");
        params.put("udf5", "");
        params.put("pg", "");

        if(empty(params.get("txnid"))){
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
            txnid=hashCal("SHA-256",rndm).substring(0,20);
            params.put("txnid", txnid);
        }
        else
            txnid=params.get("txnid");
        //String udf2 = txnid;
        String txn="abcd";
        hash="";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        if(empty(params.get("hash")) && params.size()>0)
        {
            if( empty(params.get("key"))
                    || empty(params.get("txnid"))
                    || empty(params.get("amount"))
                    || empty(params.get("firstname"))
                    || empty(params.get("email"))
                    || empty(params.get("phone"))
                    || empty(params.get("productinfo"))
                    || empty(params.get("surl"))
                    || empty(params.get("furl"))
                    || empty(params.get("service_provider"))

            ){
                error=1;
            }
            else{
                String[] hashVarSeq=hashSequence.split("\\|");

                for(String part : hashVarSeq)
                {
                    hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
                    hashString=hashString.concat("|");
                }
                hashString=hashString.concat(salt);


                hash=hashCal("SHA-512",hashString);
                action1=base_url.concat("/_payment");
            }
        }
        else if(!empty(params.get("hash")))
        {
            hash=params.get("hash");
            action1=base_url.concat("/_payment");
        }

        webView.setWebViewClient(new MyWebViewClient(){

            public void onPageFinished(WebView view, final String url) {
                progressDialog.dismiss();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //make sure dialog is showing
                if(! progressDialog.isShowing()){
                    progressDialog.show();
                }
            }
        });


        webView.setVisibility(0);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setLoadWithOverviewMode(false);

        //webView.addJavascriptInterface(new PayUJavaScriptInterface(getApplicationContext()), "PayUMoney");
        webView.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("key",merchant_key);
        mapParams.put("hash", PayUforShopingActivity.this.hash);
        mapParams.put("txnid",(empty(PayUforShopingActivity.this.params.get("txnid"))) ? "" : PayUforShopingActivity.this.params.get("txnid"));
        Log.d(tag, "txnid: "+ PayUforShopingActivity.this.params.get("txnid"));
        mapParams.put("service_provider","payu_paisa");

        mapParams.put("amount",(empty(PayUforShopingActivity.this.params.get("amount"))) ? "" : PayUforShopingActivity.this.params.get("amount"));
        mapParams.put("firstname",(empty(PayUforShopingActivity.this.params.get("firstname"))) ? "" : PayUforShopingActivity.this.params.get("firstname"));
        mapParams.put("email",(empty(PayUforShopingActivity.this.params.get("email"))) ? "" : PayUforShopingActivity.this.params.get("email"));
        mapParams.put("phone",(empty(PayUforShopingActivity.this.params.get("phone"))) ? "" : PayUforShopingActivity.this.params.get("phone"));

        mapParams.put("productinfo",(empty(PayUforShopingActivity.this.params.get("productinfo"))) ? "" : PayUforShopingActivity.this.params.get("productinfo"));
        mapParams.put("surl",(empty(PayUforShopingActivity.this.params.get("surl"))) ? "" : PayUforShopingActivity.this.params.get("surl"));
        mapParams.put("furl",(empty(PayUforShopingActivity.this.params.get("furl"))) ? "" : PayUforShopingActivity.this.params.get("furl"));
        mapParams.put("lastname",(empty(PayUforShopingActivity.this.params.get("lastname"))) ? "" : PayUforShopingActivity.this.params.get("lastname"));

        mapParams.put("address1",(empty(PayUforShopingActivity.this.params.get("address1"))) ? "" : PayUforShopingActivity.this.params.get("address1"));
        mapParams.put("address2",(empty(PayUforShopingActivity.this.params.get("address2"))) ? "" : PayUforShopingActivity.this.params.get("address2"));
        mapParams.put("city",(empty(PayUforShopingActivity.this.params.get("city"))) ? "" : PayUforShopingActivity.this.params.get("city"));
        mapParams.put("state",(empty(PayUforShopingActivity.this.params.get("state"))) ? "" : PayUforShopingActivity.this.params.get("state"));

        mapParams.put("country",(empty(PayUforShopingActivity.this.params.get("country"))) ? "" : PayUforShopingActivity.this.params.get("country"));
        mapParams.put("zipcode",(empty(PayUforShopingActivity.this.params.get("zipcode"))) ? "" : PayUforShopingActivity.this.params.get("zipcode"));
        mapParams.put("udf1",(empty(PayUforShopingActivity.this.params.get("udf1"))) ? "" : PayUforShopingActivity.this.params.get("udf1"));
        mapParams.put("udf2",(empty(PayUforShopingActivity.this.params.get("udf2"))) ? "" : PayUforShopingActivity.this.params.get("udf2"));

        mapParams.put("udf3",(empty(PayUforShopingActivity.this.params.get("udf3"))) ? "" : PayUforShopingActivity.this.params.get("udf3"));
        mapParams.put("udf4",(empty(PayUforShopingActivity.this.params.get("udf4"))) ? "" : PayUforShopingActivity.this.params.get("udf4"));
        mapParams.put("udf5",(empty(PayUforShopingActivity.this.params.get("udf5"))) ? "" : PayUforShopingActivity.this.params.get("udf5"));
        mapParams.put("pg",(empty(PayUforShopingActivity.this.params.get("pg"))) ? "" : PayUforShopingActivity.this.params.get("pg"));
        webview_ClientPost(webView, action1, mapParams.entrySet());

    }

  /*public class PayUJavaScriptInterface {

   @JavascriptInterface
        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;

                    new PostRechargeData().execute();

              Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from PayUJavaScriptInterface" ,Toast.LENGTH_LONG).show();

                }
            });
        }
 }*/


    private final class PayUJavaScriptInterface {

        PayUJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        @JavascriptInterface
        public void success(long id, final String paymentId) {
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler = null;

                      /*Intent intent = new Intent();
                     intent.putExtra(Constants.RESULT, "success");
                     intent.putExtra(Constants.PAYMENT_ID, paymentId);
                     setResult(RESULT_OK, intent);
                     finish();*/
                    // new PostRechargeData().execute();
                   // sendUpdates();
                    Intent intent=new Intent(PayUforShopingActivity.this, MainActivity.class);
                    intent.putExtra("test",getFirstName);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Successfully payment", Toast.LENGTH_LONG).show();

                }
            });
        }

        @JavascriptInterface
        public void failure(final String id, String error) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //cancelPayment();
                    Toast.makeText(getApplicationContext(),"Cancel payment" ,Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void failure() {
            failure("");
        }

        @JavascriptInterface
        public void failure(final String params) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                      /*Intent intent = new Intent();
                     intent.putExtra(Constants.RESULT, params);
                     setResult(RESULT_CANCELED, intent);
                     finish();*/
                    Toast.makeText(getApplicationContext(),"Failed payment" ,Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    public void webview_ClientPost(WebView webView, String url, Collection< Map.Entry<String, String>> postData){
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));
        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");
        Log.d(tag, "webview_ClientPost called");

        //setup and load the progress bar
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading. Please wait...");
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }


    public void success(long id, final String paymentId) {

        mHandler.post(new Runnable() {
            public void run() {
                mHandler = null;

                //  new PostRechargeData().execute();

                Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from Success Function" ,Toast.LENGTH_LONG).show();

            }
        });
    }


    public boolean empty(String s)
    {
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();



            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException nsae){ }

        return hexString.toString();


    }

    //String SUCCESS_URL = "https://pay.in/sccussful" ; // failed
    //String FAILED_URL = "https://pay.in/failed" ;
    //override the override loading method for the webview client
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

         /*if(url.contains("response.php") || url.equalsIgnoreCase(SUCCESS_URL)){

          new PostRechargeData().execute();

          Toast.makeText(getApplicationContext(),"Successfully payment\n redirect from webview" ,Toast.LENGTH_LONG).show();

                return false;
         }else  */if(url.startsWith("http")){
                //Toast.makeText(getApplicationContext(),url ,Toast.LENGTH_LONG).show();
                progressDialog.show();
                view.loadUrl(url);
                System.out.println("myresult "+url);
                //return true;
            } else {
                return false;
            }

            return true;
        }
    }

    public void sendUpdates(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Wallet_url= Constants.Application_URL+"/users/index.php/Recharge/add_money_s_wallet_payu";
        progressDialog = progressDialog.show(PayUforShopingActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Wallet_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {

                    JSONObject jObj = new JSONObject(response);


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PayUforShopingActivity.this, "Please Contact Admin", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", getEmail);
                params.put("amount",getRechargeAmt);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

