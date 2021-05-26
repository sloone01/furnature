package com.example.furnature.general;


import android.content.Context;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Helper {

     public static <T extends Model> T getModelById(Class<T> clazz,final long id)
     {
         return new Select().from(clazz).where("id=?",id).executeSingle();
     }
     public static void message(Context context,String string)
     {
         Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
     }

    private static String myFormat = "yyyy-MM-dd"; //In which you need put here
    public  static SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

}
