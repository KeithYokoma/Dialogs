package jp.yokomark.app;

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
}
