package ac.auckland.componentcompanion;

import android.util.Log;

public class Item {
	private static final String TAG = "ac.auckland.componentcampanion.Item.java";
	private static int nextID = 0;

	private int id;
	private String name; /* e.g. Resistor, Capacitor, Inductor */
	private String make;
	private String model;
	private String mount;
	private float price; /* in New Zealand Cenets (1/100 of a NZD) */
	private float value; /* Value of component in ohms, micro farads etc. */
	private String preview; /* URI to item image */

	public Item(String name, String make, String model, float price, String mount, float value) {
		this.id = Item.nextID++;

		this.name = name;
		this.make = make;
		this.model = model;
		this.price = price;
		this.mount = mount;
		this.value = value;
		this.preview = "dataprovider/".concat(model.concat(".jpg"));
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public float getPrice() {
		return price;
	}

	public String getPreview() {
		return preview;
	}
}

