package io.github.jiezhi.havebook.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.db.MySQLiteHelper;
import io.github.jiezhi.havebook.fragment.BooksFragment;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.provider.MySuggestionProvider;
import io.github.jiezhi.havebook.utils.Constants;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";

    private BooksFragment fragment;
    private Bundle bundle;
    private String barTitle = "Default";
    private List<DoubanBook> doubanBooks;
    private int state;

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
        if (state == 1) { // search
            // TODO: 6/5/16  
        } else if (state == 2){ // collect
            fragment.setDoubanBooks(doubanBooks);
        }
        fragment.setToolbar(toolbar);
        getFragmentManager().beginTransaction().replace(R.id.search_frame, fragment).commit();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(barTitle);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // search book
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, query);
            state = 1;
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            barTitle = "Search Result";
            bundle.putString(Constants.Action.ACTION, Constants.Action.SHOW_SEARCH);
            bundle.putString("keyword", query);
            initView();
        } else if (Constants.Action.SHOW_COLLECT.equals(intent.getAction())) {
            // show collections
            Log.d(TAG, "show collect");
            state = 2;
            MySQLiteHelper dbHelper = new MySQLiteHelper(this);
            Cursor cursor = dbHelper.query();
            DoubanBook book;
            doubanBooks = new ArrayList<>();
            while (cursor.moveToNext()) {
                book = new DoubanBook(cursor);
                doubanBooks.add(book);
            }
            // pass the books to the fragment
            cursor.close();
            dbHelper.close();
            barTitle = "My Collections";
            bundle.putString(Constants.Action.ACTION, Constants.Action.SHOW_COLLECT);
            initView();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
