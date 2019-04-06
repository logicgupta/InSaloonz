package com.logistic.logic.e_saloon.Payment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.BookingDetails.ConfirmBooking;
import com.logistic.logic.e_saloon.Cart.CartSaloon;
import com.logistic.logic.e_saloon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentOptions extends AppCompatActivity implements PaymentResultListener {

    RadioButton patymMode,bhimMode,CreditMode,NetBankingMode;
    TextView textView;
    Button button;
    Intent intent;
    String calender_Date;
    String calenderMonth;
    String calenderYear;
    String calenderOriginalDate;
    String calenderTime;
    String calenderDayTime;
    String totalPrice;
    String saloonName;
    FirebaseAuth firebaseAuth;
    TextView totalTextView;
    String emailId;
    String name;
    public static String TAG=PaymentOptions.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        textView=findViewById(R.id.paymentDetails);
        totalTextView=findViewById(R.id.totalPriceTextView);
        intent=getIntent();
        firebaseAuth=FirebaseAuth.getInstance();

        /*
                    Details ========
         */
        emailId=firebaseAuth.getCurrentUser().getEmail();
        if (firebaseAuth.getCurrentUser().getDisplayName().equals("")){
            SharedPreferences sharedPreferences =
                    getApplicationContext().getSharedPreferences("personalDetails", 0);
            name=sharedPreferences.getString("name",null);

        }
        else {
            name=firebaseAuth.getCurrentUser().getDisplayName();
        }


        calender_Date=intent.getStringExtra("Date");
        calenderMonth=intent.getStringExtra("Month");
        calenderYear=intent.getStringExtra("YEAR");
        calenderOriginalDate=intent.getStringExtra("DateFormat");
        calenderTime=intent.getStringExtra("time");
        calenderDayTime=intent.getStringExtra("DayTime");
        totalPrice=intent.getStringExtra("totalPrice");
        saloonName=intent.getStringExtra("saloonName");
        Log.e("PaymentOptions","totalPrice"+totalPrice);
        totalTextView.setText("Pay :"+"₹"+totalPrice);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentOptions.this,CartSaloon.class));
            }
        });
        startPaymentProcess();
        patymMode=findViewById(R.id.patymMode);
        bhimMode=findViewById(R.id.BhimMode);
        CreditMode=findViewById(R.id.CreditMode);
        NetBankingMode=findViewById(R.id.NetBankingMode);

        button=findViewById(R.id.payMode);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (patymMode.isChecked()){

                }
                else if (bhimMode.isChecked()){

                }
                else if (CreditMode.isChecked()){

                    startPaymentProcess();
                }
                else if (NetBankingMode.isChecked()){

                }
                else {
                    Toast.makeText(PaymentOptions.this, "Select Payment Mode !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startPaymentProcess(){

        Checkout checkout=new Checkout();


        checkout.setImage(R.drawable.logo_saloon);

        final Activity activity=this;


        try {
            JSONObject options=new JSONObject();

            options.put("name",name);

            /**
             *      Description can be anything
             */
            options.put("description","Order #12345");

            options.put("currency","INR");



            /**
             *  Amount is allways in paise
             *  500 = ₹ 5.00
             */
            options.put("amount",Integer.parseInt(totalPrice)*100);

            checkout.open(activity,options);
        }
        catch (Exception e){
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.e("TAg","Paymnet Response"+s);

        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(PaymentOptions.this,ConfirmBooking.class);
        intent.putExtra("payId",s);
        intent.putExtra("Date",calender_Date);
        intent.putExtra("Month",calenderMonth);
        intent.putExtra("YEAR",calenderYear);
        intent.putExtra("DateFormat",calenderOriginalDate);
        intent.putExtra("time",calenderTime);
        intent.putExtra("DayTime",calenderDayTime);
        intent.putExtra("totalPrice",totalPrice);
        intent.putExtra("saloonName",saloonName);
        intent.putExtra("EmailId",emailId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed !", Toast.LENGTH_SHORT).show();
        Log.e("Payment failed "," "+"asbdshb");
        /*
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(PaymentOptions.this,ConfirmBooking.class);
        intent.putExtra("payId",s);
        intent.putExtra("Date",calender_Date);
        intent.putExtra("Month",calenderMonth);
        intent.putExtra("YEAR",calenderYear);
        intent.putExtra("DateFormat",calenderOriginalDate);
        intent.putExtra("time",calenderTime);
        intent.putExtra("DayTime",calenderDayTime);
        intent.putExtra("totalPrice",totalPrice);
        intent.putExtra("saloonName",saloonName);
        intent.putExtra("emailId",emailId);
        startActivity(intent);
        finish();*/


    }

/*
    private void callInstamojoPay(String email, String phone_1, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone_1", phone_1);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);
    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                String strarry []=response.split(":");
                String orderId=strarry[1].substring(strarry[1].indexOf("="+1));
                String payId=strarry[3].substring(strarry[3].indexOf("="+1));
                Intent intent=new Intent(PaymentOptions.this,ConfirmBooking.class);
                intent.putExtra("Date",calender_Date);
                intent.putExtra("Month",calenderMonth);
                intent.putExtra("YEAR",calenderYear);
                intent.putExtra("DateFormat",calenderOriginalDate);
                intent.putExtra("time",calenderTime);
                intent.putExtra("DayTime",calenderDayTime);
                intent.putExtra("totalPrice",totalPrice);
            intent.putExtra("saloonName",saloonName);
                intent.putExtra("orderId",orderId);
                intent.putExtra("payId",payId);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
                finish();
            }
        };
    }*/
}
