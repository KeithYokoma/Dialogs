package jp.yokomark.app.progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author KeithYokoma
 */
public class Utils {
    private Utils() {}

    public static String getString(Context context, @NonNull Bundle args, @NonNull String key) {
        int res = args.getInt(key, 0);
        if (res == 0) {
            return args.getString(key);
        } else {
            return context.getString(res);
        }
    }
}
