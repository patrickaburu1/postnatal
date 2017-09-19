package com.example.user.m_toto.ParentMainAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.m_toto.Models.ImmunizationGivenModel;
import com.example.user.m_toto.R;

import java.util.List;

/**
 * Created by user on 5/28/2017.
 */

public class RecyclerImmunizationsGiven  extends RecyclerView.Adapter<RecyclerImmunizationsGiven.ViewHolder> {
        Context context;
        List<ImmunizationGivenModel> getImmunizationGivenModel;



public RecyclerImmunizationsGiven(List<ImmunizationGivenModel> getImmunizationGivenModel, Context context){
        super();
        this.getImmunizationGivenModel = getImmunizationGivenModel;
        this.context=context;
        }


@Override
public RecyclerImmunizationsGiven.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_immunization_given, parent, false);

        RecyclerImmunizationsGiven.ViewHolder viewHolder = new RecyclerImmunizationsGiven.ViewHolder(v);

        return viewHolder;
        }

@Override
public void onBindViewHolder(RecyclerImmunizationsGiven.ViewHolder holder, int position) {

        ImmunizationGivenModel ImmunizationGivenModel1 = getImmunizationGivenModel.get(position);

        holder.immunizationName.setText(ImmunizationGivenModel1.getImmunization_name());
        holder.dateGiven.setText(ImmunizationGivenModel1.getDate_given());
        holder.ageGiven.setText(ImmunizationGivenModel1.getAge()+"   "+"Weeks");



        }

//get counts
@Override
public int getItemCount() {

        return getImmunizationGivenModel.size();
        }


//data holders

class ViewHolder extends RecyclerView.ViewHolder {

    public TextView immunizationName,dateGiven,ageGiven;


    public ViewHolder(View itemView) {

        super(itemView);

        dateGiven = (TextView) itemView.findViewById(R.id.immunizationGivenDate);
        immunizationName = (TextView) itemView.findViewById(R.id.immunizationGivenName);
        ageGiven = (TextView) itemView.findViewById(R.id.immunizationGivenAge);


    }
}


}