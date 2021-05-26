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

public class AdminMenue extends AppCompatActivity {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<>();
    CustomGridViewAdapter customGridAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menue);

        setUpMeenu();
    }

    public void setUpMeenu()
    {
        //set grid view item

        Bitmap addItem = BitmapFactory.decodeResource(this.getResources(), R.drawable.additem);
        Bitmap manage = BitmapFactory.decodeResource(this.getResources(), R.drawable.manage);
        Bitmap orders = BitmapFactory.decodeResource(this.getResources(), R.drawable.orders);
        Bitmap delevery = BitmapFactory.decodeResource(this.getResources(), R.drawable.delevery);
        Bitmap historyIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.history);
        Bitmap users = BitmapFactory.decodeResource(this.getResources(), R.drawable.users);





        gridArray.add(new Item(addItem,"Add Item",1));
        gridArray.add(new Item(manage,"ManageItems",2));
        gridArray.add(new Item(addItem,"Add Catagory",3));
        gridArray.add(new Item(manage,"Manage Catagories",4));
        gridArray.add(new Item(orders,"New Orders",5));
        gridArray.add(new Item(delevery,"Delivery Queue",6));
        gridArray.add(new Item(users,"Manage Users",8));
        gridArray.add(new Item(historyIcon,"Logout",7));



        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener((parent,view,pos,id)->{

            Item item = gridArray.get(pos);

            Class cls = Login.class;

            switch (item.getId()){

                case 1 :
                    cls = AddFurnature.class;
                    break;
                case 2 :
                    cls = ManageItems.class;
                    break;
                case 3 :
                    cls = AddCatagory.class;
                    break;
                case 4:
                    cls = ManageCatagories.class;
                    break;
                case 5:
                    cls = AdminPendingOrders.class;
                    break;
                case 6:
                    cls = AdminDeliveryOrders.class;
                    break;
                case 7:
                    cls = Login.class;
                    break;
                case 8:
                    cls = AdminManageUsers.class;
                    break;

            }



            startActivity(new Intent(this,cls));

        });
    }


}
