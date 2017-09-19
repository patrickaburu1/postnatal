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
 * Created by user on 5/24/2017.
 */

public class RecyclerImmunizationAdapter extends RecyclerView.Adapter<RecyclerImmunizationAdapter.ViewHolder> {
    Context context;
    List<GetImmunization> getImmunization;

    public RecyclerImmunizationAdapter(List<GetImmunization> getImmunization,Context context){
        super();
        this.getImmunization=getImmunization;
        this.context=context;
    }


    @Override
    public RecyclerImmunizationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_immunization, parent, false);

        RecyclerImmunizationAdapter.ViewHolder viewHolder = new RecyclerImmunizationAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerImmunizationAdapter.ViewHolder holder, int position) {

        GetImmunization getImmunizationAdapter1 = getImmunization.get(position);

        holder.name.setText(getImmunizationAdapter1.getName());
        holder.age.setText(getImmunizationAdapter1.getAge());
        holder.id.setText(getImmunizationAdapter1.getId());

        final String immunization_id=getImmunizationAdapter1.getId();
        final String immunization_name=getImmunizationAdapter1.getName();

        holder.listOfImmunization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ((NurseNavigator) context).goToGiveImmunization(immunization_id,immunization_name);
            }
        });
    }

    //get counts
    @Override
    public int getItemCount() {

        return getImmunization.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,age,id;
        public CardView listOfImmunization;

        public ViewHolder(View itemView) {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.immunizationName);
            age = (TextView) itemView.findViewById(R.id.ageToBeGiven);
            id = (TextView) itemView.findViewById(R.id.immunizationId);
            listOfImmunization = (CardView) itemView.findViewById(R.id.listOfImmunization);



        }
    }


}