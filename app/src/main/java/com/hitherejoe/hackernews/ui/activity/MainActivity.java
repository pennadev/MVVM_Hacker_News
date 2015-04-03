package com.hitherejoe.hackernews.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hitherejoe.hackernews.HackerNewsApplication;
import com.hitherejoe.hackernews.R;
import com.hitherejoe.hackernews.data.remote.AnalyticsHelper;
import com.hitherejoe.hackernews.ui.fragment.StoriesFragment;
import com.hitherejoe.hackernews.util.RateUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addStoriesFragment();
        if (HackerNewsApplication.get().getDataManager().getPreferencesHelper().shouldShowRateDialog()) {
            RateUtils.showRateDialog(this, mOnRateDialogClickListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AnalyticsHelper.trackAboutMenuItemClicked();
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_bookmarks:
                AnalyticsHelper.trackBookmarksMenuItemClicked();
                startActivity(new Intent(this, BookmarksActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addStoriesFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new StoriesFragment())
                .commit();
    }

    private DialogInterface.OnClickListener mOnRateDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case -2:
                    HackerNewsApplication.get().getDataManager().getPreferencesHelper().putDialogFlag();
                    break;
                case -1:
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    HackerNewsApplication.get().getDataManager().getPreferencesHelper().putDialogFlag();
                    break;
            }
            dialog.dismiss();
        }
    };

}
