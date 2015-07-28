package com.cloverfly.cloverfly.cloverfly;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListPopup extends Fragment {
    String type;
    String[] values;
    TableLayout table;
    Button done,back;
    ArrayList<String> changestring = new ArrayList<String>();
    public ListPopup() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle pack = getArguments();
        type = pack.getString("type");
        values = pack.getStringArray(type);
        View v = inflater.inflate(R.layout.fragment_list_popup, container, false);
        table = (TableLayout)v.findViewById(R.id.List);
        done = (Button)v.findViewById(R.id.done);
        back = (Button)v.findViewById(R.id.back);

        for(int i = 0 ; i <values.length ; i ++){
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            CheckBox box = new CheckBox(getActivity());

            row.setLayoutParams(lp);

            final TextView  s = new TextView(getActivity());
            s.setText(values[i]);
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!changestring.contains(s.getText().toString())){
                    changestring.add(s.getText().toString());}


                    Log.e("string", changestring.get(0));


                }
            });
            row.addView(s);
            row.addView(box);
            table.addView(row);



        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager m = getFragmentManager();
                Fragment frag = m.findFragmentByTag("step2");
                switch(type){

                    case "location" :
                        TextView a = (TextView)getActivity().findViewById(R.id.LocationPop);
                        if(a.getText().toString().equalsIgnoreCase("Area you live")){
                            a.setText("");
                        }
                        a.setText(a.getText().toString()+buildString());
                        break;

                    case "occupation":
                        TextView b = (TextView)getActivity().findViewById(R.id.OccupationPop);
                        if(b.getText().toString().equalsIgnoreCase("Where do you work?")){
                            b.setText("");
                        }
                        b.setText(b.getText().toString()+buildString());
                        break;
                    case "language":
                        TextView c = (TextView)getActivity().findViewById(R.id.LanguagePop);
                        if(c.getText().toString().equalsIgnoreCase("Select your Languages")){
                            c.setText("");
                        }
                        c.setText(c.getText().toString()+buildString());
                        break;
                }
                //TextView target = (TextView)getActivity().findViewById(R.id.LocationPop);

                m.popBackStack();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager m = getFragmentManager();
                m.popBackStack();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private String buildString(){
        String output ="";
        for(int i = 0 ; i <changestring.size();i ++){
            output = output + ","+changestring.get(i);
        }
        return output;

    }


}
