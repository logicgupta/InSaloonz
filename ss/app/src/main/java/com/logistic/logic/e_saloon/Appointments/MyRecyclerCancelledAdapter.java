package com.logistic.logic.e_saloon.Appointments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerCancelledAdapter extends
        RecyclerView.Adapter<MyRecyclerCancelledAdapter.ViewHolder> {


    Context context;
    RecyclerView recyclerView;
    List<Appointment1> appointment1List=new ArrayList<>();

    public  MyRecyclerCancelledAdapter(Context context, RecyclerView recyclerView
            , List<Appointment1> appointment1List) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.appointment1List = appointment1List;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.appointment_layout1,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Appointment1 appointment1=appointment1List.get(i);
        viewHolder.textViewOrderId.setText("Appointment Id :"+appointment1.getOrderId());
        viewHolder.textViewOrderDate.setText("Appointment Booked Date:"+appointment1.getOrderDate());


    }

    @Override
    public int getItemCount() {
        return appointment1List.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewOrderId,textViewOrderDate,textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId=itemView.findViewById(R.id.orderId);
            textViewOrderDate=itemView.findViewById(R.id.orderBooked);

        }
    }
}
