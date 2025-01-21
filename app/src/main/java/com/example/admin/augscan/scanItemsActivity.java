package com.example.admin.augscan;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class scanItemsActivity extends AppCompatActivity {
    public static EditText resultsearcheview;
    private FirebaseAuth firebaseAuth;
    ImageButton scantosearch;
    Button searchbtn;
    public static String imageUrl;
    String searchtext="";
    Adapter adapter;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;
    DatabaseReference databaseReference;
    TextView colorSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Items");
        resultsearcheview = findViewById(R.id.searchfield);
        scantosearch = findViewById(R.id.imageButtonsearch);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        searchbtn = findViewById(R.id.searchbtnn);
        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);


        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));


        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitysearch.class));
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchtext = resultsearcheview.getText().toString().trim();
                if(searchtext.isEmpty()){
                    Toast.makeText(scanItemsActivity.this, "Please enter or scan Barcode. ", Toast.LENGTH_SHORT).show();
                }
                databaseReference.child(resultemail).child("Items").child(searchtext).addListenerForSingleValueEvent(new ValueEventListener() {

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                        } else {
                            // Item does not exist in the database, show an error message
                            Toast.makeText(scanItemsActivity.this, "Item does not exist in the database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {
                        // Handle errors here
                        Toast.makeText(scanItemsActivity.this, "Problem occurred", Toast.LENGTH_SHORT).show();
                    }
                });
                firebasesearch(searchtext);
            }
        });

    }

    public void firebasesearch(String searchtext){
        String parentValue = searchtext;
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        Query query = mdatabaseReference.orderByChild("itembarcode").equalTo(parentValue);
        Query firebaseSearchQuery = mdatabaseReference.orderByChild("itembarcode").startAt(searchtext).endAt(searchtext+"\uf8ff");
        final FirebaseRecyclerAdapter<Items, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Items, UsersViewHolder>
                (  Items.class,
                        R.layout.list_layout,
                        UsersViewHolder.class,
                        firebaseSearchQuery )
        {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Items model,int position){
                viewHolder.setDetails(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getProcessor(), model.getSysType(), model.getDevice_id(), model.getOS_version(), model.getInstalled_ram(), model.getAssetImg());
                Button bb = viewHolder.itemView.findViewById(R.id.imgtxt); // Get the reference to the button
                bb.setTag(model.getAssetImg()); // Set the URL as the tag of the button

                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageUrl=(String) v.getTag(); // Get the URL from the tag of the button
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                        startActivity(intent); // Start the intent to open the URL in the default browser
                    }
                });
            }
        };
        final FirebaseRecyclerAdapter<Items, UsersViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<Items, UsersViewHolder>
                (  Items.class,
                        R.layout.list_layout2,
                        UsersViewHolder.class,
                        firebaseSearchQuery )
        {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Items model,int position){

                viewHolder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());
                Button urlButton = viewHolder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                urlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageUrl=(String) v.getTag(); // Get the URL from the tag of the button
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                        startActivity(intent); // Start the intent to open the URL in the default browser
                    }
                });
            }

        };
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String childValue = dataSnapshot.child("itemname").getValue(String.class); // Replace with the actual key of the child node
                    // Do something with the child value

                    if(childValue.equalsIgnoreCase("Computer")||childValue.equalsIgnoreCase("CPU")||childValue.equalsIgnoreCase("Laptop"))
                    {
                        mrecyclerview.setAdapter(firebaseRecyclerAdapter);
                    }
                    else{

                        mrecyclerview.setAdapter(firebaseRecyclerAdapter2);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(View itemView){
            super(itemView);
            mView =itemView;
        }

        public  void setDetails(Context ctx,String itembarcode, String itemcategory, String itemname, String itemprice,String dateValue,String assignto,String Processor,String Device_id,String SysType,String OS_version,String Installed_ram, String Img){
            TextView item_barcode = (TextView) mView.findViewById(R.id.viewitembarcode);
            TextView item_name = (TextView) mView.findViewById(R.id.viewitemname);
            TextView item_category = (TextView) mView.findViewById(R.id.viewitemcategory);
            TextView item_price = (TextView) mView.findViewById(R.id.viewitemprice);
            TextView date_value = (TextView) mView.findViewById(R.id.viewDate);
            TextView assign_to = (TextView) mView.findViewById(R.id.assign);
            TextView Pro1 = (TextView)mView.findViewById(R.id.procid);
            TextView Dev1 = (TextView)mView.findViewById(R.id.dev);
            TextView Sys1 = (TextView)mView.findViewById(R.id.sysid);
            TextView OS1 = (TextView)mView.findViewById(R.id.osid);
            TextView IR1 = (TextView)mView.findViewById(R.id.ram);
            TextView IMG = (TextView)mView.findViewById(R.id.imgurl);
            item_barcode.setText(itembarcode);
            item_category.setText(itemcategory);
            item_name.setText(itemname);
            item_price.setText(itemprice);
            date_value.setText(dateValue);
            assign_to.setText(assignto);
            Pro1.setText(Processor);
            Dev1.setText(Device_id);
            Sys1.setText(SysType);
            OS1.setText(OS_version);
            IR1.setText(Installed_ram);
            // IMG.setText(Img);

        }

        public void setDetails2(Context ctx,String itembarcode, String itemcategory, String itemname, String itemprice,String dateValue,String assignt,String connection,String company1,String model1, String Img1){
            TextView item_barcode = (TextView) mView.findViewById(R.id.viewitembarcode);
            TextView item_name = (TextView) mView.findViewById(R.id.viewitemname);

            TextView item_category = (TextView) mView.findViewById(R.id.viewitemcategory);
            TextView item_price = (TextView) mView.findViewById(R.id.viewitemprice);
            TextView date_value = (TextView) mView.findViewById(R.id.viewDate);
            TextView assign_to = (TextView) mView.findViewById(R.id.assign);
            TextView con = (TextView)mView.findViewById(R.id.ConTpe);
            TextView company = (TextView)mView.findViewById(R.id.company);
            TextView model = (TextView)mView.findViewById(R.id.ModelName);
            TextView img1 = (TextView)mView.findViewById(R.id.imgurl1);
            item_barcode.setText(itembarcode);
            item_category.setText(itemcategory);
            item_name.setText(itemname);
            item_price.setText(itemprice);
            date_value.setText(dateValue);
            assign_to.setText(assignt);
            con.setText(connection);
            company.setText(company1);
            model.setText(model1);
            //img1.setText(Img1);
        }

    }
}