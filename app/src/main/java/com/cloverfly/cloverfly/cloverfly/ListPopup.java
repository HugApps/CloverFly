package com.cloverfly.cloverfly.cloverfly;


import android.app.Activity;
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
    String output ="";
    String[] values;
    TableLayout table;
    Button done,back;
    int count =0;
    String[] outputs ;
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

        outputs = new String[values.length];
        for(int i = 0 ; i <values.length ; i ++){
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
           final CheckBox box = new CheckBox(getActivity());

            row.setLayoutParams(lp);

            final TextView  s = new TextView(getActivity());
            s.setText(values[i]);
            box.setId(i);
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CheckBox b =(CheckBox)v.findViewById(v.getId());
                    if(!b.isChecked()){

                        outputs[v.getId()] =null;

                    }
                    else{
                        //outputs[v.getId()] = s.getText().toString();
                        if(type == "occupation"){
                            FragmentManager m = getFragmentManager();


                            TextView occ = (TextView)getActivity().findViewById(R.id.OccupationPop);
                            occ.setTextSize(14);
                            occ.setText(s.getText().toString());
                            m.popBackStack();

                            // Close the popup and set string as the value.

                        }
                        else if(type =="location"){
                            FragmentManager m = getFragmentManager();


                            TextView occ2 = (TextView)getActivity().findViewById(R.id.LocationPop);
                            occ2.setTextSize(14);
                            occ2.setText(s.getText().toString());
                            m.popBackStack();


                        }
                        else{
                            outputs[v.getId()]=s.getText().toString();
                        }

                       }


                   }


            });
            row.addView(s);
            row.addView(box);
            table.addView(row);



        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display =build();
                FragmentManager m = getFragmentManager();
                Fragment frag = m.findFragmentByTag("step2");
                switch(type){

                    /*case "location" :
                        TextView a = (TextView)getActivity().findViewById(R.id.LocationPop);
                        a.setTextSize(14);
                        a.setText(display);
                        mCallback.getSelectedValues(outputs,type);
                        break;*/

                   /* case "occupation":
                        TextView b = (TextView)getActivity().findViewById(R.id.OccupationPop);
                        b.setTextSize(14);
                        b.setText(display);
                        mCallback.getSelectedValues(outputs,type);
                        break;*/
                    case "language":
                        TextView c = (TextView)getActivity().findViewById(R.id.LanguagePop);
                        c.setTextSize(14);

                        c.setText(display);
                        Bundle passback = new Bundle();
                        passback.putStringArray("a",outputs);
                        mCallback.getSelectedValues(outputs,type);

                        break;
                    case "interests":
                        TextView d = (TextView)getActivity().findViewById(R.id.Interests);
                        d.setTextSize(14);
                        d.setText(display);
                        mCallback.getSelectedValues(outputs,type);

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


 private String build (){
     String display="";
     int count = 0;

     for(int i = 0 ; i <outputs.length;i++){


         if(outputs[i] != null ){

             count++;
             display = display+outputs[i] +" ,";



         }


     }
     System.out.println(count);
     if(count > 3){
         display= display.substring(0,display.length()-1);
         display = display.substring(0,display.lastIndexOf(",")) + " " + "and etc";
         return display;
     }
     if(display.length() <=0){return"";}
     display= display.substring(0,display.length()-1);
     return display;
 }

    getSelectedValues mCallback;
    public interface getSelectedValues{
        public void getSelectedValues(String[] values,String type);


    }
    @Override
    // MAke sure activity contains interface
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (getSelectedValues) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
