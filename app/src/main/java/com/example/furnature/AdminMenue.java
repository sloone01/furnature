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

        Bitmap products = BitmapFactory.decodeResource(this.getResources(), R.drawable.products);
        Bitmap brands = BitmapFactory.decodeResource(this.getResources(), R.drawable.brands);
        Bitmap orders = BitmapFactory.decodeResource(this.getResources(), R.drawable.orders);
        Bitmap delevery = BitmapFactory.decodeResource(this.getResources(), R.drawable.delivery);
        Bitmap historyIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.history);
        Bitmap users = BitmapFactory.decodeResource(this.getResources(), R.drawable.users);





        gridArray.add(new Item(products,"Add Item",1));
        gridArray.add(new Item(products,"Products",2));
        gridArray.add(new Item(products,"Add Catagory",3));
        gridArray.add(new Item(brands,"Brands",4));
        gridArray.add(new Item(orders,"Orders",5));
        gridArray.add(new Item(delevery,"Delivery",6));
        gridArray.add(new Item(users,"Users",8));
        gridArray.add(new Item(historyIcon,"Logout",7));



        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener((parent,view,pos,id)->{

            Item item = gridArray.get(pos);

            Class cls = Login.class;

            switch (item.getId()){

                case 1 :
                    cls = AddProduct.class;
                    break;
                case 2 :
                    cls = ManageItems.class;
                    break;
                case 3 :
                    cls = AddCatagory.class;
                    break;
                case 4:
                    cls = ManageBrands.class;
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
