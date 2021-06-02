package ac.auckland.componentcompanion;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

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
}
