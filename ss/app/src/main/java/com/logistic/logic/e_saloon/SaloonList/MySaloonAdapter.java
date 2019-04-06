package com.logistic.logic.e_saloon.SaloonList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.logistic.logic.e_saloon.Model.SaloonAuthId;
import com.logistic.logic.e_saloon.Model.SaloonDetails;
import com.logistic.logic.e_saloon.Model.SaloonPhotoUri;
import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

public class MySaloonAdapter extends RecyclerView.Adapter<MySaloonAdapter.ViewHolder> {


    public interface  onItemClickListener{
        public void onItemClick(String key, String sname2, String address, String location, String mobile, String url);
    }

    Context context;
    RecyclerView recyclerView;
    List<SaloonDetails> saloonDetailsList=new ArrayList<SaloonDetails>();
    List<SaloonAuthId> saloonAuthIdList=new ArrayList<>();
    List<SaloonPhotoUri> saloonPhotoUriList=new ArrayList<>();
    public onItemClickListener listener;

    public MySaloonAdapter(Context context, RecyclerView recyclerView
            , List<SaloonDetails> saloonDetailsList
            , List<SaloonAuthId> saloonAuthIdList,List<SaloonPhotoUri> saloonPhotoUriList, onItemClickListener listener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.saloonDetailsList = saloonDetailsList;
        this.saloonAuthIdList = saloonAuthIdList;
        this.listener = listener;
        this.saloonPhotoUriList=saloonPhotoUriList;
    }

    @NonNull
    @Override
    public MySaloonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context)
                .inflate(R.layout.new_custom_saloonlist,viewGroup,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MySaloonAdapter.ViewHolder viewHolder, int i) {

        SaloonDetails details=saloonDetailsList.get(i);

        Glide.with(context).load(Uri.parse(details.getSaloonImageUrl())).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                    viewHolder.imageView.setBackground(resource);
                }
            }
        });
        viewHolder.nameTextView.setText(details.getSaloon_Name());
        viewHolder.addressTextView.setText(" "+details.getSaloon_Address()+" "+details.getSaloon_Location());
        viewHolder.timingTextView.setText(" "+details.getShopTiming());

    }

    @Override
    public int getItemCount() {
        return saloonDetailsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

       RelativeLayout imageView;
        TextView nameTextView, addressTextView;
        RatingBar ratingBar;
        CardView cardView;
        TextView timingTextView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            timingTextView=itemView.findViewById(R.id.saloonTimingTextView);
            imageView=itemView.findViewById(R.id.saloonPhotoImageView);
            nameTextView=itemView.findViewById(R.id.saloonNameTextView);
            addressTextView=itemView.findViewById(R.id.saloonAddressTextView);
            ratingBar=itemView.findViewById(R.id.ratingSaloon);
            cardView=itemView.findViewById(R.id.saloon_Name_CardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerView.getChildAdapterPosition(itemView);
                    SaloonDetails details=saloonDetailsList.get(position);
                    SaloonAuthId saloonAuthId=saloonAuthIdList.get(position);
                    String key=saloonAuthId.getAuthIdSaloon();
                    String sname=details.getSaloon_Name();
                    String location=details.getSaloon_Location();
                    String address=details.getSaloon_Address();
                    String mobile=details.getMobile_Number();
                    String  url=details.getSaloonImageUrl();
                    listener.onItemClick(key,sname,address,location,mobile,url);

                }
            });

        }
    }
}
