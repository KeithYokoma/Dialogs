package jp.yokomark.app;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import jp.yokomark.app.progress.v4.ProgressDialogFragment;

public class SampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    public void onViewProgressClick(View v) {
        new ProgressDialogFragment.Builder()
                .setMessage("Now Loading...")
                .setCancelable(true)
                .setIndeterminate(true)
                .create()
                .show(getSupportFragmentManager());
    }

    public void onViewCustomProgressClick(View v) {
        new MyProgressDialogFragment.MyBuilder()
                .setAnotherMessage("sample")
                .setMessage("Now Loading...")
                .setCancelable(true)
                .setIndeterminate(true)
                .create()
                .show(getSupportFragmentManager());
    }

    // This is a customized progress dialog fragment
    public static class MyProgressDialogFragment extends ProgressDialogFragment {
        public MyProgressDialogFragment() {}

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle args = getArguments();
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.setTitle(args.getString("another_message"));
            return dialog;
        }

        public static class MyBuilder extends Builder {
            public MyBuilder() {
                super(new MyFactory());
            }

            public Builder setAnotherMessage(String message) {
                mArgs.putString("another_message", message);
                return this;
            }
        }

        static class MyFactory implements ProgressDialogFragmentFactory<MyProgressDialogFragment> {

            @Override
            public MyProgressDialogFragment newInstance() {
                return new MyProgressDialogFragment();
            }
        }
    }
}
