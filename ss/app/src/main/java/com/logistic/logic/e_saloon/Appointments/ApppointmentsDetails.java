package com.logistic.logic.e_saloon.Appointments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApppointmentsDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView,textViewTotal;
    String orderId;
    CollectionReference collectionReference;
    FirebaseFirestore firestore;
    DocumentReference documentReference;
    int totalPrice;
    List<ServicesBookedDetails> servicesBookedDetailsList=new ArrayList<>();
    String serviceName,serviceQty,servicePrice,serviceTotalPrice;
    TextView textViewReview;
    String saloonName;
    RatingBar ratingBar;
    EditText editText;
    Button button;
    TextView tax;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apppointments_details);
        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.date);
        recyclerView=findViewById(R.id.detailsAppointmentRecyclerView);
        textViewTotal=findViewById(R.id.totalTextView);
        textViewReview=findViewById(R.id.writeReviewTextView);
        tax=findViewById(R.id.taxTextView);

        Intent intent=getIntent();
        String doa=intent.getStringExtra("doa");
        String toa=intent.getStringExtra("toa");
        final String key=intent.getStringExtra("key");

        orderId=intent.getStringExtra("orderId");

        textView.setText(doa+","+toa);
        Log.e("ApppointmentDetais"," "+orderId);
        fetchOrderDetails();
        cancel=findViewById(R.id.cancelAppointment);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionReference=firestore
                        .collection("ESaloon")
                        .document("User")
                        .collection("Client")
                        .document("Appointment")
                        .collection(FirebaseAuth.getInstance().getUid());

                collectionReference.document(key)
                        .update("status","-1")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("ckcbc","Success *-**-*-*-*-*-*-*");


                                            /*     Admin Cancel Show Status
                                collectionReference=firestore
                                        .collection("ESaloon")
                                        .document("amountUnPaid")
                                        .collection(saloonName);
                                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot snapshot:task.getResult()){

                                            }
                                        }

                                    }
                                });*/











                                Toast.makeText(ApppointmentsDetails.this, "Appointment Cancelled !", Toast.LENGTH_SHORT).show();
                                Toast.makeText(ApppointmentsDetails.this, "Our Team  Will Contact You soon !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.e("ckcbc","Failed *-**-*-*-*-*-*-*");

                        Toast.makeText(ApppointmentsDetails.this, "Please Try Again After Some Time", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        textViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(ApppointmentsDetails.this);
                setContentView(R.layout.review_dialog);
                ratingBar=findViewById(R.id.rateBar);
                editText=findViewById(R.id.descReview);

                button=findViewById(R.id.submit_review);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String rating=String.valueOf(ratingBar.getRating());
                        if (editText.getText().toString().equals("")){
                            Toast.makeText(ApppointmentsDetails.this, "Please Write Something !", Toast.LENGTH_SHORT).show();
                        }
                        else if (rating.equals("")){
                            Toast.makeText(ApppointmentsDetails.this, "Please Provide some rating", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String desc=editText.getText().toString();
                            editText.setText("");
                            collectionReference=firestore
                                    .collection("ESaloon")
                                    .document("Review")
                                    .collection(saloonName);
                            SharedPreferences sharedPreferences=
                                    getApplicationContext().getSharedPreferences("personalDetails",0);
                            String name=sharedPreferences.getString("name",null);
                            String imgUrl=sharedPreferences.getString("imageUrl",null);
                            Map<String,String> map=new HashMap<>();
                            map.put("rating",rating);
                            map.put("reviewDescription",desc);
                            map.put("name",name);
                            map.put("imageUrl",imgUrl);
                            collectionReference.add(map)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(ApppointmentsDetails.this, "Success !", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                finish();
                                            }
                                        }
                                    });


                        }
                    }
                });
                dialog.show();


            }
        });



    }

    void fetchOrderDetails(){

        collectionReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Order")
                .collection(orderId);



        collectionReference
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot :task.getResult()){
                        ServicesBookedDetails details=new ServicesBookedDetails();
                        serviceName=snapshot.get("serviceType").toString();
                        serviceQty=snapshot.get("serviceQty").toString();
                        servicePrice=snapshot.get("serviceOriginalPrice").toString();
                        saloonName=snapshot.get("saloonName").toString();
                        serviceTotalPrice=snapshot.get("serviceTotalPrice").toString();
                        totalPrice+=Integer.parseInt(serviceTotalPrice);
                        details.setServiceName(serviceName);
                        details.setServiceQty(serviceQty);
                        details.setServicePrice(servicePrice);
                        details.setServiceTotalPrice(serviceTotalPrice);
                        servicesBookedDetailsList.add(details);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        AppointentsDetailedAdapter adapter=new AppointentsDetailedAdapter(recyclerView,
                                ApppointmentsDetails.this,servicesBookedDetailsList);

                        recyclerView.setAdapter(adapter);

                        int total=totalPrice+(totalPrice*18)/100;
                        int extr=(totalPrice*18)/100;
                        tax.setText("Rs."+extr);
                        textViewTotal.setText(" Rs."+total);
                    }
                }
            }
        });


    }


}
