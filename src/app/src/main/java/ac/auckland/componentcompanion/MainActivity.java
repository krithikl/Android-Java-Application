package ac.auckland.componentcompanion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;
import android.view.*;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
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
			viewholder.topPickViews.setText("Views: ".concat(itemViews));

			View.OnClickListener activity_func = new View.OnClickListener() {
				public void onClick(View v) {
					Log.d(TAG, "Top Pick Item Clicked (Layout)!");
					Pair<View, String> a0 = Pair.create(viewholder.imageButton, "item_details_transition");

					ActivityOptionsCompat optns = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, a0);
					Intent mainIntent = new Intent(MainActivity.this, ItemDetailsActivity.class);
					mainIntent.putExtra("itemID", imageID);
					MainActivity.this.startActivity(mainIntent, optns.toBundle());
				}
			};
			viewholder.imageButton.setOnClickListener(activity_func);
			viewholder.topPickText.setOnClickListener(activity_func);
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
		Parcelable recyclerViewState = null;
		if (recyclerView.getLayoutManager() != null)
			 recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		recyclerView.setAdapter(adapter);

		if (recyclerViewState != null)
			recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
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
				MainActivity.this.startActivity(activityIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
			}
		});

		cat1Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Capacitor");
				MainActivity.this.startActivity(activityIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
			}
		});

		cat2Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Inductor");
				MainActivity.this.startActivity(activityIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Pair<View, String> a0 = Pair.create(findViewById(R.id.searchBar), "search_bar");
				ActivityOptionsCompat optns = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, a0);
				Intent activityIntent = new Intent(MainActivity.this, SearchActivity.class);
				MainActivity.this.startActivity(activityIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
				//MainActivity.this.startActivity(activityIntent, optns.toBundle());
			}
		});

	}

	protected void onStop() {
		super.onStop();
	}

	protected void onResume() {
		super.onResume();
		/* Update top picks */
		setUpTopPicks();
		Log.d(TAG, "In onResume()");
	}

}
