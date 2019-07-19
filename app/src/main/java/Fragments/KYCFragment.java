package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sublime.sublimecash.sublime.R;

public class KYCFragment extends Fragment {

   ImageView imgPancard,imgAddProof;
   EditText txtPanNumber,txtIdNumber;
   Button btnSubmit;

    public KYCFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kyc, container, false);
        imgPancard = rootView.findViewById(R.id.imgPancard);
        imgAddProof = rootView.findViewById(R.id.imgAddProof);
        txtPanNumber = rootView.findViewById(R.id.txtPanNumber);
        txtIdNumber = rootView.findViewById(R.id.txtIdNumber);
        btnSubmit = rootView.findViewById(R.id.btnSubmit);


        return rootView;
    }
}
