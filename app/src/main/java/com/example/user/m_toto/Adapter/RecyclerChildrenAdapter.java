package com.example.user.m_toto.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.m_toto.NurseNavigator;
import com.example.user.m_toto.R;

import java.util.List;


/**
 * Created by user on 5/18/2017.
 */

public class RecyclerChildrenAdapter extends RecyclerView.Adapter<RecyclerChildrenAdapter.ViewHolder> {
    Context context;
    List<GetChildren> getChildren;

    public RecyclerChildrenAdapter(List<GetChildren> getChildren,Context context){
        super();
        this.getChildren=getChildren;
        this.context=context;
    }


    @Override
    public RecyclerChildrenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_children, parent, false);

        RecyclerChildrenAdapter.ViewHolder viewHolder = new RecyclerChildrenAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerChildrenAdapter.ViewHolder holder, int position) {

        GetChildren getTicketAdapter1 = getChildren.get(position);

        holder.fname.setText(getTicketAdapter1.getFname());
        holder.lname.setText(getTicketAdapter1.getLname());
        holder.dob.setText(getTicketAdapter1.getDob());

        final String clinic_child_id,clinic_child_fname,clinic_child_dob;

        clinic_child_id=getTicketAdapter1.getChildId();
        clinic_child_fname=getTicketAdapter1.getFname();
        clinic_child_dob=getTicketAdapter1.getDob();

        holder.listofChildrenForClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NurseNavigator) context).goToCategory(clinic_child_id,clinic_child_fname,clinic_child_dob);
            }
        });
    }

    //get counts
    @Override
    public int getItemCount() {

        return getChildren.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fname,lname,dob;
        public CardView listofChildrenForClinic;


        public ViewHolder(View itemView) {

            super(itemView);

            fname = (TextView) itemView.findViewById(R.id.childrenfname);
            lname = (TextView) itemView.findViewById(R.id.childrenlname);
            dob = (TextView) itemView.findViewById(R.id.childrendob);
            listofChildrenForClinic = (CardView) itemView.findViewById(R.id.listofChildrenForClinic);



        }
    }


}