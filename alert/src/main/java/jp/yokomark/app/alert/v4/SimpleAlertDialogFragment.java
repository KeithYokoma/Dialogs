package jp.yokomark.app.alert.v4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * @author KeithYokoma
 */
public class SimpleAlertDialogFragment extends DialogFragment {
    private static final String ARGS_ICON = "icon";
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "title";
    private static final String ARGS_POSITIVE = "positive";
    private static final String ARGS_NEGATIVE = "negative";
    private static final String ARGS_NEUTRAL = "neutral";
    private static final String ARGS_THEME = "theme";
    private SelectionCallback mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof SelectionCallback) {
            mCallback = (SelectionCallback) activity; // yes, activity can handle the callback.
        } else if (getTargetFragment() instanceof SelectionCallback) {
            mCallback = (SelectionCallback) getTargetFragment(); // yes, fragment can be handle the callback.
        } else {
            throw new IllegalStateException("activity or fragment should implement SelectionCallback.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        boolean hasPositive = args.containsKey(ARGS_POSITIVE);
        boolean hasNegative = args.containsKey(ARGS_NEGATIVE);
        boolean hasNeutral = args.containsKey(ARGS_NEUTRAL);
        int theme = args.getInt(ARGS_THEME, 0);
        int iconRes = args.getInt(ARGS_ICON, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), theme)
                .setTitle(args.getString(ARGS_TITLE))
                .setMessage(args.getString(ARGS_MESSAGE));

        if (iconRes != -1) {
            builder.setIcon(args.getInt(ARGS_ICON));
        }

        if (hasPositive) {
            builder.setPositiveButton(args.getString(ARGS_POSITIVE), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mCallback == null) {
                        return;
                    }
                    mCallback.onPositive();
                }
            });
        }
        if (hasNegative) {
            builder.setNegativeButton(args.getString(ARGS_NEGATIVE), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mCallback == null) {
                        return;
                    }
                    mCallback.onNegative();
                }
            });
        }
        if (hasNeutral) {
            builder.setNeutralButton(args.getString(ARGS_NEUTRAL), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mCallback == null) {
                        return;
                    }
                    mCallback.onNeutral();
                }
            });
        }
        return builder.create();
    }

    public static interface SelectionCallback {
        public void onPositive();
        public void onNegative();
        public void onNeutral();
    }
}