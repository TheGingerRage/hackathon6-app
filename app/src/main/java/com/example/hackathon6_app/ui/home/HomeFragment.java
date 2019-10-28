package com.example.hackathon6_app.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackathon6_app.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;

public class HomeFragment extends Fragment {
    ImageView imageView;
    Button button;
    Button btnScan;
    EditText editText;
    TextView scanResultText;
    String EditTextValue ;
    Thread thread ;
    public static final int QRcodeWidth = 350 ;
    Bitmap bitmap ;
    TextView tv_qr_readTxt;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        View view = root;

        imageView = (ImageView)view.findViewById(R.id.imageView);
        editText = (EditText)view.findViewById(R.id.editText);
        scanResultText = (TextView)view.findViewById(R.id.scanResultText);
        button = (Button)view.findViewById(R.id.button);
        btnScan = (Button)view.findViewById(R.id.btnScan);
        tv_qr_readTxt = (TextView) view.findViewById(R.id.tv_qr_readTxt);
        tv_qr_readTxt.setText("Nothing has been scanned yet!");

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          if (!editText.getText().toString().isEmpty()) {
                                              EditTextValue = editText.getText().toString();

                                              try {
                                                  bitmap = TextToImageEncode(EditTextValue);
                                                  imageView.setImageBitmap(bitmap);
                                              } catch (WriterException e) {

                                              }
                                          } else {
                                              editText.requestFocus();
                                              Toast.makeText(getActivity(), "Please enter your scanned test", Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  });


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    String ProfileString(){
        return "";
    }
}
