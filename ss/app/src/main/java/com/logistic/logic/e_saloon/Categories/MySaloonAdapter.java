package com.logistic.logic.e_saloon.Categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.logistic.logic.e_saloon.Model.SaloonDetails;
import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MySaloonAdapter extends RecyclerView.Adapter<MySaloonAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    RecyclerView recyclerView;
    Context context;
    List<SaloonDetails> arrayList=new ArrayList<SaloonDetails>();
    public OnItemClickListener listener;


    public MySaloonAdapter(RecyclerView recyclerView,Context context, List<SaloonDetails> arrayList,
                           OnItemClickListener listener){

        this.recyclerView=recyclerView;
        this.context=context;
        this.arrayList=arrayList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_saloon_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        SaloonDetails details=arrayList.get(0);
        viewHolder.nameTextView.setText(details.getSaloon_Name());
        viewHolder.addresTextView.setText(details.getSaloon_Location());
        //viewHolder.circleImageView.setImageURI(Uri.parse(details.getSaloonImageUrl()));
        //viewHolder.RatingimageView.setImageURI(Uri.parse(details.getSaloon_Rating()));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView addresTextView;
        CircleImageView circleImageView;
        ImageView RatingimageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.saloonname_textView);
            addresTextView=itemView.findViewById(R.id.saloonloactionTextView);
            circleImageView=itemView.findViewById(R.id.saloonImage);
            RatingimageView=itemView.findViewById(R.id.ratingSaloon);
        }
    }
}
