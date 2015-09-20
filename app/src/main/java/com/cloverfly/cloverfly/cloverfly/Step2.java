package com.cloverfly.cloverfly.cloverfly;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Step2 extends Fragment  {

    TextView Occupation,Languages,Location,button;
    JSONObject json ;

    EditText Phone;

    public Step2() {
        // Required empty public constructor
    }
    //phonenum - > phone number (10 digit number)
    //occupation -> String
    //languages  -> Array
    // location -> lat- long ( coordinates or landmark of nearby city //



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.step2, container, false);
       Occupation = (TextView) v.findViewById(R.id.OccupationPop);
       Languages = (TextView) v.findViewById(R.id.LanguagePop);
       Location = (TextView) v.findViewById(R.id.LocationPop);
       button =(TextView) v.findViewById(R.id.Step3button);
       Phone = (EditText) v.findViewById(R.id.phonel);

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create string array using predefind location, pass to fragment
                String[]list= getResources().getStringArray(R.array.Location);
                Bundle bundle = new Bundle();
                bundle.putStringArray("location",list);
                bundle.putString("type","location");
                // Load popup fragment that displays list
                Fragment Locationpop = new ListPopup();
                Locationpop.setArguments(bundle);
                FragmentManager man = getFragmentManager();


                man.beginTransaction().replace(R.id.popup,Locationpop).addToBackStack("popup").commit();


            }
        });

       Occupation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String[] list = getResources().getStringArray(R.array.occupation_list);
               Bundle bundle = new Bundle();
               bundle.putStringArray("occupation",list);
               bundle.putString("type","occupation");
               // Load popup fragment that displays list
               Fragment Locationpop = new ListPopup();
               Locationpop.setArguments(bundle);
               FragmentManager man = getFragmentManager();


               man.beginTransaction().replace(R.id.popup,Locationpop).addToBackStack("popup").commit();


           }
       });

       Languages.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String[] list = getResources().getStringArray(R.array.Languages);
               Bundle bundle = new Bundle();
               bundle.putStringArray("language",list);
               bundle.putString("type","language");

               // Load popup fragment that displays list
               Fragment Locationpop = new ListPopup();
               Locationpop.setArguments(bundle);
               FragmentManager man = getFragmentManager();


               man.beginTransaction().replace(R.id.popup,Locationpop).addToBackStack("popup").commit();


           }
       });

        // Button click submits list of fields as json and loads up fragment 3
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPhone()) {
                    System.out.println("Valid Phone number");
                } else {
                    System.out.println("Invalid phone number");
                }

                if (CheckFields()) {
                    System.out.println("ValidFields");
                } else {
                    System.out.println("Invalid phone number");
                }

                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                String phone = Phone.getText().toString();
                String occupation = Occupation.getText().toString();
                String location = Location.getText().toString();

                params.add(new BasicNameValuePair("phonenum",phone));
                params.add(new BasicNameValuePair("occupation",occupation));
                params.add(new BasicNameValuePair("location",location));
               // non- array things
                NodeConnect connection = new NodeConnect();
                JSONObject json2 = connection.getJSON("http://umeetgo.com/step2",params);
                connection.PassJson("http://umeetgo.com/step2",json);
                // Handle language array






              //  System.out.println(json);

                FragmentManager m = getFragmentManager();
                Fragment Step3 = new Step3();
                m.popBackStack();
                m.beginTransaction().replace(R.id.stepfragments, Step3).addToBackStack("step3").commit();
            }
        });
        return v;

    }


    private Boolean checkPhone(){

        // If field is blank and it has less then required digits.
        String s = Phone.getText().toString();
        if(s.length() <10){return false;}
        for(int i = 0 ; i <s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){return false;}






        }

        return true;




    }

    private Boolean CheckFields(){

        String O = "Occupation";
        String L = "Location";
        String La ="Spoken Languages";

        String of = Occupation.getText().toString();
        String Lf = Location.getText().toString();
        String Laf = Languages.getText().toString();

        if(of.length()<=0 ||of == " " || of== O){return false;}
        else if(Lf.length() <=0 || Lf == " " || Lf==L){return false;}
        else if(Laf.length() <=0 || Laf == " " || Laf==La){return false;}
        else{ return true;}


}

public JSONObject BuildJsonArray(String[] values, String type){



    JSONArray ja = new JSONArray();
    JSONObject json = new JSONObject();
    try {
        for (String s : values) {
            if (s != null) {
                JSONObject lparams =  new JSONObject();

                lparams.put("value", s);
                ja.put(lparams);
                json.put(type, ja);

            }

        }





      //  System.out.println(json);
        return json;
    }catch(Exception e){
        Log.e("JSOn","cant create array"); return null;}


    //JSONObject lparams =  new JSONObject();


   // JSONArray  larray = new JSONArray();


}


}
