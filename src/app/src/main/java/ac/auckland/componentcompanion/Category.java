package ac.auckland.componentcompanion;

import java.util.ArrayList;

public class Category {
	private String name; /* Resistor, Capacitor, Inductor */
	private ArrayList<Item> items;

	public Category(String name) {
		this.name = name;
		this.items = null;
	}

	public Category(String name, ArrayList<Item> items) {
		this.name = name;
		this.items = items;
	}

	public void addItem(Item i) {
		this.items.add(i);
	}

	public void addItem(String name, String make, String model, float price, float value) {
		this.addItem(new Item(name, make, model, price, value));
	}

	public String getCategoryName() {
		return this.name;
	}

	public int getCategoryLength() {
		return this.items.size();
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}
}
