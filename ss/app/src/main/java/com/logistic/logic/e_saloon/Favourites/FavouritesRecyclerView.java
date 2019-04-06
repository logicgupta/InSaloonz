package com.logistic.logic.e_saloon.Favourites;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.logistic.logic.e_saloon.Model.SaloonDetails;
import com.logistic.logic.e_saloon.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouritesRecyclerView extends RecyclerView.Adapter<FavouritesRecyclerView.ViewHolder>
{

    RecyclerView recyclerView;
    Context context;
    List<SaloonDetails> saloonDetailsList=new ArrayList<>();
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    CollectionReference collectionReference;

    public FavouritesRecyclerView(RecyclerView recyclerView,
                                  Context context, List<SaloonDetails> saloonDetailsList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.saloonDetailsList = saloonDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context)
                .inflate(R.layout.custom_favourites_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final SaloonDetails saloonDetails=saloonDetailsList.get(i);
        viewHolder.nameTextView.setText(saloonDetails.getSaloon_Name());
        viewHolder.addresstextView.setText(saloonDetails.getSaloon_Address());

        viewHolder.ratingBar.setNumStars(5);
        viewHolder.favoriteButton.setFavorite(true);
        viewHolder.timing.setText(saloonDetails.getShopTiming());
        viewHolder.favoriteButton.setOnFavoriteAnimationEndListener(new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
            @Override
            public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite){
                    String saloonKey=saloonDetails.getSaloon_key();
                    firestore=FirebaseFirestore.getInstance();
                    documentReference=firestore
                            .collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Favourites")
                            .collection(FirebaseAuth.getInstance().getUid()).document(saloonKey);
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("SaloonAuthKey",saloonKey);
                    Log.e("Sending Key"," "+FirebaseAuth.getInstance().getUid());
                    Log.e("Sending Key"," "+map.get("SaloonAuthKey"));

                    documentReference.set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(context, "Added To Favourites !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else {
                    String saloonKey=saloonDetails.getSaloon_key();
                    firestore=FirebaseFirestore.getInstance();
                    collectionReference=firestore
                            .collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Favourites")
                            .collection(FirebaseAuth.getInstance().getUid());

                    collectionReference.document(saloonKey).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Removed From Favourites !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

        if (saloonDetails.getSaloonImageUrl()!=null){

            Glide.with(context).load(Uri.parse(saloonDetails.getSaloonImageUrl())).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                        viewHolder.imageView.setBackground(resource);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return saloonDetailsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout imageView;
        TextView nameTextView,addresstextView;
        RatingBar ratingBar;
        MaterialFavoriteButton favoriteButton;
        TextView timing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timing=itemView.findViewById(R.id.saloonTimingTextView);
            imageView=itemView.findViewById(R.id.customfavouritesImageView);
            nameTextView=itemView.findViewById(R.id.customfavouritesNameTextView);
            addresstextView=itemView.findViewById(R.id.customfavouritesAddressTextView);
            ratingBar=itemView.findViewById(R.id.customfavouritesRatingBar);
            favoriteButton=itemView.findViewById(R.id.customfavouritesButton);
        }
    }
}
