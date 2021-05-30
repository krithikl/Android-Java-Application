package ac.auckland.componentcompanion;

import java.util.ArrayList;

public class DataLoader {
	ArrayList<Item> items = new ArrayList<Item>();
	Category category0 = new Category("Resistors");
	Category category1 = new Category("Capacitors");
	Category category2 = new Category("Inductors");


	public DataLoader() {
		String[][] rawData = DataProvider.getData();

		for (int i = 0; i < rawData[0].length; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4]));
			this.category0.addItem(this.items.get(this.items.size() - 1));
		}
		for (int i = 0; i < rawData[1].length; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4]));
			this.category1.addItem(this.items.get(this.items.size() - 1));
		}
		for (int i = 0; i < rawData[2].length; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4]));
			this.category2.addItem(this.items.get(this.items.size() - 1));
		}
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

	public Item getItem(String model) {
		for (Item i : this.items) {
			if (i.getModel().equals(model))
				return i;
		}
		return null;
	}

	public ArrayList<Category> getCategories() {
		ArrayList<Category> c = new ArrayList<Category>();
		c.add(this.category0);
		c.add(this.category1);
		c.add(this.category2);
		return c;
	}

	public Category getCategory(String name) {
		switch (name) {
			case "Resistors":
				return category0;
			case "Capacitors":
				return category1;
			case "Inductors":
				return category2;
			default:
				return null;
		}
	}
}
