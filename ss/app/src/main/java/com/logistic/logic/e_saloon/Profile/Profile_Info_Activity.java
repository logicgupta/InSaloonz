package com.logistic.logic.e_saloon.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logistic.logic.e_saloon.Model.PersonalDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Info_Activity extends Activity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    CircleImageView circleImageView;
    TextView emailEditText;
    TextView nameEditText;
    TextView phoneEditText;
    TextView addressEditText;
    ImageView imageView;
    ImageView updatePhoto;
    Button button;
    EditText updateName,updateNumber,updateAddress;
    Uri uri;
    CollectionReference collectionReference;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    StorageReference storageReference2;
    FirebaseFirestore firestore;
    ProgressBar progressBar;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__info_);



        auth=FirebaseAuth.getInstance();
        emailEditText=findViewById(R.id.user_email);
        nameEditText=findViewById(R.id.user_name);
        phoneEditText=findViewById(R.id.user_mobile);
        addressEditText=findViewById(R.id.user_address);
        circleImageView=findViewById(R.id.setProfilePhoto);
        progressBar=findViewById(R.id.progress_barProfile);
        linearLayout=findViewById(R.id.profile_Layout);

        imageView=findViewById(R.id.editProfile);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             final Dialog dialog = new Dialog(Profile_Info_Activity.this);
                                             setContentView(R.layout.update_profile);

                                             updatePhoto = findViewById(R.id.updatePhoto);
                                             updateName = findViewById(R.id.updateName);
                                             updateNumber = findViewById(R.id.updateNumber);
                                             updateAddress = findViewById(R.id.updateAddress);
                                             button = findViewById(R.id.updateButton);

                                             updatePhoto.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     Intent intent = new Intent();
                                                     intent.setAction(Intent.ACTION_GET_CONTENT);
                                                     intent.setType("image/*");
                                                     startActivityForResult(intent, 501);
                                                 }
                                             });
                                             button.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (updateName.getText().toString().equalsIgnoreCase("")) {

                                                         Toast.makeText(Profile_Info_Activity.this, "Please Enter Name !", Toast.LENGTH_SHORT).show();

                                                     } else if (updateNumber.getText().toString().equalsIgnoreCase("")) {

                                                         Toast.makeText(Profile_Info_Activity.this, "Please Enter Mobile Number !", Toast.LENGTH_SHORT).show();

                                                     } else if (updateAddress.getText().equals("")) {
                                                         Toast.makeText(Profile_Info_Activity.this, "Please Enter Address !", Toast.LENGTH_SHORT).show();


                                                     } else if (uri == null) {

                                                         Toast.makeText(Profile_Info_Activity.this, "Please Select Photo", Toast.LENGTH_SHORT).show();
                                                     } else {
                                                         final ProgressDialog progressDialog=new ProgressDialog(Profile_Info_Activity.this);
                                                         progressDialog.setMessage("Updating ...");
                                                         progressDialog.setCancelable(false);
                                                         progressDialog.show();
                                                         Toast.makeText(Profile_Info_Activity.this, "Updating", Toast.LENGTH_SHORT).show();
                                                         Map<String, String> map = new HashMap<>();
                                                         map.put("name", updateName.getText().toString());
                                                         map.put("number", updateNumber.getText().toString());
                                                         map.put("address", updateAddress.getText().toString());
                                                         map.put("imageUrl", uri.toString());
                                                         firebaseAuth=FirebaseAuth.getInstance();
                                                         firestore=FirebaseFirestore.getInstance();
                                                         storageReference = FirebaseStorage.getInstance()
                                                                 .getReference("ESaloon/User/ProfileInfo/"
                                                                         + firebaseAuth.getUid());
                                                         storageReference2 = storageReference.child(System.currentTimeMillis()
                                                                 + "." + getImageExtension(uri));
                                                         storageReference2
                                                                 .putFile(uri)
                                                                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                           @Override
                                                                                           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                               storageReference2.getDownloadUrl()
                                                                                                       .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                           @Override
                                                                                                           public void onSuccess(Uri uri) {
                                                                                                               collectionReference = firestore.collection("ESaloon")
                                                                                                                       .document("User")
                                                                                                                       .collection("ClientLogin");
                                                                                                               PersonalDetails personalDetails=new PersonalDetails(updateName.getText().toString(),
                                                                                                                       updateAddress.getText().toString()
                                                                                                                       ,updateNumber.getText().toString(),uri.toString());
                                                                                                               collectionReference
                                                                                                                       .document(firebaseAuth.getUid())
                                                                                                                       .set(personalDetails)
                                                                                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                           @Override
                                                                                                                           public void onComplete(@NonNull Task<Void> task) {

                                                                                                                               if (task.isSuccessful()){
                                                                                                                                   Toast.makeText(Profile_Info_Activity.this, "Success !", Toast.LENGTH_SHORT).show();
                                                                                                                                   dialog.dismiss();
                                                                                                                                   progressDialog.dismiss();
                                                                                                                                   finish();
                                                                                                                               }
                                                                                                                               dialog.dismiss();
                                                                                                                               progressDialog.dismiss();
                                                                                                                           }
                                                                                                                       });


                                                                                                           }
                                                                                                       });
                                                                                           }
                                                                                       });


                                                     }


                                                     dialog.show();
                                                 }

                                             });
                                         }
                                     });
        SharedPreferences preferences=getApplicationContext().getSharedPreferences("userName",0);
        String imageurl=preferences.getString("photo",null);
        String name=preferences.getString("name",null);

        if (getUserPhotoUrl()!=null){
            Glide.with(getApplicationContext()).load(getUserPhotoUrl()).into(circleImageView);
            SharedPreferences sharedPreferences=
                    getApplicationContext().getSharedPreferences("personalDetails",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("imageUrl",getUserPhotoUrl().toString());
            editor.putString("name",getUserName());
            editor.commit();
            progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }else {
                //circleImageView.setImageURI(Uri.parse(imageurl));
            collectionReference = firestore.collection("ESaloon")
                    .document("User")
                    .collection("ClientLogin");
            collectionReference
                    .document(firebaseAuth.getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String name=documentSnapshot.get("name").toString();
                    String number=documentSnapshot.get("phoneNumber").toString();
                    String address=documentSnapshot.get("address").toString();
                   // String imageUrl=documentSnapshot.get("imageUrl").toString();
                    SharedPreferences sharedPreferences=
                            getApplicationContext().getSharedPreferences("personalDetails",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                   // editor.putString("imageUrl",imageUrl);
                    editor.putString("name",name);
                    editor.commit();
                   // Glide.with(getApplicationContext()).load(imageUrl).into(circleImageView);
                    nameEditText.setText(name);
                    phoneEditText.setText(number);
                    addressEditText.setText(address);
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                }
            });
        }
        if (getUserName()!=null){
            nameEditText.setText(getUserName());
        }
        else {

        }
        emailEditText.setText(getUserEmail());

        collectionReference = firestore.collection("ESaloon")
                .document("User")
                .collection("ClientLogin");
        collectionReference
                .document(firebaseAuth.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                String name=documentSnapshot.getString("name");
                String number=documentSnapshot.getString("phoneNumber");
                String address=documentSnapshot.getString("address");
                // String imageUrl=documentSnapshot.get("imageUrl").toString();
                SharedPreferences sharedPreferences=
                        getApplicationContext().getSharedPreferences("personalDetails",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                // editor.putString("imageUrl",imageUrl);
                editor.putString("name",name);
                editor.commit();
                // Glide.with(getApplicationContext()).load(imageUrl).into(circleImageView);
                nameEditText.setText(name);
                phoneEditText.setText(number);
                addressEditText.setText(address);

            }
        });

    }


    public Uri getUserPhotoUrl(){

        FirebaseUser user=auth.getCurrentUser();
        Uri uri=user.getPhotoUrl();
        return uri;
    }
    public String  getUserName(){
        FirebaseUser user=auth.getCurrentUser();
        return user.getDisplayName();
    }
    public String getUserEmail(){
        FirebaseUser user=auth.getCurrentUser();
        return user.getEmail();
    }
    public String getUserPhone(){
        FirebaseUser user=auth.getCurrentUser();
        return user.getPhoneNumber();
    }

    public  String getImageExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==501 && data!=null){
           uri=data.getData();
            updatePhoto.setImageURI(uri);

        }
        else {
            Toast.makeText(this, "Please Select Your Photo !", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
