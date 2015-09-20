package com.cloverfly.cloverfly.cloverfly;


import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Step3 extends Fragment {

    TextView SnapPhoto,device,Interests;
    ImageView profilepic;
    private Uri file;
    public Step3() {
        // Required empty public constructor
    }
    // dp: -- > photo (string) path
    // send image photo, server handle the path
    // interest - > string array
    //description - > string

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step3, container, false);
        SnapPhoto = (TextView)v.findViewById(R.id.snapphoto);
        profilepic = (ImageView)v.findViewById(R.id.dp);
        device = (TextView) v.findViewById(R.id.fromdevice);
        Interests =(TextView)v.findViewById(R.id.Interests);


        //Drop down selection for Interests
        Interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] list = getResources().getStringArray(R.array.interest);
                Bundle bundle = new Bundle();
                bundle.putStringArray("interests",list);
                bundle.putString("type","interests");
                // Load popup fragment that displays list
                Fragment Locationpop = new ListPopup();
                Locationpop.setArguments(bundle);
                FragmentManager man = getFragmentManager();


                man.beginTransaction().replace(R.id.dropdown,Locationpop).addToBackStack("popup").commit();

            }
        });
        // Allow user to pick from gallery
        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Gallery,2);
                device.setText("Pick Another");
            }
        });
        // Turns on camera to snap photo
        SnapPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to start up camera

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePicture, 1);
                    SnapPhoto.setText("Snap another");

                }

            }

            });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode ==  getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            profilepic.setImageBitmap(imageBitmap);
            return;
        }
        if (requestCode == 2 && resultCode ==  getActivity().RESULT_OK) {
           Uri Selected = data.getData();
            try{
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            String imgDecodableString;
            Cursor cursor = getActivity().getContentResolver().query(Selected,filePathColumn,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString=cursor.getString(columnIndex);
            cursor.close();
            profilepic.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));return;}
            catch(Exception e){return;}




           // Bundle extras = data.getExtras();
          //  Bitmap imageBitmap = (Bitmap) extras.get("data");


        }
        SnapPhoto.setText("Take Photo");
        device.setText("upload from album");
    }




}
