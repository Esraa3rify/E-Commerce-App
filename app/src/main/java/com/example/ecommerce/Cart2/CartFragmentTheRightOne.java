package com.example.ecommerce.Cart2;

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

import com.example.ecommerce.Cart;
import com.example.ecommerce.CartViewHolder;
import com.example.ecommerce.R;
import com.example.prevalent.prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private TextView txtTotalAmount;

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
        View view= inflater.inflate(R.layout.fragment_cart_the_right_one, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.cartListRec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        NextProcessBtn=(Button)view.findViewById(R.id.nextBtnCart);
        txtTotalAmount=(TextView) view.findViewById(R.id.totalPriceCart);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference CartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(CartListRef.child("User view").child(prevalent.currentUserOnline.getPhoneNum()).child("products"),Cart.class)
                .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                        cartViewHolder.txtProductName.setText(Cart.getPname());
                        cartViewHolder.txtProductPrice.setText(Cart.getPrice());
                        cartViewHolder.txtProductQuantity.setText(Cart.getQuantity());

                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder=new CartViewHolder(view);

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}