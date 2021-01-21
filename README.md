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


### ItemTouchHelperCallbacks
To implement behaviour such as swipe to delete and press and hold to move an item, you need to create an ItemTouchHelper object that handles the behaviour. It containts two required methods, onSwiped() and onMove(). The complexity of the functionality you want to implement when these methods are called will dictate whether you are able to use a SimpleCallBack or if you have to use a the standard CallBack.  

#### Swipe Behaviour
The following is an example of swipe to delete. The SimpleCallBack parameter enums that reference the direction that you want the action to occur (swipe left and/or right, drag up and/or down). Swipe to delete logic is quite simple to implement once you have created your ItemTouchHelperCallback. You need to remove the item from the array (dictated by the row that is being interacted with) and then notify the adapater that there has been changes. ViewHolder.getAdapaterPosition() will get the position of the item that is being interacted with. NotifyDataSetChanged() is self explanatory.

```
    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mNames.remove(viewHolder.getAdapterPosition());
            mImageUrls.remove(viewHolder.getAdapterPosition());
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };
```

The step that is easy to forget is actually attaching the ItemTouchHelperCallback to the recyclerView:

```
    private void setupRecyclerView() {
        //Setup logic
        ...
        //Attach ItemTouchHelper
        new ItemTouchHelper((itemTouchHelperCallBack)).attachToRecyclerView(recyclerView);
    }
```
