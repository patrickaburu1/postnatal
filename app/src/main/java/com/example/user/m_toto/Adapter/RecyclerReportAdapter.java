package com.example.user.m_toto.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.m_toto.Models.MonthlyReport;
import com.example.user.m_toto.NurseNavigator;
import com.example.user.m_toto.R;

import java.util.List;

/**
 * Created by patto on 8/22/2017.
 */

public class RecyclerReportAdapter extends RecyclerView.Adapter<RecyclerReportAdapter.ViewHolder> {
    Context context;
    List<MonthlyReport> monthlyReport;

    public RecyclerReportAdapter(List<MonthlyReport> monthlyReport,Context context){
        super();
        this.monthlyReport=monthlyReport;
        this.context=context;
    }


    @Override
    public RecyclerReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_report, parent, false);

        RecyclerReportAdapter.ViewHolder viewHolder = new RecyclerReportAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerReportAdapter.ViewHolder holder, int position) {

        MonthlyReport getImmunizationAdapter1 = monthlyReport.get(position);

        holder.date.setText(getImmunizationAdapter1.getC());
        holder.immune.setText(getImmunizationAdapter1.getImmune());
        holder.number.setText(getImmunizationAdapter1.getNumber());

   }

    //get counts
    @Override
    public int getItemCount() {

        return monthlyReport.size();
    }


    //data holders

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date,immune,number;

        public ViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.reportDate);
            immune = (TextView) itemView.findViewById(R.id.reportImmune);
            number = (TextView) itemView.findViewById(R.id.reportNumber);

        }
    }


}