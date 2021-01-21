package com.example.todolistprototype;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    //Type logt and autocomplete. You can use the TAG in your logs.
    private static final String TAG = "RecyclerViewAdapter";

    //Array List
    private ArrayList<String> mImageNames = new ArrayList<String>();
    private ArrayList<String> mImages = new ArrayList<String>();

    //The context that is creating this RecyclerViewAdapter will need to inject itself so that the onClick will be able to interact with that context (example: Make a toast).
    private Context mContext;

    //Constructor
    public RecyclerViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    //This method is responsible for inflating the view. On the creation of the viewHolder, it will need to inflate the rows. It is putting the views into the position that they need to be put in.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    //This is where you customise the view of the rows.
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");

        //Glide is a dependency that was installed to load images.
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);


        holder.imageName.setText(mImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click on: " + mImageNames.get(position));
            }
        });
    }

    @Override
    //How many rows you should have
    public int getItemCount() {
        return mImageNames.size();
    }

    //Holds the widgets in memory of each individual entry. It's holding that view in memory and getting ready to add the next one - CodingWithMitch
    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
