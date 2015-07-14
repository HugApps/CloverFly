package com.cloverfly.cloverfly.cloverfly;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpPage extends ActionBarActivity {
    EditText email,pass,user,dob;
    TextView submit;
    CheckBox yes,no;
    final int MINLENGTH = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_page);

        email = (EditText)findViewById(R.id.newemail);
        pass = (EditText)findViewById(R.id.newpassword);
        //user = (EditText)findViewById(R.id.newname);
       // dob =(EditText)findViewById(R.id.dob);

        submit =(TextView)findViewById(R.id.register);
        yes = (CheckBox) findViewById(R.id.yesterms);
        no = (CheckBox) findViewById(R.id.noterms);




        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no.setChecked(false);

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yes.setChecked(false);
            }
        });
        // Submit button should send data to server , sends confirmation email to user, checks for any duplicated information

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check fields filed in //
                // checkFields();
                // submit data to server //
                if(checkFields()){
                String message = "Error, try again";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,message,duration);
                    toast.show();}


            }
        });



    }

    private boolean checkFields (){
        String message = "";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if ( email.getText().length() <= 0 || pass.getText().length() <MINLENGTH || user.getText().length() <=0 || dob.getText().length() <=0){
            message="Please fill in all required fields";
            Toast toast = Toast.makeText(context,message,duration);
            toast.show();
            return false;
        }
        if(no.isChecked() ||! yes.isChecked()){
            message =  "Please Accept terms and conditions";
            Toast toast = Toast.makeText(context, message,duration);
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
