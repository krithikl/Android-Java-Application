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


	private String TAG = "ac.auckland.componentcompanion.SearchActivity";

	private class searchAdapter extends RecyclerView.Adapter<SearchActivity.searchAdapter.ViewHolder> implements Filterable {
		public ArrayList<Item> itemList = new ArrayList<Item>(0);
		public ArrayList<Item> itemListAll = new ArrayList<Item>(0);

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private ImageButton imageButton;
			private Button makeText;
			private Button valueText;
			private Button priceText;


			public ViewHolder(View itemView) {
				super(itemView);
				imageButton = itemView.findViewById(R.id.image);
				makeText = itemView.findViewById(R.id.make);
				valueText = itemView.findViewById(R.id.value);
				priceText = itemView.findViewById(R.id.price);

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
			String imageName = itemList.get(position).getPreview();
			String make = itemList.get(position).getMake();
			float value = itemList.get(position).getValue();
			float price = itemList.get(position).getPrice();
			String unit = itemList.get(position).getUnit();

			viewholder.imageButton.setImageDrawable(Util.drawableFromAssest(SearchActivity.this, imageName));
			viewholder.makeText.setText("Make: " + make);
			viewholder.valueText.setText("Value: " + Float.toString(value) + unit);
			viewholder.priceText.setText("Price: " + Float.toString(price));


		}

		public Filter getFilter() {

			return myFilter;
		}

		Filter myFilter = new Filter() {
			/* Runs on a background thread */
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				Log.d(TAG, "Running Filter");
				ArrayList<Item> filteredList = new ArrayList<>();
				Log.d(TAG, "Search Term: ".concat(charSequence.toString()));
				if (charSequence == null || charSequence.length() == 0) {
					filteredList.addAll(itemListAll);
					Log.d(TAG, "No Search Term, Adding all items to item list");
				} else {
					for (Item item : itemListAll) {
						if (item.getMake().toLowerCase().contains(charSequence.toString().toLowerCase())) {
							Log.d(TAG, "Adding the following item to filter list: ".concat(Integer.toString(item.getId())));
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

		public int getItemCount() {
			return itemList.size();
		}

		/* Constructor initialises the data we are going to display */
		public searchAdapter(ArrayList<Item> items) {
			this.itemListAll = items;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		Log.d(TAG, "onCreate()");
		DataLoader dloader = new DataLoader();
		RecyclerView recyclerView = findViewById(R.id.search_recycle);
		SearchActivity.searchAdapter adapter = new SearchActivity.searchAdapter(dloader.getItems());

		recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, true));
		recyclerView.setAdapter(adapter);

		SearchView searchBar = findViewById(R.id.searchBar);
		searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {

				return false;
			}

			@Override
			public boolean onQueryTextChange(String inputText) {
				SearchActivity.searchAdapter searchAdapter = new SearchActivity.searchAdapter(dataloader.items);
				searchAdapter.getFilter().filter(inputText);
				RecyclerView recyclerView = findViewById(R.id.search_recycle);
				recyclerView.setAdapter(searchAdapter);
				return false;
			}
		});

		/* Populate the search list with all items by default */
		SearchActivity.searchAdapter searchAdapter = new SearchActivity.searchAdapter(dataloader.items);
		searchAdapter.getFilter().filter("");
		recyclerView.setAdapter(searchAdapter);

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
