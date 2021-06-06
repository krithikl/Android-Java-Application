package ac.auckland.componentcompanion;

import android.content.Intent;
import android.util.Log;
import android.view.*;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private String TAG = "ac.auckland.componentcompanion.MainActivity";
	private DataLoader dloader = null;

	/* Define the behaviour of the recycler view */
	private class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.ViewHolder> {
		public ArrayList<Item> items = null;

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private ImageButton imageButton;
			private Button topPickText;
			private TextView topPickViews;

			public ViewHolder(View itemView) {
				super(itemView);
				imageButton = itemView.findViewById(R.id.image);
				topPickText = itemView.findViewById(R.id.topPickMake);
				topPickViews = itemView.findViewById(R.id.topPickViews);


				imageButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Log.d(TAG, "Top Pick Item Clicked!");
					}
				});
			}
		}

		/* Called by ViewManager every time a new view is created */
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_picks_recycler, parent, false);
			return new ViewHolder(view);
		}

		/* Replace content of a view */
		public void onBindViewHolder(ViewHolder viewholder, final int position) {
			String imageName = items.get(position).getPreview();
			String topPickMake = items.get(position).getMake();

			int imageID = items.get(position).getId();
			String itemViews = Integer.toString(dloader.getItemViews(imageID));

			viewholder.imageButton.setImageDrawable(Util.drawableFromAsset(MainActivity.this, imageName));
			viewholder.topPickText.setText(topPickMake);
			viewholder.topPickViews.setText("Views: " + itemViews);

			viewholder.imageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "Top Pick Item Clicked (Layout)!");
					Intent mainIntent = new Intent(MainActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					MainActivity.this.startActivity(mainIntent);
					overridePendingTransition(R.anim.slide_in_right, 0);


				}
			});

			viewholder.topPickText.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d(TAG, "Top Pick Item Clicked (Layout)!");
					Intent mainIntent = new Intent(MainActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					MainActivity.this.startActivity(mainIntent);
					overridePendingTransition(R.anim.slide_in_right, 0);


				}
			});
		}

		public int getItemCount() {
			return items.size();
		}

		/* Constructor initialises the data we are going to display */
		public TopPicksAdapter(ArrayList<Item> items) {
			this.items = items;
		}
	}

	protected void setUpTopPicks() {
		ArrayList<Item> topPicks = dloader.getTopSix();

		RecyclerView recyclerView = findViewById(R.id.top_picks);
		TopPicksAdapter adapter = new TopPicksAdapter(topPicks);

		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.setAdapter(adapter);
		recyclerView.scrollToPosition(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.dloader = new DataLoader(this);

		setUpTopPicks();

		/* Defining buttons on main view to be clicked on and start related activity */
		ImageButton cat0Btn = findViewById(R.id.cat0Btn);
		ImageButton cat1Btn = findViewById(R.id.cat1Btn);
		ImageButton cat2Btn = findViewById(R.id.cat2Btn);
		Button searchButton = findViewById(R.id.searchBar);

		cat0Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Resistor");

				startActivity(activityIntent);
				overridePendingTransition(android.R.anim.slide_in_left, 0);
			}
		});

		cat1Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Capacitor");
				startActivity(activityIntent);
				overridePendingTransition(R.anim.push_up_in, 0);


			}
		});

		cat2Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Inductor");
				startActivity(activityIntent);
				overridePendingTransition(R.anim.slide_in_right, 0);
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(activityIntent);
				overridePendingTransition(android.R.anim.fade_in,0);
			}
		});

	}

	protected void onResume() {
		super.onResume();
		/* Update top picks */
		setUpTopPicks();
		Log.d(TAG, "In onResume()");

	}

}
