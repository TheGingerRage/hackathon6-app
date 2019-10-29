package com.example.hackathon6_app.ui.QR;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Arrays;
import java.util.ArrayList;

import com.example.hackathon6_app.R;
//import com.example.hackathon6_app.bl.Profile;
import com.example.hackathon6_app.profile.ProfileCard;
//import com.example.hackathon6_app.ui.new_connection.NewConnection;
import com.example.hackathon6_app.bl.StaticResources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;


public class QRFragment extends Fragment {
    public static final int QRcodeWidth = 350 ;
    private QRFragmentViewModel qrViewModel;
    private Button ScanButton;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        qrViewModel = ViewModelProviders.of(this).get(QRFragmentViewModel.class);
        root = inflater.inflate(R.layout.fragment_qr, container, false);

        ImageView qrCodeImg = root.findViewById(R.id.qrCodeImage);
        ScanButton = root.findViewById(R.id.scanButton);

        LinearLayout scanResultLayout = root.findViewById(R.id.scanResult);
        scanResultLayout.setVisibility(View.INVISIBLE);

//        Profile userProfile = new Profile();
//        userProfile.FirstName = "Stephen";
//        userProfile.LastName = "Middaugh";
//        userProfile.Company = "nCino";
//        userProfile.TwitterHandle = "@middaughs";
//        userProfile.Email = "stephen.middaugh@ncino.com";
//        userProfile.AboutMe = "I am a software engineer living in Wilmington, NC";
//        userProfile.Title = "Software Developer";

        final String jsonProfile = SerializeProfile(StaticResources.Main_Profile);

        ObjectMapper mapper = new ObjectMapper();

        ProfileCard p;
        try{
            p = mapper.readValue(jsonProfile, ProfileCard.class);
        }
        catch(IOException e){

        }


        try{
            Bitmap bitmap = TextToImageEncode(jsonProfile);
            qrCodeImg.setImageBitmap(bitmap);
        }
        catch(WriterException e){
            // we'll worry about exceptions later :)
        }

        ScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        return root;
    }

    String SerializeProfile(ProfileCard profile){
        if(profile == null){
            return "";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        try{
            String serializedProfile = objectWriter.writeValueAsString(profile);

            return serializedProfile;
        }
        catch (JsonProcessingException e){
            // meh
        }

        return "";
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}
