package ac.auckland.componentcompanion;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler, parent, false);
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
}