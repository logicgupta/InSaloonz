package com.logistic.logic.e_saloon.Favourites;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.logistic.logic.e_saloon.Model.SaloonDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Favourites_Activity extends AppCompatActivity {

    ImageView imageView;
    TextView textView1,textView2;
    RecyclerView recyclerView;
    CollectionReference collectionReference;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    List<SaloonDetails> saloonDetailsList=new ArrayList<>();
    ProgressBar progressBar;
    String status="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_);
        imageView=findViewById(R.id.favouritesImageView1);
        textView1=findViewById(R.id.favouritesTextView1);
        textView2 =findViewById(R.id.favouritesTextView2);
        recyclerView=findViewById(R.id.favouritesRecyclerView);
        progressBar=findViewById(R.id.favouritesProgressBar);
        imageView.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        fetchFavourites();

    }

    public void fetchFavourites(){
        collectionReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Favourites").collection(firebaseAuth.getUid());

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    Log.e("Favourites Activity","Task Success !");
                    for (QueryDocumentSnapshot snapshot :task.getResult()){
                        status="yytcy";
                        Log.e("Favourites Activity",""+snapshot.getData());
                        final String key=snapshot.get("SaloonAuthKey").toString();
                        collectionReference=firestore.collection("ESaloon")
                                .document("owner").collection(key);
                        collectionReference.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Log.e("Favourites Activity","Task Success 2 !");

                                if (task.isSuccessful()){
                                    Log.e("Favourites Activity","Task Success 2 !");
                                    for (QueryDocumentSnapshot snapshot1 :task.getResult()){
                                        status="1";
                                        SaloonDetails saloonDetails=new SaloonDetails();
                                        saloonDetails.setSaloon_key(key);
                                        saloonDetails.setSaloonImageUrl(snapshot1.getString("saloonImageUrl"));
                                        saloonDetails.setSaloon_Name(snapshot1.get("sname").toString());
                                        saloonDetails.setSaloon_Address(snapshot1.get("address").toString());
                                        saloonDetails.setSaloon_Location(snapshot1.get("city").toString());
                                        saloonDetails.setShopTiming(snapshot1.getString("shopTiming"));
                                        Log.e("FavouritesActivity"," "+snapshot1.getData());
                                        saloonDetailsList.add(saloonDetails);
                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    FavouritesRecyclerView adapter=
                                            new FavouritesRecyclerView(recyclerView,getApplicationContext(),
                                            saloonDetailsList);
                                    progressBar.setVisibility(View.GONE);

                                    if (saloonDetailsList.isEmpty()){
                                        imageView.setVisibility(View.VISIBLE);
                                        textView1.setVisibility(View.VISIBLE);
                                        textView2.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                    recyclerView.setAdapter(adapter);
                                }
                                else {
                                    Log.e("Favourites Activity","Task Failed !");
                                    progressBar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    textView1.setVisibility(View.VISIBLE);
                                    textView2.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }
                        });


                    }
                    Log.e("Favourites Activity","Task Success 2 !"+status);
                    if (status=="0"){
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        textView1.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                    }
                    Log.e("Favourites Activity","Task Success 2 !");


                }
                else {
                    Log.e("Favourites Activity","Task Failure !");
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                }

            }
        });

    }
}
