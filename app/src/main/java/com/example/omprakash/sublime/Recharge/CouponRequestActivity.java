package com.example.omprakash.sublime.Recharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omprakash.sublime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Common.ImageServer;
import Common.Url;
import Model.Coupons;

public class CouponRequestActivity extends AppCompatActivity {

    Spinner spPlans;
    EditText txtQuantity,txtHolderName,txtBankName,txtBranch,txtState,txtDistrict;
    TextView txtUpload;
    Button btnSubmit,btnImageUpdate;
    Bitmap newBitmap;
    ImageView fileImage;
    String strImage;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/Assets/uploads/abc";
    String RequestCoupon_url = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Home/request_activation";
    static final int REQUEST_IMAGE_GET = 1;
    static final int REQUEST_IMAGE_CROP = 2;
    String filePath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_request);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Coupon Request ");
        actionBar.show();

        spPlans = findViewById(R.id.spPlans);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtHolderName = findViewById(R.id.txtHolderName);
        txtBankName = findViewById(R.id.txtBankName);
        txtBranch = findViewById(R.id.txtBranch);
        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtUpload = findViewById(R.id.txtUpload);
        btnSubmit = findViewById(R.id.btnSubmit);
        fileImage = findViewById(R.id.fileImage);
        btnImageUpdate = findViewById(R.id.btnImageUpdate);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForCoupon();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.CouponRequest, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPlans.setAdapter(adapter);

    }

    public void EditImage(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

             /*   String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                filePath.setText(filePath);

                try {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        //FINE
                    } else {
                        //NOT IN REQUIRED FORMAT
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }  */
            }
    }

  /*  public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    } */









  //  @Override
  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_IMAGE_GET) {

                if (data != null) {
                    Uri uri = data.getData();
                    InputStream image_stream = getContentResolver().openInputStream(uri);
                    byte[] imgByte= ImageServer.getBytes(image_stream);
                    ImageServer.SaveFileToExternal(imgByte,"crop.jpg",getApplicationContext());
                    File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File myDir = new File(root + "/SCM/crop.jpg");
                    myDir.mkdirs();
                    Uri contentUri = Uri.fromFile(myDir);
                    ImageCropFunction(contentUri);
                }
            } else if (requestCode == REQUEST_IMAGE_CROP) {

                if (data != null) {

                    Bundle bundle = data.getExtras();
                    if(bundle!= null) {
                        newBitmap = bundle.getParcelable("data");
                        fileImage.setImageBitmap(newBitmap);
                        btnImageUpdate.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Uri cropUri =  data.getData();
                        InputStream image_stream = getContentResolver().openInputStream(cropUri);
                        newBitmap= BitmapFactory.decodeStream(image_stream);
                        fileImage.setImageBitmap(newBitmap);
                        btnImageUpdate.setVisibility(View.VISIBLE);
                    }
                    //strImage = ImageServer.getStringFromBitmap(newBitmap);
                    fileImage.invalidate();
                }
            }
        }
        catch (Exception ex)
        {
            int a=1;
        }
    }  */

    public void ImageCropFunction(Uri uri) {

        // Image Crop Code
        try {
            Intent CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 100);
            CropIntent.putExtra("outputY", 100);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            // CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
             //CropIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(CropIntent, REQUEST_IMAGE_CROP);
        }
        catch (Exception e)
        {
            int a =1;
        }
    }

    public void Image_Update(View v) {
        btnImageUpdate.setVisibility(View.INVISIBLE);

        progressDialog = progressDialog.show(CouponRequestActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CouponRequestActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CouponRequestActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", strImage);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void RequestForCoupon(){

        progressDialog = progressDialog.show(CouponRequestActivity.this, "", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestCoupon_url, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CouponRequestActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(CouponRequestActivity.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", "user@tutorialvilla.com");
                params.put("coupon_name", spPlans.getSelectedItem().toString());
                params.put("district", txtDistrict.getText().toString());
                params.put("amount", spPlans.getSelectedItem().toString());
                params.put("bank_holder_name", txtHolderName.getText().toString());
                params.put("bank_name", txtBankName.getText().toString());
                params.put("state", txtState.getText().toString());
                params.put("imagepath", url);
                params.put("quantity", txtQuantity.getText().toString());
                params.put("date", "14/06/2019");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
