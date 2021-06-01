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
import android.widget.TextView;
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
			private ImageButton imageButton;
			private TextView makeText;
			private TextView valueText;
			private TextView priceText;



			public ViewHolder(View itemView) {
				super(itemView);
				layout = itemView.findViewById(R.id.category_layout);
				imageButton = itemView.findViewById(R.id.image);
				makeText  = itemView.findViewById(R.id.make);
				valueText  = itemView.findViewById(R.id.value);
				priceText  = itemView.findViewById(R.id.price);

				layout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.d(TAG, "Top Pick Item Clicked (Layout)!");
						setContentView(R.layout.activity_item_details);

					}
				});

				imageButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d(TAG, "Top Pick Item Clicked (Image Button)!");
					}
				});
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
			viewholder.imageButton.setImageDrawable(Util.drawableFromAssest(ItemListActivity.this, imageName));
			viewholder.makeText.setText("Make: " + make);
			viewholder.valueText.setText("Value: " + Float.toString(value) + unit);
			viewholder.priceText.setText("Price: "+ Float.toString(price));

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

		DataLoader dloader = new DataLoader();
		Category categoryItems = dloader.getCategory(category);
		RecyclerView recyclerView = findViewById(R.id.category_items);
		ItemListActivity.categoryAdapter adapter = new ItemListActivity.categoryAdapter(categoryItems.getItems());
		recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this, LinearLayoutManager.VERTICAL, true));
		recyclerView.setAdapter(adapter);
		/* Remember order is reversed above */
		recyclerView.scrollToPosition(categoryItems.getLength() - 1);

		((TextView) findViewById(R.id.search_text)).setText(category.concat("s"));
	}

}
