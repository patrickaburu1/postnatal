package com.example.user.m_toto.ParentMainAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.m_toto.Models.ForumModel;
import com.example.user.m_toto.R;

import java.util.List;

/**
 * Created by patto on 7/27/2017.
 */

public class RecyclerForumAdapter extends RecyclerView.Adapter<RecyclerForumAdapter.ViewHolder> {
    Context context;
    List<ForumModel> getForumModel;



    public RecyclerForumAdapter(List<ForumModel> getForumModel,Context context){
        super();
        this.getForumModel=getForumModel;
        this.context=context;
    }


    @Override
    public RecyclerForumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_forum, parent, false);

        RecyclerForumAdapter.ViewHolder viewHolder = new RecyclerForumAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerForumAdapter.ViewHolder holder, int position) {

        ForumModel getForumModel1 = getForumModel.get(position);

        holder.fname.setText(getForumModel1.getSent_by());
        holder.date.setText(getForumModel1.getDate());
        holder.message.setText(getForumModel1.getMessage());

        
    }

    //get counts
    @Override
    public int getItemCount() {

        return getForumModel.size();
    }


    //data holders
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fname,message ,date;
       

        public ViewHolder(View itemView) {

            super(itemView);

            fname = (TextView) itemView.findViewById(R.id.sent_by);
            date = (TextView) itemView.findViewById(R.id.date_sent);
            message = (TextView) itemView.findViewById(R.id.motherMessage);


        }
    }


}