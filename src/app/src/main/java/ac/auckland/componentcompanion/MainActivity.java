package ac.auckland.componentcompanion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.*;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	private String TAG = "ac.auckland.componentcompanion.MainActivity";

	/* Define the behaviour of the recycler view */
	private class TopPicksAdapter extends RecyclerView.Adapter<TopPicksAdapter.ViewHolder> {
		public ArrayList<Item> items = null;

		/* Define the behaviour of each recycler view item */
		private class ViewHolder extends RecyclerView.ViewHolder {
			private ImageButton imageButton;

			public ViewHolder(View itemView) {
				super(itemView);
				imageButton = itemView.findViewById(R.id.image);

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
			viewholder.imageButton.setImageDrawable(Util.drawableFromAssest(MainActivity.this, imageName));
		}

		public int getItemCount() {
			return items.size();
		}

		/* Constructor initialises the data we are going to display */
		public TopPicksAdapter(ArrayList<Item> items) {
			this.items = items;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DataLoader dloader = new DataLoader();
		ArrayList<Item> topPicks = new ArrayList<Item>(6);

		RecyclerView recyclerView = findViewById(R.id.top_picks);
		TopPicksAdapter adapter = new TopPicksAdapter(topPicks);

		topPicks.add(dloader.getItem(1));
		topPicks.add(dloader.getItem(12));
		topPicks.add(dloader.getItem(14));
		topPicks.add(dloader.getItem(17));
		topPicks.add(dloader.getItem(24));
		topPicks.add(dloader.getItem(28));

		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
		recyclerView.setAdapter(adapter);
		/* Remember the layout is reversed */
		recyclerView.scrollToPosition(topPicks.size() - 1);

		Button cat0Btn = findViewById(R.id.cat0Btn);
		Button cat1Btn = findViewById(R.id.cat1Btn);
		Button cat2Btn = findViewById(R.id.cat2Btn);
		Button searchButton = findViewById(R.id.searchBar);

		cat0Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Resistor");
				startActivity(activityIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
			}
		});

		cat1Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Capacitor");
				startActivity(activityIntent);
			}
		});

		cat2Btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, ItemListActivity.class);
				activityIntent.putExtra("CATEGORY", "Inductor");
				startActivity(activityIntent);
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent activityIntent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(activityIntent);
				overridePendingTransition(0,0);
			}
		});
	}

}
