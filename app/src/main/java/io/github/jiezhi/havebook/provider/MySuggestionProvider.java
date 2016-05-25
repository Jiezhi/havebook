package io.github.jiezhi.havebook.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by jiezhi on 5/26/16.
 * Function:
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider{
    private static final String TAG = "MySuggestionProvider";

    public final static String AUTHORITY = "io.github.jiezhi.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
