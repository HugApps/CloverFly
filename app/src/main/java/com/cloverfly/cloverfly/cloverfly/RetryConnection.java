package com.cloverfly.cloverfly.cloverfly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RetryConnection extends Activity {

    Button Retry,Exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry_connection);
        Retry = (Button)findViewById(R.id.Retrybutton);
        Exit = (Button)findViewById(R.id.Exitbutton);

        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkConnection();

            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_retry_connection, menu);
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




        // This should bring user back to previous screen
        if( check.getActiveNetworkInfo() != null){
            Toast toast = Toast.makeText(getApplicationContext(),"Re connected",Toast.LENGTH_SHORT);
            toast.show();
            this.finish();
            return;
        }
        else{

            Toast toast = Toast.makeText(getApplicationContext(),"Failed,try again",Toast.LENGTH_SHORT);
            toast.show();
        }



    }
}
