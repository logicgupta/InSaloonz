package com.logistic.logic.e_saloon.BookingDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.logistic.logic.e_saloon.Model.BookingDetails;
import com.logistic.logic.e_saloon.Model.CartDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ConfirmBooking extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    DocumentReference documentReference;
    ProgressDialog progressDialog;
    List<BookingDetails> bookingDetailsList=new ArrayList<>();
    String saloonName;
    String calender_Date;
    String calenderMonth;
    String calenderYear;
    String calenderOriginalDate;
    String calenderTime;
    String calenderDayTime;
    String totalPrice;
     CollectionReference collectionReference2;
    String qty;
    String serviceType;
    String serviceDesc;
    String servicePrice;
    String serviceSaloonName;
    String orderId;
    String dob;
    String payId;
    String userEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        Intent intent=getIntent();
        calender_Date=intent.getStringExtra("Date");
        calenderMonth=intent.getStringExtra("Month");
        calenderYear=intent.getStringExtra("YEAR");
        calenderOriginalDate=intent.getStringExtra("DateFormat");
        calenderTime=intent.getStringExtra("time");
        calenderDayTime=intent.getStringExtra("DayTime");
        totalPrice=intent.getStringExtra("totalPrice");
        saloonName=intent.getStringExtra("saloonName");
        orderId=intent.getStringExtra("orderId");
        payId=intent.getStringExtra("payId");
        userEmailId=intent.getStringExtra("emailId");
        progressDialog=new ProgressDialog(ConfirmBooking.this);
        progressDialog.setMessage("Processing your Request !");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
        Random rand = new Random();
        int n = rand.nextInt(5999) + 1;
        orderId="ODKT14"+payId;
        fetchCartDetailsAndStoreAsOrder();


    }

    public void confirmBookingFirebase(){

        sendOrderDetailsToAdmin();

        bookingDetailsList.clear();

        collectionReference2=firestore.collection("ESaloon")
                .document("User")
                .collection("Client");
        firestore.collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Cart")
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(ConfirmBooking.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });

        collectionReference=firestore.collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Appointment")
                .collection(firebaseAuth.getUid());
        String name=firebaseAuth.getCurrentUser().getDisplayName();
        String email=firebaseAuth.getCurrentUser().getEmail();
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + (month+1) + "/" + year;
        dob=date;
        String doa=calenderOriginalDate;
        String toa=calenderTime;
        String tpayment=totalPrice;
        String status="1";


        final BookingDetails bookingDetails=
                new BookingDetails(name,email,dob,doa,toa,status,payId,orderId,totalPrice);

        collectionReference
                .add(bookingDetails)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            SharedPreferences preferences=getApplicationContext()
                                    .getSharedPreferences("saloonName",0);
                            saloonName=preferences.getString("sname",null);
                            collectionReference=firestore.collection("ESaloon")
                                    .document("owner")
                                    .collection("Appointment")
                                    .document("Booked")
                                    .collection(saloonName);
                            collectionReference.add(bookingDetails).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ConfirmBooking.this, "Booked!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            progressDialog.dismiss();
                            finish();
                            Toast.makeText(ConfirmBooking.this, "Booked!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    void fetchCartDetailsAndStoreAsOrder(){

        collectionReference=firestore.collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Cart")
                .collection(firebaseAuth.getUid());

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Log.e("Cart Saloon Activity", "" + documentSnapshot.getData());
                        CartDetails details = new CartDetails();
                        serviceType = documentSnapshot.get("serviceType").toString();
                        serviceDesc = documentSnapshot.get("serviceDesc").toString();
                        qty = documentSnapshot.get("serviceQty").toString();
                        servicePrice = documentSnapshot.get("serviceOriginalPrice").toString();
                        totalPrice = documentSnapshot.get("serviceTotalPrice").toString();
                        serviceSaloonName = documentSnapshot.get("saloonName").toString();
                        String docKey = documentSnapshot.getId();
                        details.setServiceType(serviceType);
                        details.setServiceDesc(serviceDesc);
                        details.setServiceQty(qty);
                        details.setServiceTotalPrice(totalPrice);
                        details.setSaloonName(serviceSaloonName);
                        saloonName=serviceSaloonName;
                        details.setServiceOriginalPrice(servicePrice);
                        documentReference=firestore.collection("ESaloon")
                                .document("User")
                                .collection("Client")
                                .document("Cart")
                                .collection(firebaseAuth.getUid()).document(documentSnapshot.getId());
                        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                }
                            }
                        });

                        collectionReference2=firestore
                                .collection("ESaloon")
                                .document("User")
                                .collection("Client")
                                .document("Order")
                                .collection(orderId);

                        collectionReference2.add(details)
                               .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                   @Override
                                   public void onComplete(@NonNull Task<DocumentReference> task) {

                                       if (task.isSuccessful()){
                                       }
                                   }
                               });

                    }
                    confirmBookingFirebase();
                }

            }
        });

    }

    public void sendOrderDetailsToAdmin(){

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "/" + (month+1) + "/" + year;
        dob=date;
        String status="0";

        /*
        collectionReference=firestore.collection("ESaloon")

                .document("AmountPaid").collection(saloonName);
         */

        HashMap<String,String> map=new HashMap<>();
        map.put("transactionId",payId);
        map.put("dob",dob);
        map.put("statusPay",status);
        map.put("orderId",orderId);
        map.put("amountUnPaid",totalPrice);
        map.put("amountPaid","0");
        map.put("status","1");
        map.put("saloonName",saloonName);
        map.put("userEmailId",userEmailId);

        collectionReference=firestore
                .collection("ESaloon")
                .document("amountUnPaid")
                .collection(saloonName);

        collectionReference.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ConfirmBooking.this, "Processing your Request!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
