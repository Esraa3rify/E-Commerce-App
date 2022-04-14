package com.example.ecommerce;

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
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;

    public searchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBtn = (Button) view.findViewById(R.id.searchButton);
        inputText = (EditText) view.findViewById(R.id.searchProductName);
        searchList = (RecyclerView) view.findViewById(R.id.searchList);
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = inputText.getText().toString();
                onStart();



            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");

        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(reference.orderByChild("pname").startAt(searchInput), products.class)
                .build();


        FirebaseRecyclerAdapter<products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<products, ProductViewHolder>(options) {



                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsinfo, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }




                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull products products) {

                        productViewHolder.txtProductName.setText(products.getPname());
                        productViewHolder.txtProductDescription.setText(products.getDescription());
                        productViewHolder.txtProductPrice.setText(products.getPrice());
                        Picasso.get().load(products.getImage()).into(productViewHolder.imageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //to intent from adapter
                                //to putExtra in adapter
                                Intent intent = new Intent(v.getContext(), productdetail.class);
                                intent.putExtra("pid", com.example.ecommerce.products.getPid());
                                v.getContext().startActivity(intent);
                            }
                        });


                    }

                };
        searchList.setAdapter(adapter);
        adapter.startListening();






    }


}
