package com.example.admin.augscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class

viewInventoryActivity extends AppCompatActivity {

    RecyclerView mrecyclerview;
    public  TextView noSuch;
    DatabaseReference mdatabaseReference, mdatabaseReference1;
    private FirebaseAuth firebaseAuth;

    public static String imageURL;
    private final String mFilterValue = ViewInventory.device; // Filter value for itemname

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_inventory);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Items");
        mdatabaseReference1 = FirebaseDatabase.getInstance().getReference("Users").child(resultemail).child("Items").child(additemActivity.itembarcodeValue);
        mrecyclerview = findViewById(R.id.recyclerViews);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        noSuch= findViewById(R.id.NoSuch);
        noSuch.setText("No assets currently present in the Inventory.");
        try {

            if (mFilterValue.equalsIgnoreCase("Computer")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                      if (snapshot.exists()) {




                          List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);
                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("Computer")) {

                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list


                                }
                            }
                            if (items1.size() > 0) {
                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getProcessor(), model.getSysType(), model.getDevice_id(), model.getOS_version(), model.getInstalled_ram(), model.getAssetImg());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            imageURL = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageURL));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }


                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);
                            }else{

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(viewInventoryActivity.this, "No assets in this category.", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            if (mFilterValue.equalsIgnoreCase("laptop")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("Laptop")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list


                                }
                            }
                            if (items1.size() > 0) {

                                // Create and set the adapter for the first list
                                RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                        .UsersViewHolder>() {
                                    @NonNull
                                    @Override
                                    public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//model.getPhone_num()
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent,
                                                false);
                                        return new scanItemsActivity.UsersViewHolder(view);
                                    }

                                    public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                        Items model = items1.get(position);
                                        holder.setDetails(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getProcessor(), model.getSysType(), model.getDevice_id(), model.getOS_version(), model.getInstalled_ram(), model.getAssetImg());

                                        Button urlButton = holder.itemView.findViewById(R.id.imgtxt); // Get the reference to the button
                                        urlButton.setTag(model.getAssetImg()); // Set the URL as the tag of the button

                                        urlButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                                startActivity(intent); // Start the intent to open the URL in the default browser
                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return items1.size();
                                    }
                                };
                                mrecyclerview.setAdapter(adapter1);
                            }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


            if (mFilterValue.equalsIgnoreCase("cpu")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("cpu")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list


                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getProcessor(), model.getSysType(), model.getDevice_id(), model.getOS_version(), model.getInstalled_ram(), model.getAssetImg());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);
                               }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            if (mFilterValue.equalsIgnoreCase("printer")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("Printer")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list


                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);
                               }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            if (mFilterValue.equalsIgnoreCase("Hub")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("Hub")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }


                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);
                                }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            if (mFilterValue.equalsIgnoreCase("monitor")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("monitor")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                                // Create and set the adapter for the first list
                                RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                        .UsersViewHolder>() {
                                    @NonNull
                                    @Override
                                    public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                                false);
                                        return new scanItemsActivity.UsersViewHolder(view);
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                        Items model = items1.get(position);
                                        holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                        Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                        urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                        urlButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                                startActivity(intent); // Start the intent to open the URL in the default browser
                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return items1.size();
                                    }
                                };
                                mrecyclerview.setAdapter(adapter1);
                            }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            if (mFilterValue.equalsIgnoreCase("mouse")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("mouse")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {
                                // Create and set the adapter for the first list
                                RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                        .UsersViewHolder>() {
                                    @NonNull
                                    @Override
                                    public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                                false);
                                        return new scanItemsActivity.UsersViewHolder(view);
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                        Items model = items1.get(position);
                                        holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                        Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                        urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                        urlButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                                startActivity(intent); // Start the intent to open the URL in the default browser
                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return items1.size();
                                    }
                                };
                                mrecyclerview.setAdapter(adapter1);
                            }else{

                            }
                        } else {
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            if (mFilterValue.equalsIgnoreCase("router")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("router")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);

                            }else{

                            }
                        } else {

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            if (mFilterValue.equalsIgnoreCase("keyboard")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("keyboard")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                                // Create and set the adapter for the first list
                                RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                        .UsersViewHolder>() {
                                    @NonNull
                                    @Override
                                    public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                                false);
                                        return new scanItemsActivity.UsersViewHolder(view);
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                        Items model = items1.get(position);
                                        holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                        Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                        urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                        urlButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                                startActivity(intent); // Start the intent to open the URL in the default browser
                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return items1.size();
                                    }
                                };
                                mrecyclerview.setAdapter(adapter1);
                            }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            if (mFilterValue.equalsIgnoreCase("ups")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("ups")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                                // Create and set the adapter for the first list
                                RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                        .UsersViewHolder>() {
                                    @NonNull
                                    @Override
                                    public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                                false);
                                        return new scanItemsActivity.UsersViewHolder(view);
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                        Items model = items1.get(position);
                                        holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                        Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                        urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                        urlButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                                startActivity(intent); // Start the intent to open the URL in the default browser
                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return items1.size();
                                    }
                                };
                                mrecyclerview.setAdapter(adapter1);
                            }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            if (mFilterValue.equalsIgnoreCase("headset")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("headset")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                                if (childvalue.equalsIgnoreCase("headphone")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);

                                }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            if (mFilterValue.equalsIgnoreCase("switch")) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (childvalue.equalsIgnoreCase("switch")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);

                                }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            if ( mFilterValue.equalsIgnoreCase("other")
            ) {
                mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       if (snapshot.exists()) {
                          



                            List<Items> items1 = new ArrayList<>(); // List to hold items to be displayed in list_layout

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String childvalue = dataSnapshot.child("itemname").getValue(String.class);

                                Items item = dataSnapshot.getValue(Items.class); // Get the item object from the snapshot

                                if (!childvalue.equalsIgnoreCase("switch") &&
                                        !childvalue.equalsIgnoreCase("Computer")&&
                                        !childvalue.equalsIgnoreCase("Laptop")&&
                                        !childvalue.equalsIgnoreCase("Cpu")&&
                                        !childvalue.equalsIgnoreCase("Printer")&&
                                        !childvalue.equalsIgnoreCase("Hub")&&
                                        !childvalue.equalsIgnoreCase("monitor")&&
                                        !childvalue.equalsIgnoreCase("switch")&&
                                        !childvalue.equalsIgnoreCase("ups")&&
                                        !childvalue.equalsIgnoreCase("headset")&&
                                        !childvalue.equalsIgnoreCase("router")&&
                                        !childvalue.equalsIgnoreCase("mouse")&&
                                        !childvalue.equalsIgnoreCase("keyboard")&&
                                        !childvalue.equalsIgnoreCase("headphone")&&
                                        childvalue.equalsIgnoreCase("other")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                                if (!childvalue.equalsIgnoreCase("switch") &&
                                        !childvalue.equalsIgnoreCase("Computer")&&
                                        !childvalue.equalsIgnoreCase("Laptop")&&
                                        !childvalue.equalsIgnoreCase("Cpu")&&
                                        !childvalue.equalsIgnoreCase("Printer")&&
                                        !childvalue.equalsIgnoreCase("Hub")&&
                                        !childvalue.equalsIgnoreCase("monitor")&&
                                        !childvalue.equalsIgnoreCase("switch")&&
                                        !childvalue.equalsIgnoreCase("ups")&&
                                        !childvalue.equalsIgnoreCase("router")&&
                                        !childvalue.equalsIgnoreCase("mouse")&&
                                        !childvalue.equalsIgnoreCase("keyboard")&&
                                        !childvalue.equalsIgnoreCase("headphone")&&
                                        !childvalue.equalsIgnoreCase("headset")&&
                                        !childvalue.equalsIgnoreCase("other")) {
                                     noSuch.setText("");
                                    items1.add(item); // Add the item to the first list
                                }
                            }
                            if (items1.size() > 0) {

                            // Create and set the adapter for the first list
                            RecyclerView.Adapter<scanItemsActivity.UsersViewHolder> adapter1 = new RecyclerView.Adapter<scanItemsActivity
                                    .UsersViewHolder>() {
                                @NonNull
                                @Override
                                public scanItemsActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent,
                                            false);
                                    return new scanItemsActivity.UsersViewHolder(view);
                                }

                                @Override
                                public void onBindViewHolder(@NonNull scanItemsActivity.UsersViewHolder holder, int position) {
                                    Items model = items1.get(position);
                                    holder.setDetails2(getApplicationContext(), model.getItembarcode(), model.getItemcategory(), model.getItemname(), model.getPhone_num(), model.getDateValue(), model.getAssign(), model.getCon(), model.getCompany(), model.getModel(), model.getAssetImg1());

                                    Button urlButton = holder.itemView.findViewById(R.id.imgtxt1); // Get the reference to the button
                                    urlButton.setTag(model.getAssetImg1()); // Set the URL as the tag of the button

                                    urlButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imageUrl = (String) v.getTag(); // Get the URL from the tag of the button
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                                            startActivity(intent); // Start the intent to open the URL in the default browser
                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    return items1.size();
                                }
                            };
                            mrecyclerview.setAdapter(adapter1);

                                }else{

                                }
                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }catch (Exception e)
        {

        }
    }
}