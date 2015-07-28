package com.cloverfly.cloverfly.cloverfly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class LandingPage extends ActionBarActivity {

    String uid ;
    String name;
    String fbmail ;

    // Shared Prefrence of the entire App
    private SharedPreferences autologin;
    TextView SignupButton,LoginButton,Recovery;
    EditText email,password;
    final int MINLENGTH = 10;
    com.facebook.login.widget.LoginButton fblogin;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        autologin = getSharedPreferences("autologin",Context.MODE_PRIVATE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_landing_page);

        fblogin=(com.facebook.login.widget.LoginButton)findViewById(R.id.fblogin);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        SignupButton = (TextView)findViewById(R.id.signup);
        LoginButton = (TextView) findViewById(R.id.login);
        email = (EditText)findViewById(R.id.emailfield);
        password = (EditText)findViewById(R.id.passwordfield);
        Recovery = (TextView) findViewById(R.id.PassRecovery);
        this.checkAuto();
        // Intializes Facebook SDK

        fblogin.setReadPermissions("public_profile");
        fblogin.setReadPermissions("email");
        //fblogin.setReadPermissions("user_likes");
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken user =  loginResult.getAccessToken();
                Profile.getCurrentProfile();


                Log.e("No such an algorithm", "login worked");
                GraphRequest request = GraphRequest.newMeRequest(
                        user,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                               try {
                                  uid = object.getString("id");
                                   name= object.getString("name");
                                  fbmail = object.getString("email");
                                   Log.e("e1",object.getString("id"));
                                   Log.e("e1",object.getString("name"));
                                   Log.e("e1",object.getString("email"));
                                   // Set auto login to true
                                   SharedPreferences.Editor editor = autologin.edit();
                                   editor.putBoolean("correct",false);

                                   editor.commit();
                                   Context context = getApplicationContext();
                                   int duration = Toast.LENGTH_SHORT;
                                   Toast toast = Toast.makeText(context,"Succesfully Logged in ",duration);
                                   toast.show();

                               }catch (JSONException e){
                                   System.out.println("error");
                                   Log.e("Error","error");
                               }

                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();
                Intent intent = new Intent(LandingPage.this, SignUpPage.class);
                LandingPage.this.startActivity(intent);


            }

            @Override
            public void onCancel() {
                Log.e("No such an algorithm", "login worked");
            }

            @Override
            public void onError(FacebookException e) {

            }
        });
       // FacebookSdk.sdkInitialize(getApplicationContext());


        // Sign up button will lead to sign up page after checking fields
        SignupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                    Intent intent = new Intent(LandingPage.this, SignUpPage.class);
                    LandingPage.this.startActivity(intent);






            }
        });

        // Handle password recovery
        Recovery.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // HANDLE FRAGMENT FOR PASSWORD RECOVERY AND api CALL.
                Log.e("CLICKABLE", getApplicationContext().getPackageName());

            }
                //Eventually handle back end authentication and transits to login page

        }) ;


       LoginButton.setOnClickListener( new View.OnClickListener(){
           @Override
            public void onClick(View view){
               //Eventually handle back end authentication and transits to login page

               if(checkFields() && checkConnection()){

                   //Handle API CALL


                   ArrayList<NameValuePair>  params = new ArrayList<NameValuePair>();
                   String Pass = password.getText().toString();
                   String user = email.getText().toString();
                   params.add(new BasicNameValuePair("email",user));
                   params.add(new BasicNameValuePair("password",Pass));
                   NodeConnect nc = new NodeConnect();
                   //mongodb://<app>:<AndroidiOS>@ds047592.mongolab.com:47592/umeetgo
                   JSONObject json = nc.getJSON("http://255.255.255.255/login",params);
                   Context context = getApplicationContext();
                   int duration = Toast.LENGTH_SHORT;
                   Intent intent = new Intent(LandingPage.this, MainMenu.class);
                   LandingPage.this.startActivity(intent); // Launch main Menu profile page//
                   if(json != null){
                       try {
                           // Check if scucess, if so login user//
                           if (json.getBoolean("correct")) {


                               SharedPreferences.Editor editor = autologin.edit();
                               editor.putBoolean("correct",false);

                               editor.commit();

                               Toast toast = Toast.makeText(context,"Succesfully Logged in ",duration);
                               toast.show();



                           }
                               Toast toast2 = Toast.makeText(context,"Failed Login",duration);

                       }catch (JSONException e){
                           System.out.println("error");
                           Toast toast2 = Toast.makeText(context,"Failed Login",duration);
                       }

                   }
                   // For now demo auto login

                   /*SharedPreferences.Editor editor = autologin.edit();
                   editor.putBoolean("correct",true);

                   editor.commit();

                   Toast toast = Toast.makeText(context,"Succesfully Logged in ",duration);
                   toast.show();*/
                   SharedPreferences.Editor editor = autologin.edit();
                   editor.putBoolean("correct",true);

                   editor.commit();

                   Toast toast = Toast.makeText(context,"Succesfully Logged in ",duration);
                   toast.show();

               }
       }}) ;
    }

    // Some how handles the login data ???
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void checkAuto(){

       // LoginButton.callOnClick();

        // complete auto login//
        Log.e("check","checking autologin");
       // Boolean loggedin = getSharedPreferences("autologin",MODE_PRIVATE).getBoolean("correct",false);
        if(getSharedPreferences("autologin",MODE_PRIVATE).getBoolean("correct",false)){

            Intent intent = new Intent(LandingPage.this, SignUpPage.class);
            LandingPage.this.startActivity(intent);
            SharedPreferences.Editor editor = autologin.edit();
            editor.clear();
            editor.commit();
        }
    }
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));


                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
    private boolean checkFields (){
      String message;
       printKeyHash(this);
      Context context = getApplicationContext();
      int duration = Toast.LENGTH_SHORT;
      if(email.getText().length()<=0 || password.getText().length() <MINLENGTH){
          //ask use to make sure fields are filled in
          message = "fill in all required fields, and try again";

          Toast toast = Toast.makeText(context,message,duration);
          toast.show();

          return false;
      }
      String emailAddress = email.getText().toString();

      if(!emailAddress.contains("@") || !emailAddress.contains(".") || emailAddress.length()<=4){
          // pop up warning about invalid email
          message = "not a valid email address";
          Toast toast = Toast.makeText(context,message,duration);
          toast.show();
          return false;
      }


    return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
        return true;
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
    @Override
    protected void onResume() {

        super.onResume();
        checkConnection();

        // Logs 'install' and 'app activate' App Events.
      //  AppEventsLogger.activateApp(this);
    }
    private boolean checkConnection(){
        ConnectivityManager check = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
       // NetworkInfo networkInfo = check.getActiveNetworkInfo();





        if( check.getActiveNetworkInfo() != null){

            return true;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext()," Please Retry Connection",Toast.LENGTH_SHORT);
            toast.show();
            // start new activity that checks for internet connection
            Intent intent = new Intent(LandingPage.this, RetryConnection.class);
            LandingPage.this.startActivity(intent);
            return false;}



    }






}
