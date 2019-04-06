package com.logistic.logic.e_saloon.Categories;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.logistic.logic.e_saloon.Configure.ConfigName;
import com.logistic.logic.e_saloon.CustomerChat.ChatActivity;
import com.logistic.logic.e_saloon.Model.CategoryData1;
import com.logistic.logic.e_saloon.R;
import com.logistic.logic.e_saloon.Reviews.ReviewsActivity;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class MaleSpaActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    DocumentReference documentReference;
    String saloonName;
    String saloonKey;
    Bundle bundle;
    String categoryType;
    String categoryDesc;
    String categoryPrice;
    FirebaseAuth firebaseAuth;
    List<CategoryData1> categoryData1List=new ArrayList<CategoryData1>();
    String saloonAddress;
     String saloonLocation;
     String saloonnMobile;

    TextView saloonNameTextView;
    TextView saloonContactFacebook,saloonContactAddress,saloonContactMobile;
    TextView saloonContactShare;
    MaterialFavoriteButton favoriteButton;
    String saloonImage;
    RelativeLayout relativeLayout;
    ImageView optionsImageView,backImageView;
    CoordinatorLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_male_spa);

        /**
         *      Getting Saloon Name and Authkey.
         */
        getSupportActionBar().hide();
        bundle=getIntent().getExtras();
        saloonName=bundle.getString("sname",null);
        saloonKey=bundle.getString("key",null);
        saloonAddress=bundle.getString("address",null);
        saloonLocation=bundle.getString("location",null);
        saloonnMobile=bundle.getString("mobile",null);
        saloonImage=bundle.getString("imgUrl",null);


        relativeLayout=findViewById(R.id.SaloonImagesLayout);

        Glide.with(this).load(Uri.parse(saloonImage)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                    relativeLayout.setBackground(resource);
                }
            }
        });

        saloonNameTextView=findViewById(R.id.saloonName);
        constraintLayout=findViewById(R.id.main_content);

        favoriteButton=findViewById(R.id.saloonFavourites);
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        backImageView=findViewById(R.id.backImageView);
        optionsImageView=findViewById(R.id.optionImageView);
        favoriteButton.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                        //
                        if (favorite){
                            addFavouritesFirebase();
                        }
                        else {
                            removeFavouritesFirebase();
                        }
                    }
                });
        collectionReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Favourites").collection(firebaseAuth.getUid());

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot :task.getResult()){
                        String key=snapshot.get("SaloonAuthKey").toString();
                        if (key.equalsIgnoreCase(saloonKey)){
                            favoriteButton.setFavorite(true);
                        }
                    }

                }
            }
        });




        saloonNameTextView.setText(saloonName);


        saloonNameTextView.setText(saloonName);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        optionsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar=Snackbar.make(constraintLayout
                        ,""
                        ,Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout layout=
                        (Snackbar.SnackbarLayout)
                                snackbar.getView();
                LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
                View snackView =inflater.inflate(R.layout.my_saloon_snackbar,null);
                snackView.setBackgroundColor(Color.WHITE);
                saloonContactShare=snackView.findViewById(R.id.contactShare);
                saloonContactFacebook=snackView.findViewById(R.id.contactChat);
                saloonContactAddress=snackView.findViewById(R.id.contactAddress);
                saloonContactMobile=snackView.findViewById(R.id.contactReviews);
                // Share                                       ////////
                saloonContactShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                        intent.putExtra(Intent.EXTRA_SUBJECT,"Hi Try Out This Saloon !");
                        intent.putExtra(Intent.EXTRA_TEXT,"link to "+saloonName);
                        startActivity(Intent.createChooser(intent,"Share It !"));


                    }
                });

                // Facebook                              ///////////
                saloonContactFacebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent=new
                                Intent(MaleSpaActivity.this,ChatActivity.class);
                        intent.putExtra("sname",saloonName);
                        startActivity(intent);



                    }
                });

                /// Address                         /////////
                saloonContactAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q="+saloonAddress+" ,"+saloonLocation));

                        startActivity(intent);
                    }
                });

                // Chat                           /     /////
                saloonContactMobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        startActivity(new Intent(MaleSpaActivity.this,ReviewsActivity.class));

                    }
                });
                layout.setPadding(0,0,0,0);
                layout.addView(snackView,0);
                snackbar.show();


            }
        });



        firestore=FirebaseFirestore.getInstance();
        viewPager=findViewById(R.id.viewPagermalespa);
        MaleSpaPagerAdapter mypageradpater=new
                MaleSpaPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mypageradpater);
        tabLayout=findViewById(R.id.tabLayoutmalespa);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void addFavouritesFirebase(){

        firestore=FirebaseFirestore.getInstance();
        documentReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Favourites")
                .collection(FirebaseAuth.getInstance().getUid()).document(saloonKey);
        Map<String,String> map=new HashMap<String,String>();
        map.put("SaloonAuthKey",saloonKey);
        Log.e("Sending Key"," "+FirebaseAuth.getInstance().getUid());
        Log.e("Sending Key"," "+map.get("SaloonAuthKey"));

        documentReference.set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MaleSpaActivity.this, "Added To Favourites !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void removeFavouritesFirebase(){

        collectionReference=firestore
                .collection("ESaloon")
                .document("User")
                .collection("Client")
                .document("Favourites")
                .collection(FirebaseAuth.getInstance().getUid());

        collectionReference.document(saloonKey).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(MaleSpaActivity.this, "Removed From Favourites !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    public void fetchFirebaseMaleSpaExpress(){

        Log.e("Female Hair Style "," ++++ "+saloonName);
        collectionReference=firestore
                .collection("ESaloon")
                .document("SaloonName")
                .collection(saloonName)
                .document(ConfigName.SPA_SALOON_KEY)
                .collection(ConfigName.MALE_SPA_EXPRESS_SALOON_KEY);

        collectionReference
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        Log.e("Male SPA EXPRESS"," "+snapshot.getData());
                        CategoryData1 categoryData=new CategoryData1();
                        categoryType=snapshot.get("serviceType").toString();
                        categoryDesc=snapshot.get("serviceDesc").toString();
                        categoryPrice=snapshot.get("servicePrice").toString();
                        categoryData.setType(categoryType);
                        categoryData.setDesc(categoryDesc);
                        categoryData.setPrice(categoryPrice);
                        categoryData1List.add(categoryData);
                    }
                }
            }
        });
    }


    public void fetchFirebaseMaleSpaMassages(){

        Log.e("Female Hair Style "," ++++ "+saloonName);
        collectionReference=firestore
                .collection("ESaloon")
                .document("SaloonName")
                .collection(saloonName)
                .document(ConfigName.SPA_SALOON_KEY)
                .collection(ConfigName.MALE_SPA_MASSAGE_SALOON_KEY);

        collectionReference
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot snapshot : task.getResult()){

                        Log.e("MALE SPA MASSAGES"," "+snapshot.getData());
                        CategoryData1 categoryData=new CategoryData1();
                        categoryType=snapshot.get("serviceType").toString();
                        categoryDesc=snapshot.get("serviceDesc").toString();
                        categoryPrice=snapshot.get("servicePrice").toString();
                        categoryData.setType(categoryType);
                        categoryData.setDesc(categoryDesc);
                        categoryData.setPrice(categoryPrice);
                        categoryData1List.add(categoryData);
                    }
                }
            }
        });
    }
}
