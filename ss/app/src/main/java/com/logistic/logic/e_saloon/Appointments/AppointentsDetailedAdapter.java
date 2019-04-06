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

public class AppointentsDetailedAdapter extends RecyclerView.Adapter<AppointentsDetailedAdapter.ViewHolder> {

    RecyclerView recyclerView;
    Context context;
    List<ServicesBookedDetails> servicesBookedDetailsList=new ArrayList<>();


    public AppointentsDetailedAdapter(RecyclerView recyclerView, Context context,
                                      List<ServicesBookedDetails> servicesBookedDetailsList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.servicesBookedDetailsList = servicesBookedDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(context)
               .inflate(R.layout.order_id_appointment_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ServicesBookedDetails bookedDetails=servicesBookedDetailsList.get(i);
        viewHolder.textViewserviceName.setText(bookedDetails.getServiceName());
        if (Integer.parseInt(bookedDetails.getServiceQty())>1){
            viewHolder.textViewQty.setText(bookedDetails.getServiceQty());
           // viewHolder.textViewPrice.setText(" x"+" ₹"+bookedDetails.getServicePrice()+"=");
            //viewHolder.textViewPrice.setVisibility(View.GONE);
            viewHolder.textViewTotalPrice.setText("₹"+bookedDetails.serviceTotalPrice);

        }
        else {
            viewHolder.textViewQty.setVisibility(View.GONE);
            //viewHolder.textViewPrice.setVisibility(View.GONE);
            viewHolder.textViewTotalPrice.setText("₹"+bookedDetails.serviceTotalPrice);
        }
    }

    @Override
    public int getItemCount() {
        return servicesBookedDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewserviceName,textViewQty,textViewPrice,textViewTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewserviceName=itemView.findViewById(R.id.serviceName);
            textViewQty=itemView.findViewById(R.id.serviceQty);
            //textViewPrice=itemView.findViewById(R.id.servicePrice);
            textViewTotalPrice=itemView.findViewById(R.id.serviceTotalPrice);
        }
    }
}
