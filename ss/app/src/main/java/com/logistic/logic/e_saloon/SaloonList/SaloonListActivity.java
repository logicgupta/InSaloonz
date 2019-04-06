package com.logistic.logic.e_saloon.SaloonList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;
import com.logistic.logic.e_saloon.Categories.FemaleBeautyActivity;
import com.logistic.logic.e_saloon.Categories.FemaleNailActivity;
import com.logistic.logic.e_saloon.Categories.FemaleSpaActivity;
import com.logistic.logic.e_saloon.Categories.HairStyleFemaleActivity;
import com.logistic.logic.e_saloon.Categories.MaleBeautyActivity;
import com.logistic.logic.e_saloon.Categories.MaleHairActivity;
import com.logistic.logic.e_saloon.Categories.MaleShaveActivity;
import com.logistic.logic.e_saloon.Categories.MaleSpaActivity;
import com.logistic.logic.e_saloon.Configure.ConfigName;
import com.logistic.logic.e_saloon.Model.SaloonAuthId;
import com.logistic.logic.e_saloon.Model.SaloonDetails;
import com.logistic.logic.e_saloon.Model.SaloonPhotoUri;
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
import java.util.Collections;
import java.util.List;

public class SaloonListActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    MySaloonAdapter adapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    DocumentReference documentReference;
    CollectionReference collectionReference;
    List<SaloonDetails> saloonDetailsList=new ArrayList<>();
    String sname;
    String address;
    String mobile;
    String city;
    String rating;
    String imageUri;
    String imageUrl;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    String saloonSelectedKey;
    String categoryType;
    Bundle bundle;
    Button sortButton,filterButton;
    RelativeLayout relativeLayout;
    String sortBy;
    String skey;
    List<SaloonAuthId> saloonAuthIdList=new ArrayList<>();
    RangeBar rangeBar;
    EditText filterSaloonNameEdiText;
    EditText filterSaloonCityEdiText;
    Button filterApplyButton;
    ProgressDialog filterProgressDialog;
    String rightRangeBarValue,leftRangeBarValue;
    String filterSaloonName;
    String filterSaloonCity;
    List<SaloonPhotoUri> saloonPhotoUriList=new ArrayList<>();

    CollectionReference collectionReference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_list);
        getSupportActionBar().hide();
        Toast.makeText(this, "Select Favourite Saloon !", Toast.LENGTH_SHORT).show();
        progressBar=this.findViewById(R.id.addProgress);
        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.statusSaloonTextView);
        recyclerView=findViewById(R.id.listsaloon_recyclerView1);
        relativeLayout=findViewById(R.id.saloon_List_Layout);
        bundle=getIntent().getExtras();
        categoryType=bundle.getString("category");

        sortButton=findViewById(R.id.sortButton);
        filterButton=findViewById(R.id.filterButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar=Snackbar.make(relativeLayout
                        ,""
                        ,Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout layout=
                        (Snackbar.SnackbarLayout)
                                snackbar.getView();
                LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
                View snackView =inflater.inflate(R.layout.my_snackbar,null);
                snackView.setBackgroundColor(Color.WHITE);
                RadioGroup radioGroup = snackView.findViewById(R.id.yourRadioGroup);
                RadioButton r1=snackView.findViewById(R.id.priceRadio1);
                RadioButton r2=snackView.findViewById(R.id.priceRadio1);
                RadioButton r3=snackView.findViewById(R.id.priceRadio1);
                RadioButton r4=snackView.findViewById(R.id.priceRadio1);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.priceRadio1:
                                fetchSaloonDetailsFilterBy("rel","xyz");
                                Toast.makeText(SaloonListActivity.this, "Updating !", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.priceRadio2:
                                fetchSaloonDetailsFilterBy("5","rating");
                                Toast.makeText(SaloonListActivity.this, "Updating !", Toast.LENGTH_SHORT).show();
                                break;
                                case R.id.priceRadio3:
                                    fetchSaloonDetailsFilterBy("100","Low");
                                    Toast.makeText(SaloonListActivity.this, "Updating !", Toast.LENGTH_SHORT).show();
                                break;
                                case R.id.priceRadio4:
                                    fetchSaloonDetailsFilterBy("400","High");
                                    Toast.makeText(SaloonListActivity.this, "Updating !", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });


                layout.setPadding(0,0,0,0);
                layout.addView(snackView,0);
                snackbar.show();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SaloonListActivity.this);
                dialog.setContentView(R.layout.activity_filter);
                dialog.show();
                rangeBar=dialog.findViewById(R.id.rangebar1);
                Toast.makeText(SaloonListActivity.this, "Hiiii", Toast.LENGTH_SHORT).show();
                filterSaloonNameEdiText=dialog.findViewById(R.id.filterSaloonName);
                filterSaloonCityEdiText=dialog.findViewById(R.id.filterSaloonCity);
                filterApplyButton=dialog.findViewById(R.id.filterApplyButton);
                rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex
                            , int rightPinIndex, String leftPinValue, String rightPinValue) {
                        rightRangeBarValue=rightPinValue;
                        leftRangeBarValue=leftPinValue;
                              }
                });

                filterApplyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterSaloonName=filterSaloonNameEdiText.getText().toString();
                        filterSaloonCity=filterSaloonCityEdiText.getText().toString();

                        fetchSaloonDetailsFilterBy(filterSaloonName,filterSaloonCity
                                ,leftRangeBarValue,rightRangeBarValue);
                        Toast.makeText(SaloonListActivity.this, "Left"+leftRangeBarValue, Toast.LENGTH_SHORT).show();
                        Toast.makeText(SaloonListActivity.this, "Right"+rightRangeBarValue, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
            }
        });

        fetchSaloonDetails();

    }
    public void fetchSaloonDetailsFilterBy(final String filterBySaloonName
            , final String filterBySaloonCity,String filterByLeftRating,String filterByRightRating){

        saloonDetailsList.clear();
        documentReference=firestore
                .collection("ESaloon")
                .document("SaloonName");


        documentReference.collection("Authids")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (final QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        skey=documentSnapshot.get("Ids").toString();
                        final SaloonAuthId saloonAuthId=new SaloonAuthId();
                        saloonAuthId.setAuthIdSaloon(skey);
                        saloonAuthIdList.add(saloonAuthId);
                        Log.e("nnbkvjkvxc","ckjc  "+skey);
                        collectionReference=firestore.collection("ESaloon")
                                .document("owner").collection(skey);
                        collectionReference
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for( QueryDocumentSnapshot documentSnapshot1: task.getResult() ) {

                                        Log.e("nnbkvjkv", "ckjc  " + documentSnapshot1.getData());
                                        Log.e("Saloon List Activity", "Saloon Auth key" + skey);

                                        SaloonDetails saloonDetails = new SaloonDetails();

                                        sname = documentSnapshot1.get("sname").toString();
                                        address = documentSnapshot1.get("address").toString();
                                        city = documentSnapshot1.get("city").toString();
                                        String imgUrl=documentSnapshot1.getString("saloonImageUrl");
                                        String phoneNumber = documentSnapshot1.get("mobileNumber").toString();
                                        String timing=documentSnapshot1.getString("shopTiming");
                                        rating = "2";
                                        saloonSelectedKey = documentSnapshot1.getId();
                                        saloonDetails.setMobile_Number(phoneNumber);
                                        if (filterBySaloonName!=null && filterBySaloonCity!=null){
                                            if (filterBySaloonName.equalsIgnoreCase(sname)
                                                    && filterBySaloonCity.equalsIgnoreCase(city)){
                                                saloonDetails.setSaloon_Name(sname);
                                                saloonDetails.setSaloon_Address(address);
                                                saloonDetails.setSaloon_Location(city);
                                                saloonDetails.setSaloon_Rating(rating);
                                                saloonDetails.setSaloon_key(skey);
                                                saloonDetails.setShopTiming(timing);
                                                saloonDetails.setSaloonImageUrl(imgUrl);
                                                saloonDetails.setTotalPrice(Integer.parseInt(documentSnapshot1.get("totalPrice").toString()));
                                                saloonDetailsList.add(saloonDetails);
                                            }
                                        }
                                        else if (filterBySaloonName!=null){
                                            saloonDetails.setSaloon_Name(sname);
                                            saloonDetails.setSaloon_Address(address);
                                            saloonDetails.setSaloon_Location(city);
                                            saloonDetails.setSaloon_Rating(rating);
                                            saloonDetails.setSaloon_key(skey);
                                            saloonDetails.setTotalPrice(Integer.parseInt(documentSnapshot1.get("totalPrice").toString()));
                                            saloonDetailsList.add(saloonDetails);
                                        }


                                    }
                                    Log.e("nnbkvjkvxc","ckjc"+saloonDetailsList);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(
                                            new LinearLayoutManager(SaloonListActivity.this));
                                    adapter=new MySaloonAdapter(
                                            SaloonListActivity.this,
                                            recyclerView, saloonDetailsList
                                            ,saloonAuthIdList
                                            ,saloonPhotoUriList, new MySaloonAdapter.onItemClickListener() {
                                        @Override
                                        public void onItemClick(String key, String sname2,
                                                                String address, String location, String mobile, String url) {


                                            if (categoryType.equals(ConfigName.MALE_HAIR_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        MaleHairActivity.class);

                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();
                                                Log.e("Saloon list Actvivty",""+key);

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if(categoryType.equals(ConfigName.FEMALE_HAIR_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        HairStyleFemaleActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.MALE_BEAUTY_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        MaleBeautyActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();
                                                Log.e("List Saloon ACTIVITY","mALE bEAUTY iNTENT");

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.FEMALE_BEAUTY_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        FemaleBeautyActivity.class);
                                                Log.e("List Saloon ACTIVITY","FEMALE bEAUTY iNTENT");
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();
                                                Log.e("Saloon list Actvivty",""+key);

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.MALE_SPA_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        MaleSpaActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.FEMALE_SPA_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        FemaleSpaActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.FEMALE_NAIL_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        FemaleNailActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("key",key);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.MALE_SHAVE_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        MaleShaveActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }



                                        }
                                    }
                                    );
                                    progressBar.setVisibility(View.GONE);
                                    if (sname!=filterBySaloonName){
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                    recyclerView.setAdapter(adapter);
                                }

                            }
                        });


                    }
                }
            }
        });
    }

    public void fetchSaloonDetailsFilterBy(final String filter, final String filterType){
        textView.setVisibility(View.GONE);
        saloonDetailsList.clear();
        documentReference=firestore
                .collection("ESaloon")
                .document("SaloonName");


        documentReference.collection("Authids")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (final QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        skey=documentSnapshot.get("Ids").toString();
                        final SaloonAuthId saloonAuthId=new SaloonAuthId();
                        saloonAuthId.setAuthIdSaloon(skey);
                        saloonAuthIdList.add(saloonAuthId);
                        Log.e("nnbkvjkvxc","ckjc  "+skey);
                        collectionReference=firestore.collection("ESaloon")
                                .document("owner").collection(skey);
                        collectionReference
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                   for( QueryDocumentSnapshot documentSnapshot1: task.getResult() ){

                                       Log.e("nnbkvjkv","ckjc  "+documentSnapshot1.getData());
                                       Log.e("Saloon List Activity","Saloon Auth key"+skey);

                                       SaloonDetails saloonDetails=new SaloonDetails();

                                        sname=documentSnapshot1.get("sname").toString();
                                        address=documentSnapshot1.get("address").toString();
                                        city=documentSnapshot1.get("city").toString();
                                        String imgUrl=documentSnapshot1.getString("saloonImageUrl");
                                        String phoneNumber=documentSnapshot1.get("mobileNumber").toString();
                                        rating="2";
                                       String timing=documentSnapshot1.getString("shopTiming");
                                        saloonSelectedKey=documentSnapshot1.getId();
                                        saloonDetails.setMobile_Number(phoneNumber);
                                        saloonDetails.setSaloon_Name(sname);
                                        saloonDetails.setSaloonImageUrl(imgUrl);          // Saloon Shop  Image
                                        saloonDetails.setSaloon_Address(address);
                                        saloonDetails.setSaloon_Location(city);
                                        saloonDetails.setSaloon_Rating(rating);
                                        saloonDetails.setSaloon_key(skey);
                                       saloonDetails.setShopTiming(timing);
                                       saloonDetails.setTotalPrice(Integer.parseInt(documentSnapshot1.get("totalPrice").toString()));
                                       Log.e("Saloon Image Url"," "+imgUrl);

                                       saloonDetailsList.add(saloonDetails);


                                   }
                                        if (filter.equals("400") && filterType.equals("Low")){
                                            Collections.sort(saloonDetailsList,SaloonDetails.BY_PRICE_LOW);
                                    }

                                    else if (filter.equals("rel")){
                                            Collections.sort(saloonDetailsList,SaloonDetails.BY_ALPHABET);
                                        }
                                        else if (filterType.equals("High")){
                                            Collections.sort(saloonDetailsList,SaloonDetails.BY_PRICE_HIGH);
                                        }


                                    Log.e("nnbkvjkvxc","ckjc"+saloonDetailsList);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(
                                            new LinearLayoutManager(SaloonListActivity.this));
                                   adapter=new MySaloonAdapter(
                                           SaloonListActivity.this,
                                           recyclerView, saloonDetailsList
                                           ,saloonAuthIdList
                                           , saloonPhotoUriList,new MySaloonAdapter.onItemClickListener() {
                                       @Override
                                       public void onItemClick(String key, String sname2,
                                                               String address, String location, String mobile, String url) {


                                           if (categoryType.equals(ConfigName.MALE_HAIR_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       MaleHairActivity.class);

                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();
                                               Log.e("Saloon list Actvivty",""+key);

                                               intent.putExtra("key",key);
                                               intent.putExtra("url",url);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if(categoryType.equals(ConfigName.FEMALE_HAIR_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       HairStyleFemaleActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();

                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.MALE_BEAUTY_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       MaleBeautyActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();
                                               Log.e("List Saloon ACTIVITY","mALE bEAUTY iNTENT");

                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.FEMALE_BEAUTY_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                      FemaleBeautyActivity.class);
                                               Log.e("List Saloon ACTIVITY","FEMALE bEAUTY iNTENT");
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();
                                               Log.e("Saloon list Actvivty",""+key);

                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.MALE_SPA_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       MaleSpaActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();

                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.FEMALE_SPA_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       FemaleSpaActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();

                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.FEMALE_NAIL_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       FemaleNailActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();

                                                intent.putExtra("imgUrl",url);
                                               intent.putExtra("key",key);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }
                                           else if (categoryType.equals(ConfigName.MALE_SHAVE_KEY)){
                                               Intent intent=new Intent(SaloonListActivity.this,
                                                       MaleShaveActivity.class);
                                               /**
                                                *           Shared Preferences saloonName
                                                */
                                               SharedPreferences preferences=getApplicationContext()
                                                       .getSharedPreferences("saloonName",0);
                                               SharedPreferences.Editor editor=preferences.edit();
                                               editor.putString("sname",sname2);
                                               editor.commit();

                                               intent.putExtra("key",key);
                                               intent.putExtra("imgUrl",url);
                                               intent.putExtra("sname",sname2);
                                               intent.putExtra("address",address);
                                               intent.putExtra("location",location);
                                               intent.putExtra("mobile",mobile);
                                               startActivity(intent);
                                           }



                                       }
                                   }
                                   );
                                  progressBar.setVisibility(View.GONE);
                                   if (sname==null){
                                       textView.setVisibility(View.VISIBLE);
                                   }
                                   recyclerView.setAdapter(adapter);
                                }

                            }
                        });


                    }
                }
            }
        });
    }


    public void fetchSaloonDetails() {

        documentReference = firestore
                .collection("ESaloon")
                .document("SaloonName");


        documentReference.collection("Authids")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                      skey = documentSnapshot.get("Ids").toString();
                      final SaloonAuthId saloonAuthId=new SaloonAuthId();
                       saloonAuthId.setAuthIdSaloon(skey);
                        saloonAuthIdList.add(saloonAuthId);
                        Log.e("nnbkvjkvxc", "ckjc  " + skey);


                        collectionReference = firestore.collection("ESaloon")
                                .document("owner").collection(skey);
                        collectionReference
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()) {




                                        Log.e("nnbkvjkvxc", "ckjc  " + documentSnapshot1.getData());
                                        final SaloonDetails saloonDetails = new SaloonDetails();

                                        sname = documentSnapshot1.get("sname").toString();
                                        address = documentSnapshot1.get("address").toString();
                                        city = documentSnapshot1.get("city").toString();
                                        String phoneNumber = documentSnapshot1.get("mobileNumber").toString();
                                        rating = "2";
                                        String timing=documentSnapshot1.getString("shopTiming");
                                        String imgUrl=documentSnapshot1.getString("saloonImageUrl");
                                        saloonSelectedKey = documentSnapshot1.getId();
                                        saloonDetails.setMobile_Number(phoneNumber);
                                        saloonDetails.setSaloon_Name(sname);
                                        saloonDetails.setShopTiming(timing);
                                        saloonDetails.setSaloon_Address(address);
                                        saloonDetails.setSaloon_Location(city);
                                        saloonDetails.setSaloon_Rating(rating);
                                        saloonDetails.setSaloon_key(skey);
                                        saloonDetails.setSaloonImageUrl(imgUrl);
                                        saloonDetails.setTotalPrice(Integer.parseInt(documentSnapshot1.get("totalPrice").toString()));

                                        saloonDetailsList.add(saloonDetails);


                                    }
                                    Log.e("nnbkvjkvxc", "ckjc  " + saloonDetailsList);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(
                                            new LinearLayoutManager(SaloonListActivity.this));
                                    adapter = new MySaloonAdapter(
                                            SaloonListActivity.this,
                                            recyclerView, saloonDetailsList,saloonAuthIdList,saloonPhotoUriList
                                            , new MySaloonAdapter.onItemClickListener() {
                                        @Override
                                        public void onItemClick(String key, String sname2, String address, String location, String mobile, String url) {


                                            if (categoryType.equals(ConfigName.MALE_HAIR_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        MaleHairActivity.class);

                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("address", address);
                                                intent.putExtra("location", location);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            } else if (categoryType.equals(ConfigName.FEMALE_HAIR_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        HairStyleFemaleActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("address", address);
                                                intent.putExtra("location", location);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            } else if (categoryType.equals(ConfigName.MALE_BEAUTY_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        MaleBeautyActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();
                                                Log.e("List Saloon ACTIVITY", "mALE bEAUTY iNTENT");

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("address", address);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("location", location);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            } else if (categoryType.equals(ConfigName.FEMALE_BEAUTY_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        FemaleBeautyActivity.class);
                                                Log.e("List Saloon ACTIVITY", "FEMALE bEAUTY iNTENT");
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("address", address);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("location", location);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            } else if (categoryType.equals(ConfigName.MALE_SPA_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        MaleSpaActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("address", address);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("location", location);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            } else if (categoryType.equals(ConfigName.FEMALE_SPA_KEY)) {
                                                Intent intent = new Intent(SaloonListActivity.this,
                                                        FemaleSpaActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences = getApplicationContext()
                                                        .getSharedPreferences("saloonName", 0);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.putString("sname", sname2);
                                                editor.commit();

                                                intent.putExtra("key", key);
                                                intent.putExtra("sname", sname2);
                                                intent.putExtra("address", address);
                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("location", location);
                                                intent.putExtra("mobile", mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.FEMALE_NAIL_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        FemaleNailActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);
                                            }
                                            else if (categoryType.equals(ConfigName.MALE_SHAVE_KEY)){
                                                Intent intent=new Intent(SaloonListActivity.this,
                                                        MaleShaveActivity.class);
                                                /**
                                                 *           Shared Preferences saloonName
                                                 */
                                                SharedPreferences preferences=getApplicationContext()
                                                        .getSharedPreferences("saloonName",0);
                                                SharedPreferences.Editor editor=preferences.edit();
                                                editor.putString("sname",sname2);
                                                editor.commit();

                                                intent.putExtra("imgUrl",url);
                                                intent.putExtra("key",key);
                                                intent.putExtra("sname",sname2);
                                                intent.putExtra("address",address);
                                                intent.putExtra("location",location);
                                                intent.putExtra("mobile",mobile);
                                                startActivity(intent);

                                            }


                                        }
                                    }
                                    );
                                    progressBar.setVisibility(View.GONE);
                                    if (sname == null) {
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        });


                    }
                }
            }
        });
    }
    }
