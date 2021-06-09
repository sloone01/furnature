package com.example.furnature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

import com.example.furnature.adapters.CustomGridViewAdapter;
import com.example.furnature.general.SYSTEM;
import com.example.furnature.pojos.Item;

import java.util.ArrayList;

public class UserMenu extends AppCompatActivity {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<>();
    CustomGridViewAdapter customGridAdapter;
    long userid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        userid = getIntent().getLongExtra(SYSTEM.USER.toString(), 0);
        setUpMeenu();
    }

    public void setUpMeenu()
    {
        //set grid view item

        Bitmap addItem = BitmapFactory.decodeResource(this.getResources(), R.drawable.additem);
        Bitmap manage = BitmapFactory.decodeResource(this.getResources(), R.drawable.manage);
        Bitmap orders = BitmapFactory.decodeResource(this.getResources(), R.drawable.cart);
        Bitmap delevery = BitmapFactory.decodeResource(this.getResources(), R.drawable.delivery);
        Bitmap historyIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.history);





        gridArray.add(new Item(addItem,"Furntature",1));
        gridArray.add(new Item(manage,"Search",2));
        gridArray.add(new Item(orders,"Cart",3));
        gridArray.add(new Item(delevery,"New Orders",4));
        gridArray.add(new Item(historyIcon,"Orders On Delivery",5));
        gridArray.add(new Item(historyIcon,"Logout",6));




        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener((parent,view,pos,id)->{

            Item item = gridArray.get(pos);

            Class cls = Login.class;

            switch (item.getId()){

                case 1 :
                    cls = BrandsPage.class;
                    break;
                case 2 :
                    cls = Search.class;
                    break;
                case 3 :
                    cls = ShowCart.class;
                    break;
                case 4:
                    cls = UserPendingOrders.class;
                    break;
                case 5:
                    cls = UserDeliveringOrders.class;
                    break;
                case 6:
                    cls = Login.class;
                    break;


            }



            Intent intent = new Intent(this, cls);

            intent.putExtra(SYSTEM.USER.toString(),userid);
            startActivity(intent);

        });
    }


}
