package com.logistic.logic.e_saloon.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.logistic.logic.e_saloon.Main.MainActivity;
import com.logistic.logic.e_saloon.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class Login_Activity extends AppCompatActivity {

    private static final String TAG = Login_Activity.class.getSimpleName();
    private static final int RC_SIGN_IN =1;
    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    LoginButton loginButton;
    GoogleApiClient mGoogleSignInClient;
    SignInButton signInButton;
    EditText emailEditText;
    EditText passwordEditText;
    String email;
    String password;
    Button login_btn;
    TextView textView,textView2;
    Button mFacebookButton;
    Button mGoogleButton;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        textView=findViewById(R.id.forgotTextView);
        mFacebookButton=findViewById(R.id.fblogin_button);
        mGoogleButton=findViewById(R.id.googlelogin_button);
        textView2=findViewById(R.id.signUpTextView);
        // Register New User ....
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this,RegisterActivity.class));
            }
        });

        // Forgot the Password ....
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this,ForgotPassword.class));
            }
        });

        login_btn=findViewById(R.id.login_button);

        // Login with Email and Password .....
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=emailEditText.getText().toString();
                password=passwordEditText.getText().toString();
                if (email.equals("") ){
                    emailEditText.setError("Enter The Email !");
                }
                else if (password.equals("")){
                    passwordEditText.setError("Enter the Password !");
                }
                else {
                        loginWithEmail();
                }
            }
        });


    // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    updateUI(mAuth.getCurrentUser());
                }
            }
        };

        // Google SigIN .....
      mGoogleButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              signIn();
          }
      });

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("223613673427-god2eqqfm7hvrclfk3641gf3d4k1gj73.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient =new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Login_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        mCallbackManager = CallbackManager.Factory.create();
        // Facebook SigIn .....
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this,
                        Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager
                        , new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError(FacebookException error) {

                            }
                        });
            }
        });

    }

    public void loginWithEmail(){
        email=emailEditText.getText().toString();
        password=passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user=mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            Toast.makeText(Login_Activity.this, "Try Again !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account=result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                Toast.makeText(
                        this, "Auth Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login_Activity.this, " Facebook Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void signIn() {
        Intent signInIntent=Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            startActivity(new Intent(Login_Activity.this,MainActivity.class));
            finish();
        }
        else {

        }

    }
    @Override
    protected void onResume() {
        mAuth.addAuthStateListener(authStateListener);
        super.onResume();

    }

    @Override
    protected void onPause() {
        mAuth.removeAuthStateListener(authStateListener);
        super.onPause();


    }
}
