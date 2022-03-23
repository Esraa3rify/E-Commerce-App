package com.example.ecommerce;

import android.os.Bundle;
import android.view.View;

import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.cart.cartFragment;
import com.example.ecommerce.databinding.ActivityNavDrawerBinding;
import com.example.prevalent.prevalent;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class navDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavDrawerBinding binding;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerViewVar;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);










        //ProductRef = FirebaseDatabase.getInstance().getReference().child("products");

        binding = ActivityNavDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        setSupportActionBar(binding.appBarNavDrawer.toolbar);
        binding.appBarNavDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayoutxml;
        NavigationView navigationView = binding.navView;

        //call imageView and textView from the header
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.User_profile_image);
        //change the text according to the logging credentials
        userNameTextView.setText(prevalent.currentUserOnline.getName());
        //to change the image in the navigation drawer after editing it in the settings layot
        Picasso.get().load(prevalent.currentUserOnline.getImage()).placeholder(R.drawable.profile).into(profileImageView);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cart, R.id.nav_orders, R.id.nav_categories,R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();

      //HumbergerNavDrawer

       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_drawer);
       NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       NavigationUI.setupWithNavController(navigationView, navController);

//        recyclerViewVar = findViewById(R.id.recycler_menu);
//        recyclerViewVar.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerViewVar.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();




//  FirebaseRecyclerOptions<products> options =
//                new FirebaseRecyclerOptions.Builder<products>()
//                        .setQuery(ProductRef, products.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<products, productViewHolder> adapter =
//                new FirebaseRecyclerAdapter<products, productViewHolder>(options) {
//
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull productViewHolder productViewHolder, int i, @NonNull products products) {
//
//                        productViewHolder.textProductdescription.setText(products.getDescription());
//                        productViewHolder.textProductName.setText(products.getPname());
//                        productViewHolder.textproductPrice.setText("Price =" + products.getPrice() + "$");
//                        Picasso.get().load(products.getImage()).into(productViewHolder.ProductImageView);
//
//
//                    }
//
//
//                    @NonNull
//                    @Override
//                    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_items, parent, false);
//                        productViewHolder holder = new productViewHolder(view);
//                        return holder;
//                    }
//
//
//                };
//        recyclerViewVar.setAdapter(adapter);
//        adapter.startListening();
//
//    }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;


    }


    @Override
    public boolean onSupportNavigateUp () {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }




}


