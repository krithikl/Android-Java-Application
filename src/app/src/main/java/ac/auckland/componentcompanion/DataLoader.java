package ac.auckland.componentcompanion;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class DataLoader {
	private static final String TAG = "DataLoader";

	private static int[] views = new int[30];
	private static final String viewPath = "views.txt";
	private Context context;
	private ArrayList<Item> items = new ArrayList<Item>();
	private Category category0 = new Category("Resistors");
	private Category category1 = new Category("Capacitors");
	private Category category2 = new Category("Inductors");


	public DataLoader(Context context) {
		this.context = context;

		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = context.openFileInput(DataLoader.viewPath);
			Log.d(TAG, "File ".concat(DataLoader.viewPath).concat(" found - reading it in to array"));
			for (int i = 0; i < 30; ++i) {
				String s = Util.readLineFromFile(fin);
				DataLoader.views[i] = Integer.parseInt(s);
				Log.d(TAG, "Item ".concat(Integer.toString(i)).concat(" : Views ").concat(Integer.toString(DataLoader.views[i])));
			}
		} catch (Exception e) {
			/* File does not exist, this is the first run */
			/* Create it, and fill it with zeros */
			Log.d(TAG, "File ".concat(DataLoader.viewPath).concat(" does not exist - creating it"));
			try {
				fout = context.openFileOutput(DataLoader.viewPath, Context.MODE_PRIVATE);
			} catch (Exception g) {
				Log.e(TAG, "DataLoader() Exception (catch)!");
			}
			for (int i = 0; i < DataLoader.views.length; ++i) {
				DataLoader.views[i] = 0;
				Util.writeStr2File(Integer.toString(0).concat("\n"), fout);
			}
			Log.d(TAG, "File ".concat(DataLoader.viewPath).concat(" has zeros written to it"));
		} finally {
			try {
				if (fin != null) fin.close();
				if (fout != null) fout.close();
			} catch (Exception e) {
				Log.e(TAG, "DataLoader() Exception (finally)!");
			}
		}


		String[][] rawData = DataProvider.getData();

		/* Adds item parameters into specified category. Cat0 = Resistors, cat1 = capacitors, cat2 = inductors */
		for (int i = 0; i < 10; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4], Float.parseFloat(rawData[i][5]), rawData[i][6]));
			this.category0.addItem(this.items.get(this.items.size() - 1));
		}
		for (int i = 10; i < 20; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4], Float.parseFloat(rawData[i][5]), rawData[i][6]));
			this.category1.addItem(this.items.get(this.items.size() - 1));
		}
		for (int i = 20; i < 30; ++i) {
			this.items.add(new Item(rawData[i][0], rawData[i][1], rawData[i][2], Float.parseFloat(rawData[i][3]), rawData[i][4], Float.parseFloat(rawData[i][5]), rawData[i][6]));
			this.category2.addItem(this.items.get(this.items.size() - 1));
		}
	}

	public ArrayList<Item> getItems() {
		return this.items;
	}

	public Item getItem(int index) {
		return this.items.get(index);
	}

	public Item getItem(String model) {
		for (Item i : this.items) {
			if (i.getModel().equals(model))
				return i;
		}
		return null;
	}

	/* Method call to return the number of views an item has*/
	public int getItemViews(int index) {
		return DataLoader.views[index];
	}

	/* Fetches top 6 items with the most views and appends to top picks section*/
	public ArrayList<Item> getTopSix() {
		ArrayList<Item> items = new ArrayList<Item>(0);
		int[] alreadyCounted = new int[5];
		int max = 0;
		int maxIdx = 0;

		/* Most popular */
		for (int i = 0; i < 30; ++i) {
			if (this.views[i] >= max) {
				alreadyCounted[0] = i;
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		max = 0;
		/* Second Most popular */
		for (int i = 0; i < 30; ++i) {
			/* Don't add the same item twice */
			if (i == alreadyCounted[0])
				continue;

			if (this.views[i] >= max) {
				alreadyCounted[1] = i;
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		max = 0;
		/* Third Most popular */
		for (int i = 0; i < 30; ++i) {
			/* Don't add the same item twice */
			if (i == alreadyCounted[0] || i == alreadyCounted[1])
				continue;

			if (this.views[i] >= max) {
				alreadyCounted[2] = i;
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		max = 0;
		/* Fourth Most popular */
		for (int i = 0; i < 30; ++i) {
			/* Don't add the same item twice */
			if (i == alreadyCounted[0] || i == alreadyCounted[1] || i == alreadyCounted[2])
				continue;

			if (this.views[i] >= max) {
				alreadyCounted[3] = i;
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		max = 0;
		/* Fifth Most popular */
		for (int i = 0; i < 30; ++i) {
			/* Don't add the same item twice */
			if (i == alreadyCounted[0] || i == alreadyCounted[1] || i == alreadyCounted[2] || i == alreadyCounted[3])
				continue;

			if (this.views[i] >= max) {
				alreadyCounted[4] = i;
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		max = 0;
		/* Sixth Most popular */
		for (int i = 0; i < 30; ++i) {
			/* Don't add the same item twice */
			if (i == alreadyCounted[0] || i == alreadyCounted[1] || i == alreadyCounted[2] || i == alreadyCounted[3] || i == alreadyCounted[4])
				continue;

			if (this.views[i] >= max) {
				max = this.views[i];
				maxIdx = i;
			}
		}

		items.add(this.items.get(maxIdx));
		return items;
	}

	/* Reset all item views to zero */
	public void resetViews() {
		FileOutputStream fout;
		Log.d(TAG, "Resetting all item views to zero");
		try {
			fout = this.context.openFileOutput(DataLoader.viewPath, Context.MODE_PRIVATE);
			for (int i = 0; i < DataLoader.views.length; ++i) {
				DataLoader.views[i] = 0;
				Util.writeStr2File(Integer.toString(0).concat("\n"), fout);
			}
			fout.close();
		} catch (Exception g) {
			Log.e(TAG, "DataLoader() Exception (resetViews())!");
		}
	}

	public void addItemView(int index) {
		++(DataLoader.views[index]);
		/* Write this data out to file */
		FileOutputStream fout = null;
		try {
			fout = this.context.openFileOutput(DataLoader.viewPath, Context.MODE_PRIVATE);
			for (int i = 0; i < 30; ++i) {
				Util.writeStr2File(Integer.toString(DataLoader.views[i]).concat("\n"), fout);
				Log.d(TAG, "Item ".concat(Integer.toString(i)).concat(" : Views ").concat(Integer.toString(DataLoader.views[i])));
			}
			fout.close();
		} catch (Exception e) {
			Log.e(TAG, "addItemView() Exception!");
		}

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
			case "Resistor":
				return category0;
			case "Capacitor":
				return category1;
			case "Inductor":
				return category2;
			default:
				return null;
		}
	}
}
