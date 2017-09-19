package com.example.user.m_toto.ParentFragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.m_toto.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedFeeding extends Fragment {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    CardView above6months,above12months,above2years;
    TextView feedingContent;
    ImageView feedingImage;

    public RecommendedFeeding() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recommended_feeding, container, false);

        mContext =getActivity();
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl);

      //  feedingContent= (TextView) view.findViewById(R.id.feedingContent);

        CardView ageupto6months= (CardView) view.findViewById(R.id.ageupto6months);
        above6months= (CardView) view.findViewById(R.id.ageupto12months);
        above12months= (CardView) view.findViewById(R.id.age12to2years);
        above2years= (CardView) view.findViewById(R.id.age2years);


        ageupto6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBirth();
                feedingContent.setText(getString(R.string.atbirth));
            }});
    // between 6 months to 12 months
        above6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBirth();
                feedingContent.setText(getString(R.string.above6months));
                feedingImage.setImageResource(R.drawable.feed12months);
            }
        });

        //between 1 yr and 2 years
        above12months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBirth();
                feedingContent.setText(getString(R.string.above1year));
                feedingImage.setImageResource(R.drawable.feedingherself);
            }
        });

        //above 2 years
        above2years.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBirth();
                feedingContent.setText(getString(R.string.above2years));
                feedingImage.setImageResource(R.drawable.years2);
            }
        });
        return  view;
    }

    ///at birth feeding covers everything
    public void atBirth(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        mRelativeLayout.setVisibility(View.GONE);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.custom_layout,null);

        feedingContent= (TextView) customView.findViewById(R.id.feedingContent);
        feedingImage= (ImageView) customView.findViewById(R.id.feedingImage);

        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.isFocusable();
        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
                mRelativeLayout.setVisibility(view.VISIBLE);
            }
        });
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);

    }
}
