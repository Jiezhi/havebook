package io.github.jiezhi.havebook.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.fragment.BooksFragment;
import io.github.jiezhi.havebook.provider.MySuggestionProvider;

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

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragment = new BooksFragment();
        fragment.setArguments(bundle);
        fragment.setToolbar(toolbar);
        getFragmentManager().beginTransaction().replace(R.id.search_frame, fragment).commit();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(bundle.getString("keyword"));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            bundle.putString("keyword", query);
            initView();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
