package jp.yokomark.app.alert;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.os.Build;

/**
 * @author KeithYokoma
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlertDialogFragment extends DialogFragment {
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "title";
    private static final String ARGS_POSITIVE = "positive";
    private static final String ARGS_NEGATIVE = "negative";
}
