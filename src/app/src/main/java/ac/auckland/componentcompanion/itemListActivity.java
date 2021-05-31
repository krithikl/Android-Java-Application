package ac.auckland.componentcompanion;

import android.content.Intent;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class itemListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);

		Intent startActivityIntent = getIntent();
		String category = startActivityIntent.getStringExtra("CATEGORY");

		((TextView) findViewById(R.id.category_text)).setText(category);

		// Back button remap, goes to previous activity
		OnBackPressedCallback backButtonCall = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				// set an exit transition
				finish();
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
		};
		getOnBackPressedDispatcher().addCallback(this, backButtonCall);

	}

}