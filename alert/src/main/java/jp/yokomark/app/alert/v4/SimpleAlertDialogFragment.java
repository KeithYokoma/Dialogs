package jp.yokomark.app.alert.v4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author KeithYokoma
 */
public class SimpleAlertDialogFragment extends DialogFragment {
    public static final String TAG = SimpleAlertDialogFragment.class.getSimpleName();
    private static final String ARGS_ICON = "icon";
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "title";
    private static final String ARGS_POSITIVE = "positive";
    private static final String ARGS_NEGATIVE = "negative";
    private static final String ARGS_NEUTRAL = "neutral";
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
        int iconRes = args.getInt(ARGS_ICON, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(args.getInt(ARGS_TITLE))
                .setMessage(args.getInt(ARGS_MESSAGE));

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

    public void show(FragmentManager manager) {
        super.show(manager, TAG);
    }

    public int show(FragmentTransaction transaction) {
        return super.show(transaction, TAG);
    }

    public static class Builder {
        private final Bundle mBundle;

        public Builder() {
            mBundle = new Bundle();
        }

        public Builder setIcon(@DrawableRes int iconRes) {
            mBundle.putInt(ARGS_ICON, iconRes);
            return this;
        }

        public Builder setTitle(Context context, @StringRes int title) {
            return setTitle(context.getString(title));
        }

        public Builder setTitle(String title) {
            mBundle.putString(ARGS_TITLE, title);
            return this;
        }

        public Builder setMessage(Context context, @StringRes int message) {
            return setMessage(context.getString(message));
        }

        public Builder setMessage(String message) {
            mBundle.putString(ARGS_MESSAGE, message);
            return this;
        }

        public Builder setPositiveButton(Context context, @StringRes int positive) {
            return setPositiveButton(context.getString(positive));
        }

        public Builder setPositiveButton(String positive) {
            mBundle.putString(ARGS_POSITIVE, positive);
            return this;
        }

        public Builder setNegativeButton(Context context, @StringRes int negative) {
            return setNegativeButton(context.getString(negative));
        }

        public Builder setNegativeButton(String negative) {
            mBundle.putString(ARGS_NEGATIVE, negative);
            return this;
        }

        public Builder setNeutralButton(Context context, @StringRes int neutral) {
            return setNeutralButton(context.getString(neutral));
        }

        public Builder setNeutralButton(String neutral) {
            mBundle.putString(ARGS_NEUTRAL, neutral);
            return this;
        }

        public SimpleAlertDialogFragment create() {
            SimpleAlertDialogFragment fragment = new SimpleAlertDialogFragment();
            fragment.setArguments(mBundle);
            return fragment;
        }

        public <F extends Fragment & SelectionCallback> SimpleAlertDialogFragment create(F callback, int requestCode) {
            SimpleAlertDialogFragment fragment = new SimpleAlertDialogFragment();
            fragment.setArguments(mBundle);
            fragment.setTargetFragment(callback, requestCode);
            return fragment;
        }
    }

    public static interface SelectionCallback {
        public void onPositive();
        public void onNegative();
        public void onNeutral();
    }
}