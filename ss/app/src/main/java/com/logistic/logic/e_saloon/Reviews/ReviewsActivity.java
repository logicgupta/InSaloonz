package com.logistic.logic.e_saloon.Reviews;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;
    RecyclerView recyclerView;
    TextView showStatusTextView;
    ProgressBar progressBar;
    List<ReviewState> reviewStateList=new ArrayList<>();
    private String saloonName;
    String rating;
    String name;
    String address;
    String ratingDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        showStatusTextView=findViewById(R.id.reviewsStatusTextView);
        progressBar=findViewById(R.id.reviewsProgressbar);
        recyclerView=findViewById(R.id.reviewsRecyclerView);
        SharedPreferences preferences=getApplicationContext()
                .getSharedPreferences("saloonName",0);
        saloonName=preferences.getString("sname",null);

        fetchFirebaseReviewData();
    }

    public void fetchFirebaseReviewData(){
        collectionReference=firestore
                .collection("ESaloon")
                .document("Review")
                .collection(saloonName);
        collectionReference
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot: task.getResult()){
                        Log.e("REview Activity"," "+snapshot.getData());
                        ReviewState reviewState=new ReviewState();
                        String rating=snapshot.get("rating").toString();
                        String desc=snapshot.get("reviewDescription").toString();
                        String name=snapshot.get("name").toString();
//                        String imageUrl=snapshot.get("imageUrl").toString();

                        reviewState.setNumStarsReviews(rating);
                        reviewState.setDescReviews(desc);
                        reviewState.setNameReviews(name);
                       // reviewState.setImgUrlReviews(imageUrl);
                        reviewStateList.add(reviewState);

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReviewsActivity.this));
                    ReviewAdapter adapter=new ReviewAdapter(getApplicationContext()
                            ,recyclerView,reviewStateList);
                    progressBar.setVisibility(View.GONE);
                    if (reviewStateList.isEmpty()){
                        showStatusTextView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
