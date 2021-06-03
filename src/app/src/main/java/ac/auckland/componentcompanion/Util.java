package ac.auckland.componentcompanion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Util {
	private static final String TAG = "componentcompanion.util";

	public static Drawable drawableFromAsset(Context context, String path) {
		try {
			InputStream istream = context.getAssets().open(path);
			return Drawable.createFromStream(istream, null);
		} catch (Exception IoException) {
			Log.e(TAG, "Error opening asset: ".concat(path));
		}
		return null;
	}

	public static void writeStr2File(String s, FileOutputStream f) {
		for (int i = 0; i < s.length(); ++i) {
			try {
				f.write(s.charAt(i));
			} catch (Exception e) {
				Log.e(TAG, "Error writing string to file: ".concat(s));
			}
		}
	}

	/* Returns the line exlcuding the new line character (no '\n') */
	public static String readLineFromFile(FileInputStream f) {
		String s = "";
		byte b;
		byte[] barray = new byte[1];
		int read = 0;
		do {
			try {
				read = f.read();
				if (read == -1)
					break;
				else
					b = (byte) read;
			} catch (Exception e) {
				return s;
			}
			if (b == '\n') {
				return s;
			} else {
				barray[0] = b;
				try {
					s = s.concat(new String(barray, "UTF-8"));
				} catch (Exception e) {
					Log.e(TAG, "Error reading line from file!");
				}
			}
		} while (true);

		return s;
	}
}
