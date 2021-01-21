# Recycler View - Android

## Purpose:
The purpose of this code base is to explore how a recycler view functions

### RecyclerView:
Recycler view is named so because it it recycles the rows as the user scrolls. This is similar to the dequeing behaviour of UITableView and UICollectionView. 

##### Implementation:
The implementation of the RecyclerView itself is quite straight forward. Place it in your Activities/Fragment's XML file and give it an ID. You will need to create a layout for the row/cell (also an XML file).

### RecyclerViewAdapter:
Like ListViews, RecyclerViews need adapters to adapt the view as the data changes and the user interacts with the view. Unlike ListViews, RecyclerViewAdapters need a ViewHolder to manage the recycling of views that go off the screen. The ViewHolder describes an itemView and metadata about its place within the RecyclerView. This is especially important when views are scrolled off screen. The ViewHolder will remember!

##### Implementation:
When creating a Recycler View Adapter, you want to start by creating the ViewHolder class first. This involes declarding and finding your views within your row/cell layout.

```
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

```

Then, you will need to extend the RecyclerViewAdapter and implement the required methods:
- onCreateViewHolder()
- onBindViewHolder()
- getItemCount()

##### public void onBindViewHolder(@NonNull ViewHolder holder, int position) {}
This method function similar to UICollectionViewDataSource's cellForItemAt(). This is where you customise the row/cell based on it's position.

##### public int getItemCount() {}
This method controls how many rows/cells will be layed out.

##### public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {}
This method is responsible for inflating the view. It will also initialised as viewHolder with the cell/row so that the class will have access to it's view properties.

```
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
  
    private static final String TAG = "RecyclerViewAdapter";
    
    //Properties
    private ArrayList<String> mImageNames = new ArrayList<String>();
    private ArrayList<String> mImages = new ArrayList<String>();
    private Context mContext;

    //Constructor
    public RecyclerViewAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.imageName.setText(mImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: click on: " + mImageNames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

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
```
