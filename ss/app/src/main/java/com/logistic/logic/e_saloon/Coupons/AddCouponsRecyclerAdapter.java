package com.logistic.logic.e_saloon.Coupons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.logistic.logic.e_saloon.Model.CouponsDetails;
import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

public class AddCouponsRecyclerAdapter  extends RecyclerView.Adapter<AddCouponsRecyclerAdapter.ViewHolder> {

    private static int currentPosition = -1;
    Context context;
    RecyclerView recyclerView;
    List<CouponsDetails> couponsDetailsList=new ArrayList<>();

    public AddCouponsRecyclerAdapter(Context context, RecyclerView recyclerView,
                                     List<CouponsDetails> couponsDetailsList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.couponsDetailsList = couponsDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater
                .from(context)
                .inflate(R.layout.show_coupon_design,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        CouponsDetails couponsDetails=couponsDetailsList.get(i);
        Glide.with(context).load(couponsDetails.getImageUrl()).into(viewHolder.imageView);
        viewHolder.textViewName.setText(couponsDetails.getCouponTitle());
        viewHolder.textViewCode.setText(couponsDetails.getCouponCode());
        viewHolder.textViewDate.setText(" Valid till "+couponsDetails.getCouponValidity());
        viewHolder.textViewDetails.setText(couponsDetails.getCouponDetails());
        viewHolder.linearLayout.setVisibility(View.GONE);


        if (currentPosition == i) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
            viewHolder.linearLayout.setVisibility(View.VISIBLE);

            //adding sliding effect
            viewHolder.linearLayout.startAnimation(slideDown);
        }

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = i;

                //reloding the list
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return couponsDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textViewName,textViewCode,textViewDate,textView,textViewDetails;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.couponImageView);
            textViewName=itemView.findViewById(R.id.couponDescTextView);
            textViewCode=itemView.findViewById(R.id.couponCodeTextView);
            textViewDate=itemView.findViewById(R.id.validityDateTextView);
            textView=itemView.findViewById(R.id.action_details);
            textViewDetails=itemView.findViewById(R.id.coupon_details);
            linearLayout=itemView.findViewById(R.id.detailLayout);
        }
    }
}