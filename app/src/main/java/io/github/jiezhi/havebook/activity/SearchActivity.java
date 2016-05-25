package io.github.jiezhi.havebook.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.fragment.BooksFragment;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private BooksFragment fragment;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        bundle = new Bundle();
        handleIntent(getIntent());


        fragment = new BooksFragment();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.search_frame, fragment).commit();

    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        handleIntent(intent);
//    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, query);
            bundle.putString("keyword", query);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
