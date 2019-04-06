package com.logistic.logic.e_saloon.Main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logistic.logic.e_saloon.Appointments.Appointments_Activity;
import com.logistic.logic.e_saloon.Cart.CartSaloon;
import com.logistic.logic.e_saloon.Configure.ConfigName;
import com.logistic.logic.e_saloon.Coupons.Coupon_Fragment;
import com.logistic.logic.e_saloon.FAQs.FAQusetion;
import com.logistic.logic.e_saloon.Favourites.Favourites_Activity;
import com.logistic.logic.e_saloon.Login.Login_Activity;
import com.logistic.logic.e_saloon.Notofication.Notification_Fragment;
import com.logistic.logic.e_saloon.Profile.Profile_Info_Activity;
import com.logistic.logic.e_saloon.R;
import com.logistic.logic.e_saloon.SaloonList.SaloonListActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,CartSaloon.OnFragmentInteractionListener
        ,Coupon_Fragment.OnFragmentInteractionListener
        ,Notification_Fragment.OnFragmentInteractionListener {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    private FirebaseAuth mAuth;

    private static final int REQUEST_CHECK_SETTINGS=1;
    CircleImageView circleImageView;
    TextView textView;

    FirebaseAuth firebaseAuth;
     CollectionReference collectionReference;
     FirebaseFirestore firestore;
     TextView optionMale,optionFemale;
     View  layout1,layout2;
    CardView female_hair_cardView;
    CardView female_skinCare_cardView;
    CardView female_spa_cardView;
    CardView female_nail_cardView;
    CardView male_hair_cardView;
    CardView male_thread_cardView;
    CardView male_spa_cardView;
    CardView male_shave_cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayLocationSettingsRequest(MainActivity.this);
        mAuth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);


        // Intialize the Bottom NavigationBar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        layout1=findViewById(R.id.maleLayout);
        layout2=findViewById(R.id.femaleLayout);
        optionMale=findViewById(R.id.maleTextViewSelect);
        optionFemale=findViewById(R.id.femaleTextViewSelect);
        maleInit();

        optionFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                Animation animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_left);
                layout2.setAnimation(animation);
                femaleInit();


            }
        });

        optionMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_right);
                layout1.setAnimation(animation);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);

                maleInit();


            }
        });




        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("userName",0);
        String imageurl=preferences.getString("photo",null);
        String name=preferences.getString("name",null);

        circleImageView=headerView.findViewById(R.id.imageView);
        textView=headerView.findViewById(R.id.user_name);
        if (getUserPhotoUrl()!=null){
           Glide.with(getApplicationContext()).load(getUserPhotoUrl()).into(circleImageView);
        }else {
        }
        if (getUserName()!=null){
            textView.setText(getUserName());
        }
        else {
         //   getPesonalDetails();


        }



    }

    public Uri getUserPhotoUrl(){

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        Uri uri=user.getPhotoUrl();
        return uri;
    }
    public String  getUserName(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        return user.getDisplayName();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                    return  true;
                case R.id.nav_categories:
                    fragment=new Coupon_Fragment();
                    loadFragment(fragment);
                    return  true;
                case R.id.nav_coupons:
                    startActivity(new Intent(MainActivity.this,Favourites_Activity.class));
                    return true;
                case R.id.navigation_cart:
                        fragment=new CartSaloon();
                        loadFragment(fragment);
                    return true;
            }


            return false;
        }
    };
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void getPesonalDetails(){
        firestore=FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("ESaloon")
                .document("User")
                .collection("ClientLogin");
        collectionReference
                .document(firebaseAuth.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.get("name").toString();
                String number = documentSnapshot.get("phoneNumber").toString();
                String address = documentSnapshot.get("address").toString();
                String imageUrl = documentSnapshot.get("imageUrl").toString();
                SharedPreferences sharedPreferences =
                        getApplicationContext().getSharedPreferences("personalDetails", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imageUrl", imageUrl);
                editor.putString("name", name);
                editor.putString("number",number);
                editor.commit();
                textView.setText(name);
            }
        });
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {

        if (exit) {
            //  super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 3 * 1000);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_profile) {
           startActivity(new Intent(MainActivity.this,Profile_Info_Activity.class));

        } else if (id == R.id.nav_appointments) {
           startActivity(new Intent(MainActivity.this,Appointments_Activity.class));

        } else if (id == R.id.nav_offers) {
           Fragment fragment;
           fragment=new Coupon_Fragment();
           loadFragment(fragment);


        }
        else if (id == R.id.nav_favorites) {
           startActivity(new Intent(MainActivity.this,Favourites_Activity.class));

        }
        /*else if (id == R.id.nav_share) {
           startActivity(new Intent(MainActivity.this,Refer_And_Earn.class));


       } */
       else if (id == R.id.nav_contact) {
            startActivity(new Intent(MainActivity.this,ContactUs.class));

        }
        else if (id == R.id.nav_faq) {
            startActivity(new Intent(MainActivity.this,FAQusetion.class));

        }
        else if (id == R.id.nav_logout) {
           SharedPreferences preferences=getApplicationContext().getSharedPreferences("saloonName",0);
           SharedPreferences.Editor editor=preferences.edit();
           editor.clear();
           editor.commit();
           SharedPreferences sharedPreferences=
                   getApplicationContext().getSharedPreferences("personalDetails",0);
           SharedPreferences.Editor editor1=sharedPreferences.edit();
           editor1.clear();
           editor1.commit();
            Toast.makeText(this, "!!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,Login_Activity.class));
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices
                .SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    void femaleInit(){
        female_hair_cardView=findViewById(R.id.hairStyleFemale);
        female_skinCare_cardView=findViewById(R.id.beautyStyleFemale);
        female_spa_cardView=findViewById(R.id.spaStyleFemale);
        female_nail_cardView=findViewById(R.id.nailStyleFemale);
        female_hair_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),HairStyleFemaleActivity.class));
                Intent intent=new Intent(MainActivity.this, SaloonListActivity.class);
                intent.putExtra("category", ConfigName.FEMALE_HAIR_KEY);
                startActivity(intent);
            }
        });


        female_skinCare_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),FemaleBeautyActivity.class));
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.FEMALE_BEAUTY_KEY);
                startActivity(intent);
            }
        });

        female_spa_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),FemaleSpaActivity.class));
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.FEMALE_SPA_KEY);
                startActivity(intent);
            }
        });
        female_nail_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.FEMALE_NAIL_KEY);
                startActivity(intent);
            }
        });


    }
    void maleInit(){
        male_hair_cardView=findViewById(R.id.HairStyleMale);
        male_thread_cardView=findViewById(R.id.BeautyStyleMale);
        male_spa_cardView=findViewById(R.id.SpaStyleMale);
        male_shave_cardView=findViewById(R.id.cardView2);
        male_hair_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),MaleHairActivity.class));
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.MALE_HAIR_KEY);
                startActivity(intent);
            }
        });


        male_thread_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.MALE_BEAUTY_KEY);
                startActivity(intent);
            }
        });


        male_spa_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(getActivity(),MaleSpaActivity.class));
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.MALE_SPA_KEY);
                startActivity(intent);
            }
        });

        male_shave_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SaloonListActivity.class);
                intent.putExtra("category",ConfigName.MALE_SHAVE_KEY);
                startActivity(intent);

            }
        });

    }
}
