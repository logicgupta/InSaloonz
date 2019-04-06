package com.logistic.logic.e_saloon.Categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logistic.logic.e_saloon.Model.CategoryData1;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnButtonClick(int position);
        void OnDeleteButtonClick(int position);
    }


    RecyclerView recyclerView;
    Context context;
    List<CategoryData1> categoryData1s=new ArrayList<CategoryData1>();
    int qty=0;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;
    DocumentReference documentReference;

    public OnItemClickListener listener;

    public MyRecyclerAdapter(RecyclerView recyclerView,Context context,List<CategoryData1> categoryData1s,OnItemClickListener listener){
        this.recyclerView=recyclerView;
        this.context=context;
        this.listener=listener;
        this.categoryData1s=categoryData1s;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_style_layout,viewGroup,false);
    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        final CategoryData1 list=categoryData1s.get(i);
        viewHolder.textViewType.setText(list.getType());
        viewHolder.textViewDesc.setText(list.getDesc());
        Glide.with(context).load(list.getImageUrl()).into(viewHolder.imageView);
        viewHolder.textViewPrice.setText("₹ "+list.getPrice());
        viewHolder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.book.setVisibility(View.GONE);
                viewHolder.linearLayout.setVisibility(View.VISIBLE);
                if (viewHolder.showQtyTextView.getText().toString().equalsIgnoreCase("Book")){
                    viewHolder.showQtyTextView.setText("1");
                    firestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    String prc=list.getPrice();
                    documentReference=firestore.collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Cart")
                            .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("serviceType",viewHolder.textViewType.getText().toString());
                    map.put("serviceDesc",viewHolder.textViewDesc.getText().toString());
                    map.put("serviceQty",viewHolder.showQtyTextView.getText().toString());
                    map.put("serviceOriginalPrice",prc);
                    map.put("imageUrl",list.getImageUrl());
                    map.put("serviceTotalPrice",list.getPrice());
                    map.put("saloonName",list.getSaloonName());
                    documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Check Out your Cart !" +
                                        "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    String abc=viewHolder.showQtyTextView.getText().toString();
                    String prc=list.getPrice();
                    int price=Integer.parseInt(prc);
                    int x=Integer.parseInt(abc)+1;
                    firestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    documentReference=firestore.collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Cart")
                            .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("serviceType",viewHolder.textViewType.getText().toString());
                    map.put("serviceDesc",viewHolder.textViewDesc.getText().toString());
                    map.put("serviceQty",""+x);
                    map.put("imageUrl",list.getImageUrl());
                    map.put("serviceOriginalPrice",prc);
                    map.put("saloonName",list.getSaloonName());

                    int total_price=price*x;
                    map.put("serviceTotalPrice",String.valueOf(total_price ));

                    documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Check Out your Cart !" +
                                        "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    viewHolder.showQtyTextView.setText(""+x);
                }


            }
        });
        viewHolder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.showQtyTextView.getText().toString().equalsIgnoreCase("Book")){
                    viewHolder.showQtyTextView.setText("1");
                    firestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    String prc=list.getPrice();
                    documentReference=firestore.collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Cart")
                            .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());

                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("serviceType",viewHolder.textViewType.getText().toString());
                    map.put("serviceDesc",viewHolder.textViewDesc.getText().toString());
                    map.put("serviceQty",viewHolder.showQtyTextView.getText().toString());
                    map.put("serviceOriginalPrice",prc);
                    map.put("imageUrl",list.getImageUrl());
                    map.put("serviceTotalPrice",list.getPrice());
                    map.put("saloonName",list.getSaloonName());
                    documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Check Out your Cart !" +
                                        "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    String abc=viewHolder.showQtyTextView.getText().toString();
                    String prc=list.getPrice();
                    int price=Integer.parseInt(prc);
                    int x=Integer.parseInt(abc)+1;
                    firestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    documentReference=firestore.collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Cart")
                            .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("serviceType",viewHolder.textViewType.getText().toString());
                    map.put("serviceDesc",viewHolder.textViewDesc.getText().toString());
                    map.put("serviceQty",""+x);
                    map.put("imageUrl",list.getImageUrl());
                    map.put("serviceOriginalPrice",prc);
                    map.put("saloonName",list.getSaloonName());

                    int total_price=price*x;
                    map.put("serviceTotalPrice",String.valueOf(total_price ));

                    documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Check Out your Cart !" +
                                        "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    viewHolder.showQtyTextView.setText(""+x);
                }

            }
        });
        viewHolder.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty=--qty;
                String abc=viewHolder.showQtyTextView.getText().toString();
                int x=Integer.parseInt(abc);

                if (x>1){
                    x--;
                    firestore=FirebaseFirestore.getInstance();
                    firebaseAuth=FirebaseAuth.getInstance();
                    documentReference=firestore.collection("ESaloon")
                            .document("User")
                            .collection("Client")
                            .document("Cart")
                            .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("serviceType",viewHolder.textViewType.getText().toString());
                    map.put("serviceDesc",viewHolder.textViewDesc.getText().toString());
                    String prc=list.getPrice();
                    int price=Integer.parseInt(prc);
                    int total_price=price*x;
                    map.put("serviceQty",""+x);
                    map.put("imageUrl",list.getImageUrl());
                    map.put("serviceOriginalPrice",prc);
                    map.put("serviceTotalPrice",String.valueOf(total_price ));

                    map.put("saloonName",list.getSaloonName());

                    documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(context, "Check Out your Cart !" +
                                        "", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    viewHolder.showQtyTextView.setText(""+x);
                }
                    if (x==1){
                        viewHolder.book.setVisibility(View.VISIBLE);
                        viewHolder.linearLayout.setVisibility(View.GONE);
                        firestore=FirebaseFirestore.getInstance();
                        firebaseAuth=FirebaseAuth.getInstance();
                        documentReference=firestore.collection("ESaloon")
                                .document("User")
                                .collection("Client")
                                .document("Cart")
                                .collection(firebaseAuth.getUid()).document(viewHolder.textViewType.getText().toString());
                        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Successfully Removed from cart !", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryData1s.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewType,textViewDesc,textViewPrice;
        TextView addTextView,deleteTextView,showQtyTextView;
        Button book;
        LinearLayout linearLayout;
        ImageView imageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            linearLayout=itemView.findViewById(R.id.showplusMinusButtonLayout);
            imageView=itemView.findViewById(R.id.type_image);
            book=itemView.findViewById(R.id.productAddService);
            textViewType=itemView.findViewById(R.id.type_service);
            textViewDesc=itemView.findViewById(R.id.decription);
            textViewPrice=itemView.findViewById(R.id.price);
            addTextView=itemView.findViewById(R.id.addCategory);
            deleteTextView=itemView.findViewById(R.id.deleteCategory);
            showQtyTextView=itemView.findViewById(R.id.showQty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerView.getChildLayoutPosition(itemView);
                }
            });
            addTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerView.getChildLayoutPosition(itemView);
                    listener.OnButtonClick(position);

                }
            });
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerView.getChildLayoutPosition(itemView);
                    listener.OnItemClick(position);
                }
            });
        }
    }

}
