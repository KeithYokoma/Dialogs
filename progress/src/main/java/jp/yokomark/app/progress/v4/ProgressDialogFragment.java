package jp.yokomark.app.progress.v4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import jp.yokomark.app.progress.Utils;

/**
 * @author KeithYokoma
 */
public class ProgressDialogFragment extends DialogFragment {
    public static final String TAG = ProgressDialogFragment.class.getSimpleName();
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "title";
    private static final String ARGS_INDETERMINATE = "indeterminate";
    private static final String ARGS_CANCELABLE = "cancelable";
    private static final String ARGS_MAX = "max";
    private static final String ARGS_STYLE = "style";

    public ProgressDialogFragment() {}

    public static void postDismiss(FragmentActivity activity) {
        postDismiss(activity.getSupportFragmentManager());
    }

    public static void postDismiss(final FragmentManager manager) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                dismiss(manager);
            }
        });
    }

    public static void dismiss(FragmentActivity activity) {
        dismiss(activity.getSupportFragmentManager());
    }

    public static void dismiss(FragmentManager manager) {
        DialogFragment fragment = (DialogFragment) manager.findFragmentByTag(TAG);
        if (fragment == null) {
            return;
        }
        fragment.dismiss();
    }

    @SuppressLint("Override")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int style = args.getInt(ARGS_STYLE);
        String title = Utils.getString(getActivity(), args, ARGS_TITLE);
        String message = Utils.getString(getActivity(), args, ARGS_MESSAGE);
        int max = args.getInt(ARGS_MAX);
        boolean cancelable = args.getBoolean(ARGS_CANCELABLE);
        boolean indeterminate = args.getBoolean(ARGS_INDETERMINATE);

        ProgressDialog dialog = new ProgressDialog(getActivity(), style);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setMax(max);
        dialog.setCancelable(cancelable);
        dialog.setIndeterminate(indeterminate);
        dialog.setOnCancelListener(this);
        return dialog;
    }

    public void show(FragmentManager manager) {
        show(manager, TAG);
    }

    public void show(FragmentTransaction transaction) {
        show(transaction, TAG);
    }

    public void updateProgress(int progress) {
        ProgressDialog dialog = (ProgressDialog) getDialog();
        if (dialog == null) {
            return;
        }
        dialog.setProgress(progress);
    }

    public void updateMax(int max) {
        ProgressDialog dialog = (ProgressDialog) getDialog();
        if (dialog == null) {
            return;
        }
        dialog.setMax(max);
    }

    public @Nullable TextView getMessageView() {
        ProgressDialog dialog = (ProgressDialog) getDialog();
        if (dialog == null) {
            return null;
        }
        return (TextView) dialog.findViewById(android.R.id.message);
    }

    public static interface ProgressDialogFragmentFactory<D extends ProgressDialogFragment> {
        public D newInstance();
    }

    public static class DefaultProgressDialogFragmentFactory implements ProgressDialogFragmentFactory<ProgressDialogFragment> {
        @Override
        public ProgressDialogFragment newInstance() {
            return new ProgressDialogFragment();
        }
    }

    public static class Builder {
        protected final Bundle mArgs;
        private final ProgressDialogFragmentFactory<?> mFactory;

        public Builder() {
            this(new DefaultProgressDialogFragmentFactory());
        }

        public Builder(ProgressDialogFragmentFactory<?> factory) {
            mArgs = new Bundle();
            mFactory = factory;
        }

        public ProgressDialogFragment create() {
            validate();
            ProgressDialogFragment instance = mFactory.newInstance();
            instance.setArguments(mArgs);
            return instance;
        }


        public Builder setTitle(Context context, @StringRes int titleRes) {
            return setTitle(context.getString(titleRes));
        }

        public Builder setTitle(String tilte) {
            mArgs.putString(ARGS_TITLE, tilte);
            return this;
        }

        public Builder setMessage(Context context, @StringRes int messageRes) {
            return setMessage(context.getString(messageRes));
        }

        public Builder setMessage(String message) {
            mArgs.putString(ARGS_MESSAGE, message);
            return this;
        }

        public Builder setIndeterminate(boolean indeterminate) {
            mArgs.putBoolean(ARGS_INDETERMINATE, indeterminate);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mArgs.putBoolean(ARGS_CANCELABLE, cancelable);
            return this;
        }

        public Builder setMax(int max) {
            mArgs.putInt(ARGS_MAX, max);
            return this;
        }

        public Builder setStyle(@Style int style) {
            mArgs.putInt(ARGS_STYLE, style);
            return this;
        }

        private void validate() {
            if (!mArgs.containsKey(ARGS_MESSAGE)) {
                throw new IllegalStateException("progress message should be specified.");
            }
        }
    }

    @IntDef({ProgressDialog.STYLE_HORIZONTAL, ProgressDialog.STYLE_SPINNER})
    public static @interface Style {}
}
