package com.jax.android.jaxsqrcodeattendancesystemsecond;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;



import java.net.NetworkInterface;
import java.security.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class GenerateQRcode extends AppCompatActivity {
    String TAG = "AMS";
    private ProgressBar progressView;
    private ImageView qrCodeImageView;
    Bitmap generatedImage;
    String courseId = "SE001";
    String timeStamp;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        // prevent from tkaing screen shots
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Date date = new Date();

        //get data from other activity

        //courseId = getIntent().getExtras("course_id");
        //*****Left get courseId part*****
        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("USER_INFO"), User.class);

        timeStamp = (new java.sql.Timestamp(date.getTime())).toString();

        progressView = (ProgressBar)findViewById(R.id.login_progress);
        qrCodeImageView=(ImageView)findViewById(R.id.qrCodeImage);
        //empty transparent background
        qrCodeImageView.setImageResource(android.R.color.transparent);

        AsyncCallWS task = new AsyncCallWS();
        task.execute();


    }

    //generate QR code

    private Bitmap generateQRCode(String content)
    {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            //Find screen size

            EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 800  , 800,hint);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    // pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000
                    // : 0xFFFFFFFF;
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.w(TAG, "QR Code Generator Error:"+e.toString());

        }
        return null;
    }

    private class AsyncCallWS extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String macAddress = "0";
            try {
                macAddress = getMacAddress();
            }
            catch(Exception ex){
                ex.printStackTrace();

            }
            // the content needs to encode
            String content = user.getStudentID() + ";" + courseId + ";" + macAddress + " ; " + timeStamp;
            Log.w("AMS", content);
            generatedImage = generateQRCode(content);
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            progressView.setVisibility(View.GONE);
            if (generatedImage != null)
                qrCodeImageView.setImageBitmap(generatedImage);
            else
                Log.w(TAG, "QR Code is null");
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            progressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            progressView.animate();
        }
    }
    // get Mac address
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}



