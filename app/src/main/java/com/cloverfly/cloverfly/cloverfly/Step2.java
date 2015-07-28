package com.cloverfly.cloverfly.cloverfly;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Step2 extends Fragment {

    TextView Occupation,Languages,Location;

    EditText Description,Phone;

    public Step2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.step2, container, false);
       Occupation = (TextView) v.findViewById(R.id.OccupationPop);
       Languages = (TextView) v.findViewById(R.id.LanguagePop);
       Location = (TextView) v.findViewById(R.id.LocationPop);

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
        return v;

    }


}
