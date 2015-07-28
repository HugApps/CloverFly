package com.cloverfly.cloverfly.cloverfly;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpPage extends ActionBarActivity {
    EditText email,pass,user,dob,pass2,fname,lname;
    Switch gender;
    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    TextView submit;
    CheckBox yes,no;
    RelativeLayout Frag;
    Step2 step2 ;
    final int MINLENGTH = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_page);
        step2 = new Step2();
        Frag = (RelativeLayout)findViewById(R.id.stepfragments);
        email = (EditText)findViewById(R.id.newemail);
        pass = (EditText)findViewById(R.id.newpassword);
        pass2 = (EditText) findViewById(R.id.confirmpword);
        fname = (EditText) findViewById(R.id.fname);
        lname =(EditText) findViewById(R.id.lname);
        gender = (Switch) findViewById(R.id.gender);
        dob = (EditText)findViewById(R.id.dob);

        //user = (EditText)findViewById(R.id.newname);
       // dob =(EditText)findViewById(R.id.dob);

        submit =(TextView)findViewById(R.id.register);




      /*  yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setChecked(false);

            }
        }); */

       /* no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setChecked(false);
            }
        });*/
        // Submit button should send data to server , sends confirmation email to user, checks for any duplicated information

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check fields filed in //
                // checkFields();
                // submit data to server //
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if (checkFields()) {
                    // JSON of signup credentials
                   // ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();



                    params.add(new BasicNameValuePair("email", email.getText().toString()));
                    params.add(new BasicNameValuePair("password", pass.getText().toString()));
                    params.add(new BasicNameValuePair("fname", fname.getText().toString()));
                    params.add(new BasicNameValuePair("lname", lname.getText().toString()));
                    params.add(new BasicNameValuePair("dob", dob.getText().toString()));
                    params.add(new BasicNameValuePair("gender", gender.getText().toString()));




                        // Once successfully submitted , move to step 2
                        FragmentManager man = getFragmentManager();
                        Fragment frag =step2;

                        man.beginTransaction().replace(R.id.stepfragments,frag).addToBackStack("step2").commit();

                        return;


                    }
                FragmentManager man = getFragmentManager();
                Fragment frag =step2;

                man.beginTransaction().replace(R.id.stepfragments,frag).addToBackStack("step2").commit();

                }





        });}



    private boolean checkFields (){
        String message = "";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if ( email.getText().length() <= 0 || pass.getText().length() <MINLENGTH || user.getText().length() <=0 || dob.getText().length() <=0 ||fname.getText().length()<=0 ||lname.getText().length()<=0 ){
            message="Please fill in all required fields";
            Toast toast = Toast.makeText(context,message,duration);
            toast.show();
            return false;
        }
        if(pass2.getText() != pass.getText()){
            message="Passwords do not match";
            Toast toast = Toast.makeText(context,message,duration);
            toast.show();
            return false;

        }

        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up_page, menu);
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



    private void checkConnection(){
        ConnectivityManager check = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = check.getActiveNetworkInfo();





        if( check.getActiveNetworkInfo() != null){
            Toast toast = Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext()," Please Retry Connection",Toast.LENGTH_SHORT);
            toast.show();
           Intent intent = new Intent(SignUpPage.this, RetryConnection.class);
           SignUpPage.this.startActivity(intent);
            return;}



    }
}
/*
   NodeConnect nc = new NodeConnect();


                    JSONObject json = nc.getJSON("http://255.255.255.255/login", params);

       if (json != null) {
                        try {
                            // Check if scucess, if so login user//
                            if (json.getBoolean("correct")) {


                                Toast toast = Toast.makeText(context, "Succesfully Logged in ", duration);
                                toast.show();

                                // Launch main Menu profile page//


                            }

                        } catch (JSONException e) {
                            System.out.println("error");
                            Toast toast2 = Toast.makeText(context, "Failed Login", duration);
                        }
                        else {
                        String message = "Error, try again";

                        Toast toast = Toast.makeText(context, message, duration);
                        toast.show();
                    }

 */