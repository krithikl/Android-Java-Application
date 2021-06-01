package ac.auckland.componentcompanion;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class SearchActivity extends AppCompatActivity {
    private DataLoader dataloader = new DataLoader();

    private String TAG = "ac.auckland.componentcompanion.MainActivity";

    private class searchAdapter extends RecyclerView.Adapter<SearchActivity.searchAdapter.ViewHolder> {
        public ArrayList<Item> items = null;

        /* Define the behaviour of each recycler view item */
        private class ViewHolder extends RecyclerView.ViewHolder {
            private ImageButton imageButton;
            private Button makeText;
            private Button valueText;
            private Button priceText;



            public ViewHolder(View itemView) {
                super(itemView);
                imageButton = itemView.findViewById(R.id.image);
                makeText  = itemView.findViewById(R.id.make);
                valueText  = itemView.findViewById(R.id.value);
                priceText  = itemView.findViewById(R.id.price);

                imageButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.d(TAG, "Top Pick Item Clicked!");
                    }
                });
            }
        }

        /* Called by ViewManager every time a new view is created */
        public SearchActivity.searchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler, parent, false);
            return new SearchActivity.searchAdapter.ViewHolder(view);
        }

        /* Replace content of a view */
        public void onBindViewHolder(SearchActivity.searchAdapter.ViewHolder viewholder, final int position) {
            String imageName = items.get(position).getPreview();
            String make = items.get(position).getMake();
            float value = items.get(position).getValue();
            float price = items.get(position).getPrice();
            String unit = items.get(position).getUnit();
            viewholder.imageButton.setImageDrawable(Util.drawableFromAssest(SearchActivity.this, imageName));
            viewholder.makeText.setText("Make: " + make);
            viewholder.valueText.setText("Value: " + Float.toString(value) + unit);
            viewholder.priceText.setText("Price: "+ Float.toString(price));

        }

        public int getItemCount() {
            return items.size();
        }

        /* Constructor initialises the data we are going to display */
        public searchAdapter(ArrayList<Item> items) {
            this.items = items;
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<SearchActivity.RecyclerAdapter.ViewHolder> implements Filterable {
        public ArrayList<Item> itemList = null;
        public ArrayList<Item> itemListAll = null;

        /* Constructor initialises the data we are going to display */
        private RecyclerAdapter(ArrayList<Item> itemList) {
            DataLoader dloader = new DataLoader();
            this.itemList = itemList;
            this.itemListAll = new ArrayList<>(dloader.getItems());
        }

        /* Define the behaviour of each recycler view item */
        private class ViewHolder extends RecyclerView.ViewHolder {
            private ImageButton imageButton;

            public ViewHolder(View itemView) {
                super(itemView);
                imageButton = itemView.findViewById(R.id.image);
            }
        }

        /* Called by ViewManager every time a new view is created */
        public SearchActivity.RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler, parent, false);
            return new SearchActivity.RecyclerAdapter.ViewHolder(view);
        }

        /* Replace content of a view */
        public void onBindViewHolder(SearchActivity.RecyclerAdapter.ViewHolder viewholder, final int position) {
            String imageName = itemList.get(position).getPreview();
            viewholder.imageButton.setImageDrawable(Util.drawableFromAssest(SearchActivity.this, imageName));
        }

        public int getItemCount() {
            return itemList.size();
        }

        @Override
        public Filter getFilter() {

            return myFilter;
        }

        Filter myFilter = new Filter() {
            /* Runs on a background thread */
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<Item> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(itemListAll);
                }
                else {
                    for (Item item: itemListAll) {
                        if (item.getMake().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }
            /* Runs on a UI thread */
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemList.clear();
                itemList.addAll((Collection<? extends Item>) filterResults.values);
                notifyDataSetChanged();
            }
        };

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        DataLoader dloader = new DataLoader();
        RecyclerView recyclerView = findViewById(R.id.search_recycle);
        SearchActivity.searchAdapter adapter = new SearchActivity.searchAdapter(dloader.getItems());

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(adapter);


        // Back button remap
        OnBackPressedCallback backButtonCall = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backButtonCall);



    }

    public boolean onCreateOptionsMenu(Menu menu) {


        SearchView searchBar = findViewById(R.id.searchBar);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String inputText) {
                SearchActivity.RecyclerAdapter recyclerAdapter = new SearchActivity.RecyclerAdapter(dataloader.items);
                recyclerAdapter.getFilter().filter(inputText);
                RecyclerView recyclerView = findViewById(R.id.search_recycle);

                recyclerView.setAdapter(recyclerAdapter);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}