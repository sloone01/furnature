package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

import com.example.furnature.adapters.CustomGridViewAdapter;
import com.example.furnature.pojos.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<>();
    CustomGridViewAdapter customGridAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpMeenu();
    }

    public void setUpMeenu()
    {
        //set grid view item

        Bitmap profileIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.profile);
        Bitmap myworkicon = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);
        Bitmap servicesIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.services);
        Bitmap searchIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.search_res);
        Bitmap historyIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.history);





        gridArray.add(new Item(profileIcon,"Profile",1));
        gridArray.add(new Item(searchIcon,"Shop Online",2));
        gridArray.add(new Item(myworkicon,"My Orders",5));
        gridArray.add(new Item(servicesIcon,"Delivery",6));
        gridArray.add(new Item(historyIcon,"History",7));



        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener((parent,view,pos,id)->{

            Item item = gridArray.get(pos);

            Class cls = Login.class;

            switch (item.getId()){

                case 1 :
                    cls = AddImages.class;
                    break;
                case 2 :
                    cls = ShopOnline.class;
                    break;
                case 3 :
                    cls = Login.class;
                    break;
                case 4:
                    cls = AddFurnature.class;
                    break;
                case 5:
                    cls = Login.class;
                    break;
                case 6:
                    cls = Login.class;
                    break;
                case 7:
                    cls = Login.class;
                    break;
                case 8:
                    cls = Login.class;
                    break;

            }



            startActivity(new Intent(this,cls));

        });
    }


}

