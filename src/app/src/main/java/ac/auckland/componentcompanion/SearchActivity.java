package ac.auckland.componentcompanion;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
	private DataLoader dataloader = new DataLoader(this);


	private String TAG = "ac.auckland.componentcompanion.SearchActivity";

	private class searchAdapter extends RecyclerView.Adapter<SearchActivity.searchAdapter.ViewHolder> implements Filterable {
		public ArrayList<Item> itemList = new ArrayList<Item>(0);
		public ArrayList<Item> itemListAll = new ArrayList<Item>(0);

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private LinearLayout layout;
			private ImageButton imageButton;
			private TextView makeText;
			private TextView valueText;
			private TextView priceText;


			public ViewHolder(View itemView) {
				super(itemView);
				layout = itemView.findViewById(R.id.search_recycle_layout);
				imageButton = itemView.findViewById(R.id.image);
				makeText = itemView.findViewById(R.id.make);
				valueText = itemView.findViewById(R.id.value);
				priceText = itemView.findViewById(R.id.price);

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

			int imageID = itemList.get(position).getId();

			String make = itemList.get(position).getMake();
			float value = itemList.get(position).getValue();
			float price = itemList.get(position).getPrice();
			String unit = itemList.get(position).getUnit();

			viewholder.imageButton.setImageDrawable(Util.drawableFromAsset(SearchActivity.this, imageName));
			viewholder.makeText.setText("Make: " + make);
			viewholder.valueText.setText("Value: " + Float.toString(value) + unit);
			viewholder.priceText.setText("Price: " + Float.toString(price));

			viewholder.layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "Top Pick Item Clicked (Layout)!");
					Intent mainIntent = new Intent(SearchActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);


					SearchActivity.this.startActivity(mainIntent);


				}
			});

			viewholder.imageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "Top Pick Item Clicked (Layout)!");
					Intent mainIntent = new Intent(SearchActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					SearchActivity.this.startActivity(mainIntent);


				}
			});

		}

		public Filter getFilter() {

			return myFilter;
		}
		/* Filters the input sequence and checks if the sequence matches, name, model, or value*/
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
						if (item.getMake().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
								item.getName().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
								item.getModel().toLowerCase().contains(charSequence.toString().toLowerCase()) ||
								Float.toString(item.getValue()).toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
				itemList.addAll((ArrayList<Item>) filterResults.values);
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
		DataLoader dloader = new DataLoader(this);
		RecyclerView recyclerView = findViewById(R.id.search_recycle);
		SearchActivity.searchAdapter adapter = new SearchActivity.searchAdapter(dloader.getItems());

		recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(adapter);

		SearchView searchBar = findViewById(R.id.searchBar);
		searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				if (query.equals("DBG_RESET_VIEWS")) {
					dloader.resetViews();
				}

				SearchActivity.searchAdapter searchAdapter = new SearchActivity.searchAdapter(dataloader.getItems());
				searchAdapter.getFilter().filter(query);
				RecyclerView recyclerView = findViewById(R.id.search_recycle);
				recyclerView.setAdapter(searchAdapter);

				return false;
			}

			@Override
			public boolean onQueryTextChange(String inputText) {
				SearchActivity.searchAdapter searchAdapter = new SearchActivity.searchAdapter(dataloader.getItems());
				searchAdapter.getFilter().filter(inputText);
				RecyclerView recyclerView = findViewById(R.id.search_recycle);
				recyclerView.setAdapter(searchAdapter);
				return false;
			}
		});

		/* Populate the search list with all items by default */
		SearchActivity.searchAdapter searchAdapter = new SearchActivity.searchAdapter(dataloader.getItems());
		searchAdapter.getFilter().filter("");
		recyclerView.setAdapter(searchAdapter);

		/* Make the keyboard visible and give focus to the search input */
		searchBar.requestFocus();
		/* Show the keyboard */
		((InputMethodManager) (getSystemService(Context.INPUT_METHOD_SERVICE))).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

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
