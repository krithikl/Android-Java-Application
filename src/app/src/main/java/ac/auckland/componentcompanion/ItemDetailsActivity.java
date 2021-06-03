package ac.auckland.componentcompanion;

import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {
	DataLoader dloader;
	private String TAG = "ac.auckland.componentcompanion.ItemDetailsActivity";

	/* Define the behaviour of the recycler view */
	private class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
		private Item item = null;

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private ImageView imView;
			public ViewHolder(View itemView) {
				super(itemView);
				imView = itemView.findViewById(R.id.large_image_view);
			}
		}

		/* Called by ViewManager every time a new view is created */
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_recycler, parent, false);
			return new ViewHolder(view);
		}

		/* Replace content of a view */
		public void onBindViewHolder(ViewHolder viewholder, final int position) {
			if (item == null)
				return;
			String imageName = item.getPreview().concat("-").concat(Integer.toString(position).concat(".png"));
			Log.d(TAG, "Image Name: ".concat(imageName));
			viewholder.imView.setImageDrawable(Util.drawableFromAsset(ItemDetailsActivity.this, imageName));
		}

		public int getItemCount() {
			return 3;
		}

		/* Constructor initialises the data we are going to display */
		public ImagesAdapter(Item item) {
			this.item = item;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_details);
		this.dloader = new DataLoader(this);

		int itemID = getIntent().getIntExtra("itemID", 0);
		Item item = dloader.getItem(itemID);

		RecyclerView recyclerView = findViewById(R.id.large_images);
		/* We want the recycler view to act as a pager, which 'snaps' to an
		* image on swipe. Android provides a built-in helper for this sort of thing.
		*/
		SnapHelper snapHelper = new PagerSnapHelper();
		ItemDetailsActivity.ImagesAdapter adapter = new ItemDetailsActivity.ImagesAdapter(item);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		snapHelper.attachToRecyclerView(recyclerView);
		recyclerView.setAdapter(adapter);
		recyclerView.scrollToPosition(0);

		// Back button remap, goes to previous activity
		OnBackPressedCallback backButtonCall = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				finish();
				overridePendingTransition(0, android.R.anim.slide_out_right);
			}
		};
		getOnBackPressedDispatcher().addCallback(this, backButtonCall);

		getIncomingIntent();
	}

	/* Catch other extras into the conditional to ensure it isn't retrieving intents that don't exist */
	private void getIncomingIntent() {

		if (getIntent().hasExtra("itemID")) {


			int itemID = getIntent().getIntExtra("itemID", 0);

			Item item = dloader.getItem(itemID);
			this.dloader.addItemView(itemID);


			String itemMake = item.getMake();
			String itemValue = Float.toString(item.getValue());
			String itemPrice = Float.toString(item.getPrice());
			String itemUnit = item.getUnit();
			String imageUrl = item.getPreview();
			String itemModel = item.getModel();
			String itemViews = Integer.toString(this.dloader.getItemViews(itemID));
			String itemCat = item.getName();


			setImageDetails(imageUrl, itemMake, itemValue, itemPrice, itemUnit, itemModel, itemViews, itemCat);

		}
	}

	/* Function to set the image details from the list view */
	private void setImageDetails(String imageUrl, String itemMake, String itemValue, String itemPrice, String itemUnit, String itemModel, String itemViews, String itemCat) {
		TextView price = findViewById(R.id.unitPrice);
		TextView make = findViewById(R.id.itemMake);
		TextView value = findViewById(R.id.itemValue);
		TextView model = findViewById(R.id.itemModel);
		ImageView image = findViewById(R.id.image);
		TextView views = findViewById(R.id.itemViews);
		TextView category = findViewById(R.id.category);


		make.setText("Make: " + itemMake);
		if (itemCat == "Resistor") {
			value.setText("Resistance: " + itemValue.concat(itemUnit));
		}

		else if (itemCat == "Capacitor") {
			value.setText("Capacitance: " + itemValue.concat(itemUnit));
		}

		else if (itemCat == "Inductor") {
			value.setText("Inductance: " + itemValue.concat(itemUnit));
		}

		price.setText("Unit Price: " + itemPrice.concat("¢"));
		model.setText("Model: " + itemModel);
		views.setText("Views: " + itemViews);
		category.setText("Type: " + itemCat);

		image.setImageDrawable(Util.drawableFromAsset(this, imageUrl));
	}
}
