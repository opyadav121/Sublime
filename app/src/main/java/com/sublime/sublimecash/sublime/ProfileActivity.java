package com.sublime.sublimecash.sublime;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Common.Constants;
import Common.Session;
import Common.VolleyMultipartRequest;
import Model.Profile;
import de.hdodenhof.circleimageview.CircleImageView;
public class ProfileActivity extends AppCompatActivity {
    CircleImageView imageProfile;
    Button editProfile;
    Profile myProfile;
    TextView txtEmailID,txtFirstName,txtMobileNumber,txtDob,txtGender,txtMaritalStatus,txtFatherName,txtMotherName,
            txtNominee,txtNomineeRelation,txtUserId,txtAddress,txtSponser,txtActivation,txtPan,txtOccupation,
            txtDistrict,txtState,txtPincode,txtPassport,txtCommission,txtLeftUser,txtRightUser,txtleftBusiness,txtRightBusiness;
    TextView txtAccountNumber,txtBankName,txtIfsc,txtBranch;
    TextView txtInsurance,txtHolderName,txtPremium,txtCompanyName,txtInsuranceType,txtPolicyNumber,txtVehicleNumber,txtPurchagedYear,txtExpiryDate,txtCompName;
    String url = Constants.Application_URL+"/users/index.php/kyc/upload_pic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageProfile = findViewById(R.id.imageProfile);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtMobileNumber = findViewById(R.id.txtMobileNumber);
        txtEmailID = findViewById(R.id.txtEmailID);
        txtDob = findViewById(R.id.txtDob);
        txtGender = findViewById(R.id.txtGender);
        txtMaritalStatus = findViewById(R.id.txtMaritalStatus);
        txtFatherName = findViewById(R.id.txtFatherName);
        txtMotherName = findViewById(R.id.txtMotherName);
        txtNominee = findViewById(R.id.txtNominee);
        txtNomineeRelation = findViewById(R.id.txtNomineeRelation);
        txtUserId = findViewById(R.id.txtUserId);
        txtAddress = findViewById(R.id.txtAddress);
        txtSponser = findViewById(R.id.txtSponser);
        txtActivation = findViewById(R.id.txtActivation);
        txtPan = findViewById(R.id.txtPan);
        txtOccupation = findViewById(R.id.txtOccupation);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtState = findViewById(R.id.txtState);
        txtPincode = findViewById(R.id.txtPincode);
        txtPassport = findViewById(R.id.txtPassport);
        txtCommission = findViewById(R.id.txtCommission);
        txtLeftUser = findViewById(R.id.txtLeftUser);
        txtRightUser = findViewById(R.id.txtRightUser);
        txtleftBusiness = findViewById(R.id.txtleftBusiness);
        txtRightBusiness = findViewById(R.id.txtRightBusiness);
        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });
        myProfile = Session.GetProfile(this);
        txtEmailID.setText(myProfile.UserLogin);
        txtMobileNumber.setText(myProfile.MobileNumber);
        txtFirstName.setText(myProfile.UserName);
        txtDob.setText(myProfile.Dob);
        txtGender.setText(myProfile.gender);
        txtMaritalStatus.setText(myProfile.marital);
        txtFatherName.setText(myProfile.FatherName);
        txtMotherName.setText(myProfile.MotherName);
        txtNominee.setText(myProfile.nominee);
        txtNomineeRelation.setText(myProfile.nomineeRelation);
        txtUserId.setText(myProfile.UserID);
        txtAddress.setText(myProfile.Address);
        txtSponser.setText(myProfile.Sponsor);
        txtActivation.setText(myProfile.ActivationDate);
        txtPan.setText(myProfile.PanNumber);
        txtOccupation.setText(myProfile.occupation);
        txtDistrict.setText(myProfile.district);
        txtState.setText(myProfile.State);
        txtPincode.setText(myProfile.PinCode);
        txtPassport.setText(myProfile.Passport);
        txtCommission.setText(" \u20B9"+myProfile.commission_paid);
        txtLeftUser.setText(myProfile.total_left_user);
        txtRightUser.setText(myProfile.total_right_user);
        txtleftBusiness.setText(" \u20B9"+myProfile.left_business);
        txtRightBusiness.setText(" \u20B9"+myProfile.right_business);

         // String url1 = "http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/users/opt/" + history.optImageName;
        //  Picasso.with(getApplicationContext()).load(url1).into(imageProfile);

     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        //adding click listener to button
        findViewById(R.id.btnImageUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });  */
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageProfile.setImageBitmap(bitmap);
                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,url , new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email",myProfile.UserLogin);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
