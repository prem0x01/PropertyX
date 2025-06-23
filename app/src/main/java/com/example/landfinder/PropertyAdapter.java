package com.example.landfinder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private Context context;
    private List<PropertyModel> propertyList;
    private OnPropertyClickListener listener;

    public PropertyAdapter(Context context, List<PropertyModel> propertyList, OnPropertyClickListener listener) {
        this.context = context;
        this.propertyList = propertyList;
        this.listener = listener;
    }



    public interface OnPropertyClickListener {
        void onPropertyClick(PropertyModel property);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.property_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PropertyModel property = propertyList.get(position);
        holder.propertyName.setText(property.getName());
        holder.propertyLocation.setText(property.getLocation());
        holder.propertyPrice.setText("₹" + property.getPrice());
        holder.propertyImage.setImageURI(Uri.parse(property.getImageUrl()));

        holder.itemView.setOnClickListener(v -> listener.onPropertyClick(property));
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName, propertyLocation, propertyPrice;
        ImageView propertyImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyName = itemView.findViewById(R.id.property_name);
            propertyLocation = itemView.findViewById(R.id.property_location);
            propertyPrice = itemView.findViewById(R.id.property_price);
            propertyImage = itemView.findViewById(R.id.property_image);
        }
    }
}
