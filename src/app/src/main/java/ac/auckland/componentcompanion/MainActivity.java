package ac.auckland.componentcompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

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
			viewholder.imageButton.setImageResource(getResources().getIdentifier(imageName, "drawable", getPackageName()));
		}

		public int getItemCount() {
			return items.size();
		}

		/* Constructor initialises the data we are going to dislpay */
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

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
	}


}
