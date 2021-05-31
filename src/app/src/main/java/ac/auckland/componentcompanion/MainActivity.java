package ac.auckland.componentcompanion;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        Button cat0Btn = findViewById(R.id.cat0Btn);

        cat0Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent catActivity = new Intent(MainActivity.this, itemListActivity.class);
                startActivity(catActivity);
            }
        });

        SearchView searchBar = findViewById(R.id.searchBar);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                // Filter results in here with adapter
                return false;
            }
        });




    }
}