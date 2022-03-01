package viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.ItemClickListener;
import com.example.ecommerce.R;

public class productViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textProductName,  textProductdescription,textproductPrice;
    public ImageView ProductImageView;
    public ItemClickListener listener;

    public productViewHolder(@NonNull View itemView) {
        super(itemView);

        ProductImageView=(ImageView) itemView.findViewById(R.id.product_image);
        textProductdescription=(TextView) itemView.findViewById(R.id.product_description);
        textProductName=(TextView) itemView.findViewById(R.id.product_name);
        textproductPrice=(TextView) itemView.findViewById(R.id.product_price);


    }
    public void setItemOnClickListener(ItemClickListener listener){

        this.listener=listener;

    }

    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);

    }
}
