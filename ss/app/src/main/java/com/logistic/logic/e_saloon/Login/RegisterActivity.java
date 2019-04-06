package com.logistic.logic.e_saloon.Login;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.Model.PersonalDetails;
import com.logistic.logic.e_saloon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    EditText useredit,emailedit,passedit,confirmedit,editTextNumber,editTextAddress;
    Button continueButton;

    CircleImageView imageView;

    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    DatabaseReference databaseReference;
    CollectionReference collectionReference;
    CollectionReference collectionReference2;
    DocumentReference documentReference2;
    StorageReference storageReference;
    StorageReference storageReference2;
    String uid;
    TextView textView;
    String imageUrl;

    @Override
    protected void onStart() {
        FirebaseUser currentUser=mAuth.getCurrentUser();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth=FirebaseAuth.getInstance();
        useredit=findViewById(R.id.editText2);
        imageView=findViewById(R.id.imageView3);
        emailedit=findViewById(R.id.editText4);
        passedit=findViewById(R.id.editText6);
        confirmedit=findViewById(R.id.editText7);
        continueButton=findViewById(R.id.button4);
        textView=findViewById(R.id.alreadyTextView);
        editTextNumber=findViewById(R.id.editTextPhone);
        editTextAddress=findViewById(R.id.editTextAddress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_Photo();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=useredit.getText().toString();
                final String email=emailedit.getText().toString();
                final String password=passedit.getText().toString();
                final String confpass=confirmedit.getText().toString();
                if(username.equals("")&&email.equals("")&&!checkEmail(email)&&password.equals("")&&confpass.equals("")){
                    useredit.setError("enter the username");
                    emailedit.setError("enter the email");
                    passedit.setError("enter the password");
                    confirmedit.setError("enter confirm password");
                }
                else if(username.equals("")){
                    useredit.setError("enter the username");
                }
                else if(email.equals("")&&!checkEmail(email)){
                    emailedit.setError("enter the email");
                }
                else if(password.equals("")){
                    passedit.setError("enter the password");
                }
                else if(confpass.equals("")){
                    confirmedit.setError("enter confirm password");
                }
                else if(!password.equals(confpass)){
                    Toast.makeText(RegisterActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                }
                else if (imageUrl==null){
                    Toast.makeText(RegisterActivity.this, "Please Select Photo from Gallery !", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences preferences=getApplicationContext().getSharedPreferences("userName",0);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("name",useredit.getText().toString());
                    editor.putString("photo",imageUrl);
                    editor.apply();
                    onContinueTapped();
                }
            }
        });
    }
    public void onContinueTapped(){
        String email=emailedit.getText().toString();
        String password=passedit.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            saveData(useredit.getText().toString(),
                                    imageUrl,editTextNumber.getText().toString()
                            ,editTextAddress.getText().toString());


                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "failure", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
    private boolean checkEmail(String email){
        return email.contains("@");
    }
    public void onSignInTapped(View view){
        Intent intent=new Intent(RegisterActivity.this,MobileVerification.class);
        startActivity(intent);
    }
    private void Select_Photo(){

        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,101);
    }

    void saveData(final String name, final String imageUrl, final String phone, final String address){

        Log.e("Caling -------","Saing data to firebase +++++++++++");
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("ESaloon")
                .document("User")
                .collection("ClientLogin");

        PersonalDetails personalDetails=new PersonalDetails(name, phone
                ,address);
        collectionReference
                .document(mAuth.getUid())
                .set(personalDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Log.e("Register","jbvjf");
                            storageReference = FirebaseStorage.getInstance()
                                    .getReference("ESaloon/User/ProfileInfo/"
                                            + mAuth.getUid());
                            storageReference2 = storageReference.child(System.currentTimeMillis()
                                    + "." + getImageExtension(Uri.parse(imageUrl)));
                            storageReference2
                                    .putFile(Uri.parse(imageUrl))
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Log.e("Register","Upload Done !");
                                            storageReference2.getDownloadUrl()
                                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            collectionReference = firestore.collection("ESaloon")
                                                                    .document("User")
                                                                    .collection("ClientLogin");
                                                            collectionReference.document().update("imageUrl",uri)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                        }
                                                                    });

                                                            mAuth.signOut();
                                                            Intent i=
                                                                    new Intent(RegisterActivity.this
                                                                            ,Login_Activity.class);
                                                            startActivity(i);
                                                            finish();
                                                        }
                                                    });
                                        }
                                    });


                        }
                    }
                });




    }

    public  String getImageExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 && data!=null){
            String uri=data.getData().toString();
            imageUrl=uri;
            imageView.setImageURI(Uri.parse(imageUrl));

        }
        else {
            Toast.makeText(this, "Please Select Your Photo !", Toast.LENGTH_SHORT).show();
        }
    }
}
