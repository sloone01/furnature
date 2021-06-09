package com.example.market.general;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;


import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class FileProcessor {


    @TargetApi(Build.VERSION_CODES.O)
    public static String encodeFile(Bitmap bitmap) {


        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytesArray = stream.toByteArray();


            return Base64.getEncoder().encodeToString(bytesArray);


        } catch (Exception e) {
            throw new FilePrcessingException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static Bitmap decodeFile(String fileString) {



        try  {

            byte[] decodedBytes = Base64.getDecoder().decode(fileString);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);

            return bitmap;

        } catch (Exception e) {
            throw new FilePrcessingException(e);
        }



    }
}

