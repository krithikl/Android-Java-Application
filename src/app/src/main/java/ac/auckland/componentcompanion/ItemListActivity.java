package ac.auckland.componentcompanion;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {


	private String TAG = "ac.auckland.componentcompanion.ItemListActivity";

	/* Define the behaviour of the recycler view */
	private class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {
		public ArrayList<Item> items = null;

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private LinearLayout layout;
			private ImageButton imageView;
			private TextView makeText;
			private TextView valueText;
			private TextView priceText;



			public ViewHolder(View itemView) {
				super(itemView);
				layout = itemView.findViewById(R.id.category_layout);
				imageView = itemView.findViewById(R.id.image);
				makeText  = itemView.findViewById(R.id.make);
				valueText  = itemView.findViewById(R.id.value);
				priceText  = itemView.findViewById(R.id.price);


			}
		}

		/* Called by ViewManager every time a new view is created */
		public ItemListActivity.categoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler, parent, false);
			return new ItemListActivity.categoryAdapter.ViewHolder(view);
		}

		/* Replace content of a view */
		public void onBindViewHolder(ItemListActivity.categoryAdapter.ViewHolder viewholder, final int position) {


			String imageName = items.get(position).getPreview();
			String make = items.get(position).getMake();
			float value = items.get(position).getValue();
			float price = items.get(position).getPrice();
			String unit = items.get(position).getUnit();
			int imageID = items.get(position).getId();

			viewholder.imageView.setImageDrawable(Util.drawableFromAsset(ItemListActivity.this, imageName));
			viewholder.makeText.setText("Make: " + make);
			viewholder.valueText.setText("Value: " + Float.toString(value) + unit);
			viewholder.priceText.setText("Price: "+ Float.toString(price) + "¢");

			viewholder.layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent mainIntent = new Intent(ItemListActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					ItemListActivity.this.startActivity(mainIntent);
					overridePendingTransition(R.anim.slide_in_right,0);
				}
			});

			viewholder.imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent mainIntent = new Intent(ItemListActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					ItemListActivity.this.startActivity(mainIntent);
					overridePendingTransition(R.anim.slide_in_right,0);
				}
			});
		}

		public int getItemCount() {
			return items.size();
		}

		/* Constructor initialises the data we are going to display */
		public categoryAdapter(ArrayList<Item> items) {
			this.items = items;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);


		Intent startActivityIntent = getIntent();
		String category = startActivityIntent.getStringExtra("CATEGORY");

		DataLoader dloader = new DataLoader(this);
		Category categoryItems = dloader.getCategory(category);
		RecyclerView recyclerView = findViewById(R.id.category_items);
		ItemListActivity.categoryAdapter adapter = new ItemListActivity.categoryAdapter(categoryItems.getItems());
		recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(adapter);
		recyclerView.scrollToPosition(0);

		((TextView) findViewById(R.id.search_text)).setText(category.concat("s"));

		OnBackPressedCallback backButtonCall = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				finish();
				overridePendingTransition(0, R.anim.push_down_out);
			}
		};
		getOnBackPressedDispatcher().addCallback(this, backButtonCall);
	}

}
