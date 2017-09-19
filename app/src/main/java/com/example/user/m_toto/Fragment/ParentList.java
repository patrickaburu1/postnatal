package com.example.user.m_toto.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Adapter.GetParentAdapter;
import com.example.user.m_toto.Adapter.RecyclerParentAdapter;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ParentList extends Fragment  implements SearchView.OnQueryTextListener {


    private List<GetParentAdapter> GetParentAdapter1;
    private RecyclerParentAdapter adapter ;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    private RecyclerParentAdapter recyclerViewadapter;
    SearchView parentsearch;
    EditText textSearch;
   // ProgressBar progressBar;

    //to be removed
    private RecyclerParentAdapter mAdapter;
    private ArrayList<GetParentAdapter> mArrayList;

    //testing
       String JSON_FNAME = "firstName";
    String JSON_LNAME = "lastName";
    String JSON_PHONE = "phone_No";
    String JSON_MOTHER_ID = "id";

    RequestQueue requestQueue ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view=inflater.inflate(R.layout.fragment_parent_list, container, false);
        return inflater.inflate(R.layout.fragment_parent_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetParentAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewParent);

      //  progressBar = (ProgressBar) view.findViewById(R.id.progressBarParent);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

       // parentsearch= (SearchView) view.findViewById(R.id.ParentTopSearch);



        TextView registerNewParent= (TextView) view.findViewById(R.id.registerNewParent);

        //register mother layout
        registerNewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.nurseNavigator, new RegisterMother(), getString(R.string.app_name));
                f.commit();
            }
        });



        parents();

       // return  view;
    }


    public void parents(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading Parents", "Please wait...");
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.MOTHERS,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                       // progressBar.setVisibility(View.GONE);
                        progress.dismiss();
                        json_parse_data(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressBar.setVisibility(View.GONE);
                        progress.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...!")
                                .setContentText("PLEASE CHECK YOUR NETWORK CONNECTION")
                                .show();
                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array){

        if (array.length()==0){

        }
        else{

        }
        for(int i = 0; i<array.length(); i++) {

              GetParentAdapter GetDataAdapter2 = new GetParentAdapter();
         //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                GetDataAdapter2.setFname(json.getString(JSON_FNAME));

                GetDataAdapter2.setLname(json.getString(JSON_LNAME));
                GetDataAdapter2.setPhone(json.getString(JSON_PHONE));
                GetDataAdapter2.setMother_id(json.getString(JSON_MOTHER_ID));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetParentAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerParentAdapter(GetParentAdapter1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        recyclerViewadapter.setFilter(GetParentAdapter1);

                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }



    @Override
    public boolean onQueryTextChange(String newText) {

        final List<GetParentAdapter> filteredModelList = filter(GetParentAdapter1, newText);

      //  recyclerViewadapter.setFilter(filteredModelList);
        recyclerViewadapter.setFilter(filteredModelList);
        recyclerView.scrollToPosition(0);
       // ((RecyclerParentAdapter) recyclerView.getAdapter()).setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       // new ParentList().execute(query.trim());
        return false;
    }

    private List<GetParentAdapter> filter(List<GetParentAdapter> models, String query) {
        query = query.toLowerCase();final List<GetParentAdapter> filteredModelList = new ArrayList<>();
        for (GetParentAdapter model : models) {
            final String text = model.getFname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}

