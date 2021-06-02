package ac.auckland.componentcompanion;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class itemDetailsActivity extends AppCompatActivity {
    DataLoader dloader = new DataLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Back button remap, goes to previous activity
        OnBackPressedCallback backButtonCall = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backButtonCall);

        getIncomingIntent();
    }
    /* Catch other extras into the conditional to ensure it isn't retrieving intents that don't exist */
    private void getIncomingIntent() {

//        if (getIntent().hasExtra("itemPrice")) {
//            String itemMake = getIntent().getStringExtra("itemMake");
//            String itemValue = getIntent().getStringExtra("itemValue");
//            String itemPrice = getIntent().getStringExtra("itemPrice");
//            String itemUnit = getIntent().getStringExtra("itemUnit");
//            String imageUrl = getIntent().getStringExtra("imageUrl");

        if (getIntent().hasExtra("itemID")) {


            int itemID = getIntent().getIntExtra("itemID",0);

            Item item = dloader.getItem(itemID);

            String itemMake = item.getMake();
            String itemValue = Float.toString(item.getValue());
            String itemPrice = Float.toString(item.getPrice());
            String itemUnit = item.getUnit();
            String imageUrl = item.getPreview();



            setImageDetails(imageUrl, itemMake, itemValue, itemPrice, itemUnit);

        }
    }

    /* Function to set the image details from the list view */
    private void setImageDetails(String imageUrl, String itemMake, String itemValue, String itemPrice, String itemUnit) {
        TextView price = findViewById(R.id.unitPrice);
        TextView make = findViewById(R.id.itemMake);
        TextView value = findViewById(R.id.itemValue);
        ImageView image = findViewById(R.id.image);


        make.setText("Make: " + itemMake);
        value.setText("Value: " + itemValue.concat(itemUnit));
        price.setText("Unit Price: " + itemPrice.concat("¢"));

        image.setImageDrawable(Util.drawableFromAsset(this, imageUrl));

    }
}