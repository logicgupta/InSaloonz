package com.logistic.logic.e_saloon.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.Main.MainActivity;
import com.logistic.logic.e_saloon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.Random;

public class MobileVerification extends AppCompatActivity implements VerificationListener {

    private static final String TAG = MobileVerification.class.getSimpleName();
    EditText phoneNumberEditText;
    Button submit;
    Spinner spinner;
    String phoneNumber;
    String mVerificationId;
    String code;
    private FirebaseAuth mAuth;
    EditText verifyPhoneCode;
    Button otpVerifyButton;
    EditText editText;

    Verification mVerification;
    ProgressDialog progressDialog;
    EditText editTextotp;
     ProgressDialog otpprogressDialog;
     TextView textViewtimer;
    String secondString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        String[] a = {"Gender", "Male", "Female"};
        mAuth = FirebaseAuth.getInstance();
        phoneNumberEditText = findViewById(R.id.mobileEditText);
        spinner = findViewById(R.id.genderSpinner);
        submit = findViewById(R.id.submitButton);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MobileVerification.this, android.R.layout.simple_list_item_1, a);
        spinner.setAdapter(arrayAdapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumberEditText.getText().toString().equals("")) {
                    phoneNumberEditText.setError("Enter Mobile Number !");

                } else if (spinner.getSelectedItem().equals("Gender")) {
                    Toast.makeText(MobileVerification.this, "Please Select Gender !", Toast.LENGTH_SHORT).show();
                } else {
                    phoneNumber = phoneNumberEditText.getText().toString();
                    otpgenerate(phoneNumber);
                    mVerification.initiate();
                    mVerification= SendOtpVerification.createSmsVerification(
                            SendOtpVerification.config("+91"+"8963970412")
                                    .context(MobileVerification.this)
                                    .autoVerification(true)
                                    .build()
                            ,MobileVerification.this);

                }
            }
        });
    }

    public void otpgenerate(String number){
        Random random=new Random();
        int n= random.nextInt(5982)+1;
        String otp_code=String.valueOf(n);
        mVerification= SendOtpVerification.createSmsVerification(
                SendOtpVerification.config("+91"+"8963970412")
                .context(MobileVerification.this)
                .autoVerification(true)
                .build()
                ,MobileVerification.this);
    }


    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" +"bvhjvjhhjvhj"+ response);
        //OTP successfully resent/sent.
        Dialog dialog=new Dialog(MobileVerification.this);
//        CountdownView countDownTimer=findViewById(R.id.countdown);
//        countDownTimer.start(10000);
        setContentView(R.layout.verify_otp);
        editTextotp=findViewById(R.id.verify_otp);
        textViewtimer=findViewById(R.id.textViewTimer1);
        CountDownTimer countDownTimer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                UpdateTimer((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        Button button=findViewById(R.id.validate_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otpprogressDialog=new ProgressDialog(MobileVerification.this);
                otpprogressDialog.setMessage(" Verifying OTP ...");
                otpprogressDialog.setCancelable(false);
                otpprogressDialog.show();
                String otp_code=editTextotp.getText().toString();
                if(otp_code.equals("")){
                    otpprogressDialog.dismiss();

                    Toast.makeText(MobileVerification.this, "Please Enter THe OTP !", Toast.LENGTH_SHORT).show();
                }
                else {
                    mVerification.verify(otp_code);

                }
            }
        });
        dialog.show();

    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Toast.makeText(this, "Please Try Again!", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Verification Inititated  Failed");

    }

    @Override
    public void onVerified(String response) {
        otpprogressDialog.dismiss();
        //progressDialog.dismiss();
        Log.d(TAG,"Verified Response"+response);
        startActivity(new Intent(MobileVerification.this,MainActivity.class));



    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        otpprogressDialog.dismiss();
        //progressDialog.dismiss();
        Toast.makeText(this, " Invalid OTP !", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Verification  Failed");

    }
    public void UpdateTimer(int leftSeconds){
        int minutes=leftSeconds/60;
        int seconds=leftSeconds-(minutes*60);

        if (seconds<=9){
           secondString="0"+seconds;
        }
        textViewtimer.setText(Integer.toString(minutes)+":"+secondString);
    }
}
