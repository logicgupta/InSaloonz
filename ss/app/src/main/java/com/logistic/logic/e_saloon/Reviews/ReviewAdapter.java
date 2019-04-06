package com.logistic.logic.e_saloon.Reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    List<ReviewState> reviewStateList=new ArrayList<>();
    RecyclerView recyclerView;

    public ReviewAdapter(Context context,RecyclerView recyclerView, List<ReviewState> reviewStateList) {
        this.context = context;
        this.recyclerView=recyclerView;
        this.reviewStateList = reviewStateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_show_reviews,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ReviewState reviewState=reviewStateList.get(i);
        //Glide.with(context).load(reviewState.getImgUrlReviews()).into(viewHolder.imageView);
        if (Double.parseDouble(reviewState.getNumStarsReviews())>=5){

            viewHolder.ratingBar.setNumStars(5);
        }
        else  if (Double.parseDouble(reviewState.getNumStarsReviews())>=4 ){
            viewHolder.ratingBar.setNumStars(4);
        }
        else  if (Double.parseDouble(reviewState.getNumStarsReviews())>=3 ){
            viewHolder.ratingBar.setNumStars(3);
        }
        else  if (Double.parseDouble(reviewState.getNumStarsReviews())>=2 ){
            viewHolder.ratingBar.setNumStars(2);
        }
        else  if (Double.parseDouble(reviewState.getNumStarsReviews())>=0 ){
            viewHolder.ratingBar.setNumStars(1);
        }
        viewHolder.descReviews.setText(reviewState.getDescReviews());
        viewHolder.namePersonReview.setText(reviewState.getNameReviews());


    }

    @Override
    public int getItemCount() {
        return reviewStateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rateStateTextView,descReviews,namePersonReview;
        RatingBar ratingBar;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar=itemView.findViewById(R.id.reviewRatingBar);
            descReviews=itemView.findViewById(R.id.reviewsDescTextView);
            namePersonReview=itemView.findViewById(R.id.reviewsNameTextView);
            imageView=itemView.findViewById(R.id.userimage);
        }
    }
}
