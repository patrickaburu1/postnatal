package com.example.user.m_toto.ParentMainAdapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.m_toto.Models.ChildrenClinic;
import com.example.user.m_toto.ParentNavigator;
import com.example.user.m_toto.R;

import java.util.List;

/**
 * Created by user on 5/22/2017.
 */

// concerned on
public class RecyclerChildrenClinicAdapter extends RecyclerView.Adapter<RecyclerChildrenClinicAdapter.ViewHolder> {
    Context context;
    List<ChildrenClinic> getChildrenClinic;



    public RecyclerChildrenClinicAdapter(List<ChildrenClinic> getChildrenClinic,Context context){
        super();
        this.getChildrenClinic=getChildrenClinic;
        this.context=context;
    }


    @Override
    public RecyclerChildrenClinicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_children_clinic, parent, false);

        RecyclerChildrenClinicAdapter.ViewHolder viewHolder = new RecyclerChildrenClinicAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerChildrenClinicAdapter.ViewHolder holder, int position) {

        ChildrenClinic getChildrenClinic1 = getChildrenClinic.get(position);

        holder.fname.setText(getChildrenClinic1.getFname());
        holder.lname.setText(getChildrenClinic1.getLname());
        holder.dob.setText(getChildrenClinic1.getDob());

        final String child_id=getChildrenClinic1.getChild_id();
        final String child_name=getChildrenClinic1.getFname()+"  "+getChildrenClinic1.getLname();
        final String child_dob=getChildrenClinic1.getDob();

        holder.childrenList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "Clicked "+child_name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ((ParentNavigator) context).goToClinic(child_id,child_name,child_dob);
            }
        });
    }

    //get counts
    @Override
    public int getItemCount() {

        return getChildrenClinic.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fname,lname,dob;
        public  CardView childrenList;

        public ViewHolder(View itemView) {

            super(itemView);

            fname = (TextView) itemView.findViewById(R.id.childrenclinicfname);
            lname = (TextView) itemView.findViewById(R.id.childrencliniclname);
            dob = (TextView) itemView.findViewById(R.id.childrenclinicdob);

            childrenList= (CardView) itemView.findViewById(R.id.listofchildrenclinic);

        }
    }


}