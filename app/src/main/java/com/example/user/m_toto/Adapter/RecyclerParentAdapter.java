package com.example.user.m_toto.Adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.user.m_toto.NurseNavigator;
import com.example.user.m_toto.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 5/12/2017.
 */

public class RecyclerParentAdapter  extends RecyclerView.Adapter<RecyclerParentAdapter.ViewHolder> {
    Context context;
   private List<GetParentAdapter> getParentAdapter;

    public RecyclerParentAdapter(List<GetParentAdapter> getParentAdapter,Context context){

        super();

        this.getParentAdapter=getParentAdapter;
        this.context=context;

    }




    @Override
    public RecyclerParentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_parent, parent, false);

        RecyclerParentAdapter.ViewHolder viewHolder = new RecyclerParentAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerParentAdapter.ViewHolder holder, int position) {

        GetParentAdapter getParentAdapter1 = getParentAdapter.get(position);
        //GetParentAdapter mFilteredList = getParentAdapter.get(i);

        holder.fname.setText(getParentAdapter1.getFname());

        holder.lname.setText(getParentAdapter1.getLname());
        holder.phone.setText(getParentAdapter1.getPhone());

         final String motherPhone=getParentAdapter1.getPhone();
        final String motherId=getParentAdapter1.getMother_id();
        final String motherName=getParentAdapter1.getFname();

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "u clicked "+motherPhone, Toast.LENGTH_SHORT).show();
               // motherPhoneHold=motherPhone;
                Snackbar.make(v, "Clicked "+motherName, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               ((NurseNavigator) context).goToRegisterNewBorn(motherPhone,motherId,motherName);

            }
        });

    }


    //get counts
    @Override
    public int getItemCount() {

        return getParentAdapter.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fname,lname,phone;

        private LinearLayout linearParent;
        public ViewHolder(View view) {
            super(view);

            fname = (TextView) itemView.findViewById(R.id.parentfname);
            lname = (TextView) itemView.findViewById(R.id.parentlname);
            phone = (TextView) itemView.findViewById(R.id.parentphone);

            linearParent= (LinearLayout) itemView.findViewById(R.id.linearParent);

        }
    }



    public void setFilter(List<GetParentAdapter> getParentAdapterModels) {
        getParentAdapter = new ArrayList<>();
        getParentAdapter.addAll(getParentAdapterModels);
        notifyDataSetChanged();

    }
}
