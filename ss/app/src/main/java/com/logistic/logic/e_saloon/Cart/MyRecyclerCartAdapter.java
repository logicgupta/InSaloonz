package com.logistic.logic.e_saloon.Cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.logistic.logic.e_saloon.Model.CartDetails;
import com.logistic.logic.e_saloon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecyclerCartAdapter extends RecyclerView.Adapter<MyRecyclerCartAdapter.ViewHolder> {

    interface OnClickListener{
        public void onDeleteClickListener(int position,String key);
    }

    Context context;
    RecyclerView recyclerView;
    List<CartDetails> cartDetailsList =new ArrayList<CartDetails>();
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    DocumentReference documentReference;
    OnClickListener listener;
    int totalPrice;

    public MyRecyclerCartAdapter(RecyclerView recyclerView,Context context,
                                 List<CartDetails> cartDetailsList ,OnClickListener listener){
        this.recyclerView=recyclerView;
        this.context=context;
        this.cartDetailsList=cartDetailsList;
        this.listener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.custom_cart_details,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CartDetails details=cartDetailsList.get(i);
        viewHolder.serviceType.setText(details.getServiceType());
        Glide.with(context).load(details.getImageUrl()).into(viewHolder.imageView);
        viewHolder.serviceQty.setText(details.getServiceQty());
        viewHolder.servicePrice.setText("  Rs."+details.getServiceOriginalPrice());

    }

    @Override
    public int getItemCount() {
        return cartDetailsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView serviceType,serviceDesc,serviceQty,servicePrice;
        TextView serviceTotalPrice,deleteService;
        CircleImageView imageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            serviceType=itemView.findViewById(R.id.cartServiceType);
            imageView=itemView.findViewById(R.id.imageViewCart);
            serviceQty=itemView.findViewById(R.id.cartServiceQty);
            servicePrice=itemView.findViewById(R.id.cartServicePrice);
            deleteService=itemView.findViewById(R.id.cartServiceDelete);
            deleteService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerView.getChildAdapterPosition(itemView);
                    final CartDetails details=cartDetailsList.get(position);
                    String key=details.getDocKey();
                    listener.onDeleteClickListener(position,key);
                }
            });
        }

    }
}
