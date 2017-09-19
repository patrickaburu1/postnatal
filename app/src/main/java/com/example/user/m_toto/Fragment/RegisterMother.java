package com.example.user.m_toto.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterMother.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterMother#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterMother extends Fragment {
    EditText fname,lname,phone,county,district,division,location,town,village;
    Spinner counties;

    String fname1,lname1,phone1,county1,district1,division1,location1,town1,village1;
    ProgressDialog pDialog;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterMother() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterMother.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterMother newInstance(String param1, String param2) {
        RegisterMother fragment = new RegisterMother();
        Bundle args = new Bundle();;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register_mother, container, false);

        fname= (EditText) view.findViewById(R.id.motherFname);
        lname= (EditText) view.findViewById(R.id.motherLname);
        phone= (EditText) view.findViewById(R.id.motherPhone);
       // county= (EditText) view.findViewById(R.id.motherCounty);
        district= (EditText) view.findViewById(R.id.motherDistrict);
        division= (EditText) view.findViewById(R.id.motherDivision);
        location= (EditText) view.findViewById(R.id.motherLocation);
        town= (EditText) view.findViewById(R.id.motherTown);
        village= (EditText) view.findViewById(R.id.motherVillage);

        counties= (Spinner) view.findViewById(R.id.motherCounty);

        Button register= (Button) view.findViewById(R.id.motherRegisterButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewParent();
            }
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void registerNewParent(){
       fname1=fname.getText().toString();
        lname1=lname.getText().toString();
        phone1=phone.getText().toString();
      //  county1=county.getText().toString();
        county1=counties.getSelectedItem().toString();
        district1=district.getText().toString();
        division1=division.getText().toString();
        location1=location.getText().toString();
        town1=town.getText().toString();
        village1=village.getText().toString();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Registering Mother......................Hold On!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        // date=other_date.getText().toString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("fname", fname1);
        params.put("lname", lname1);
        params.put("phone", phone1);
        params.put("county", county1);
        params.put("district", district1);
        params.put("division", division1);
        params.put("location", location1);
        params.put("town", town1);
        params.put("village", village1);
//        params.put("password", password);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.REGISTER_MOTHER, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                               // Log.d("Amount Successful!", response.toString());
                               String id = response.getString("id");
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("SUCCESSFULLY")
                                        .setContentText("REGISTERED")
                                        .show();
                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.nurseNavigator, new ParentList(), getString(R.string.app_name));
                                f.commit();
                            } else {
                                pDialog.dismiss();

                               // Toast.makeText(getActivity(), "error "+response, Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("OPPS...!!")
                                        .setContentText("PLEASE TRY AGAIN"+response)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                pDialog.dismiss();
                Log.d("Response: ", response.toString());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OoPS...!!")
                        .setContentText("PLEASE TRY AGAIN"+response)
                        .show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
}
