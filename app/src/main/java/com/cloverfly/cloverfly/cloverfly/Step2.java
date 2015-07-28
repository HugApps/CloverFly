package com.cloverfly.cloverfly.cloverfly;


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

    Spinner Occupation,Languages,Location;

    EditText Description,Phone;

    public Step2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.step2, container, false);
       Occupation = (Spinner) v.findViewById(R.id.OccupationSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.occupation_list, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.Languages, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(),
                R.array.Location, android.R.layout.simple_spinner_item);

       adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


       Languages = (Spinner)v.findViewById(R.id.LanguageSpinner);
       Location = (Spinner)v.findViewById(R.id.LocationSpinner);
        Occupation.setAdapter(adapter1);
        Languages.setAdapter(adapter2);
        Location.setAdapter(adapter3);

        return v;

    }


}
