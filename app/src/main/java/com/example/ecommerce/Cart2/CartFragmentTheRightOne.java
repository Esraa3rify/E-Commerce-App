package com.example.ecommerce.Cart2;

import static android.content.Intent.getIntent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Cart;
import com.example.ecommerce.CartViewHolder;
import com.example.ecommerce.ConfirmFinalActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.navDrawer;
import com.example.ecommerce.productdetail;
import com.example.ecommerce.products;
import com.example.model.users;
import com.example.prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragmentTheRightOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragmentTheRightOne extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager LayoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtmsg1;
    private String productId = "";
    private int overtotalPrivce = 0;
    private String totalAmount = "";


    public CartFragmentTheRightOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragmentTheRightOne.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragmentTheRightOne newInstance(String param1, String param2) {
        CartFragmentTheRightOne fragment = new CartFragmentTheRightOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart_the_right_one, container, false);


        totalAmount = getActivity().getIntent().getStringExtra("Total Price");
        productId = getActivity().getIntent().getStringExtra("pid");

        recyclerView = (RecyclerView) view.findViewById(R.id.cartListRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NextProcessBtn = (Button) view.findViewById(R.id.nextBtnCart);
        txtTotalAmount = (TextView) view.findViewById(R.id.totalPriceCart);
        txtmsg1 = (TextView) view.findViewById(R.id.MSG1);


        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtTotalAmount.setText("Total Price = " + String.valueOf(overtotalPrivce));
                Intent intent = new Intent(getActivity(), ConfirmFinalActivity.class);
                intent.putExtra("Total Price", String.valueOf(overtotalPrivce));
                startActivity(intent);


            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        CheckOrderState();

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference CartListRef = database.getReference().child("Cart List");

            //final DatabaseReference CartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
            FirebaseRecyclerOptions<Cart> options =
                    new FirebaseRecyclerOptions.Builder<Cart>()
                            .setQuery(CartListRef.child("User view").child(users.getPhoneNum()).child("products"), Cart.class)
                            .build();

            FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                            cartViewHolder.txtProductName.setText(Cart.getPname());
                            cartViewHolder.txtProductPrice.setText(Cart.getPrice());
                            cartViewHolder.txtProductQuantity.setText(Cart.getQuantity());

                            int OneTypeProductPrice = ((Integer.parseInt(Cart.getPrice()))) * Integer.parseInt(Cart.getQuantity());
                            overtotalPrivce = overtotalPrivce + OneTypeProductPrice;
                            cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    CharSequence options[] = new CharSequence[]{
                                            "Edit",
                                            "Remove"


                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Cart Options:");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            if (i == 0) {
                                                Intent intent = new Intent(getActivity(), productdetail.class);
                                                intent.putExtra("pid", Cart.getPid());
                                                startActivity(intent);
                                            }
                                            if (i == 1) {
                                                CartListRef.child("User View")
                                                        .child(prevalent.currentUserOnline.getPhoneNum())
                                                        .child("products")
                                                        .child(Cart.getPid())
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getActivity(), "Item removed successfully...", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(getActivity(), navDrawer.class);
                                                                    startActivity(intent);
                                                                }

                                                            }
                                                        });
                                            }


                                        }
                                    });

                                    builder.show();
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                            CartViewHolder holder = new CartViewHolder(view);

                            return holder;
                        }
                    };


            recyclerView.setAdapter(adapter);
            adapter.startListening();

        } catch (NullPointerException ignored) {

        }
    }




        //Another way to add data to fb;


        // OrdersRef.child("Orders").child(users.getPhoneNum()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
        //@Override
        // public void onSuccess(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        String shippingState = dataSnapshot.child("state").getValue().toString();
//                        String UserName = dataSnapshot.child("name").getValue().toString();
//
//                        if (shippingState.equals("shipped")) {
//                            txtTotalAmount.setText("Dear" + UserName + "\n Order is shipped successfully!");
//                            recyclerView.setVisibility(View.GONE);
//                            txtmsg1.setText("Congratulations, Your final order has been shipped successfully.");
//                            txtmsg1.setVisibility(View.VISIBLE);
//                            NextProcessBtn.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "You can purchase more products, once you received your first order.", Toast.LENGTH_SHORT).show();
//
//                        } else if (shippingState.equals("Not shipped")) {
//
//                            txtTotalAmount.setText("Shipping state=Not shipped!");
//                            recyclerView.setVisibility(View.GONE);
//
//                            txtmsg1.setVisibility(View.VISIBLE);
//                            NextProcessBtn.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), "You can purchase more products, once you received your first order.", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }

        // }
        //  });
        private void CheckOrderState() {


        try {


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference OrdersRef = database.getReference();
            OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalent.currentUserOnline.getPhoneNum());

            OrdersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String shippingState = snapshot.child("state").getValue().toString();
                        String UserName = snapshot.child("name").getValue().toString();

                        if (shippingState.equals("shipped")) {
                            txtTotalAmount.setText("Dear" + UserName + "\n Order is shipped successfully!");
                            recyclerView.setVisibility(View.GONE);
                            txtmsg1.setText("Congratulations, Your final order has been shipped successfully.");
                            txtmsg1.setVisibility(View.VISIBLE);
                            NextProcessBtn.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "You can purchase more products, once you received your first order.", Toast.LENGTH_SHORT).show();

                        } else if (shippingState.equals("Not shipped")) {

                            txtTotalAmount.setText("Shipping state=Not shipped!");
                            recyclerView.setVisibility(View.GONE);

                            txtmsg1.setVisibility(View.VISIBLE);
                            NextProcessBtn.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "You can purchase more products, once you received your first order.", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (NullPointerException ignored) {

        }
    }
}
