package io.github.jiezhi.havebook.activity;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;

import java.util.List;
import java.util.Map;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.app.MySingleton;
import io.github.jiezhi.havebook.db.MySQLiteHelper;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.Constants;

/**
 * Created by jiezhi on 5/24/16.
 * Function:
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected RequestQueue requestQueue;
    private Context context;
    private MySQLiteHelper sqLiteHelper;

    protected static final String BOOK_RELATED = "bookRelated";
    protected static final int ADD_LIKED_BOOK = 1;
    protected static final int DEL_LIKED_BOOK = ADD_LIKED_BOOK << 1;
    protected static final int QUERY_LIKED_BOOK = DEL_LIKED_BOOK << 1;

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            DoubanBook book;
            switch (msg.arg1) {
                case ADD_LIKED_BOOK:
                    book = (DoubanBook) msg.getData().getSerializable(BOOK_RELATED);
                    ContentValues cv = getContentValuesFromBook(book);
                    // TODO: 6/3/16 Change sqlitehelper to singleton
                    sqLiteHelper = new MySQLiteHelper(context);
                    sqLiteHelper.insert(cv);
                    break;
                case DEL_LIKED_BOOK:
                    book = (DoubanBook) msg.getData().getSerializable(BOOK_RELATED);
                    sqLiteHelper.delete(book.getId());
                    break;
                case QUERY_LIKED_BOOK:

                    break;
            }
            if (sqLiteHelper != null) sqLiteHelper.close();
            return true;
        }
    });

    private ContentValues getContentValuesFromBook(DoubanBook book) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.Book.ID, book.getId());
        cv.put(Constants.Book.AUTHOR_INTRO, book.getAuthor_intro());
        cv.put(Constants.Book.BINDING, book.getBinding());
        cv.put(Constants.Book.CATALOG, book.getCatalog());
        cv.put(Constants.Book.IMAGE, book.getImage());
        cv.put(Constants.Book.LARGE_IMG, book.getImg_large());
        cv.put(Constants.Book.MEDIUM_IMG, book.getImg_medium());
        cv.put(Constants.Book.SMALL_IMG, book.getImg_small());
        cv.put(Constants.Book.ISBN10, book.getIsbn10());
        cv.put(Constants.Book.ISBN13, book.getIsbn13());
        cv.put(Constants.Book.ORIGIN_TITLE, book.getOrigin_title());
        cv.put(Constants.Book.PAGES, book.getPages());
        cv.put(Constants.Book.PRICE, book.getPrice());
        cv.put(Constants.Book.PUBDATE, book.getPubdate());
        cv.put(Constants.Book.PUBLISHER, book.getPublisher());
        cv.put(Constants.Book.SUBTITLE, book.getSubtitle());
        cv.put(Constants.Book.TITLE, book.getTitle());
        cv.put(Constants.Book.URL, book.getUrl());
        cv.put(Constants.Book.ALT, book.getAlt());
        cv.put(Constants.Book.ALT_TITLE, book.getAlt_title());

        // save authors, translators and tags into one column separately
        String[] authors = book.getAuthors();
        if (authors != null) {
            StringBuilder sb = new StringBuilder(authors.length);
            for (String author : authors) {
                sb.append(author).append(Constants.Others.SEPERATE);
            }
            cv.put(Constants.Book.AUTHOR, sb.toString());
        }
        String[] translators = book.getTranslator();
        if (translators != null) {
            StringBuilder sb = new StringBuilder(translators.length);
            for (String translator : translators) {
                sb.append(translator).append(Constants.Others.SEPERATE);
            }
            cv.put(Constants.Book.TRANSLATOR, sb.toString());
        }

        List<Map<String, String>> tags = book.getTags();
        if (tags != null) {
            int tagCount = tags.size();
            StringBuilder sb = new StringBuilder(tagCount);
            for (Map<String, String> tag : tags) {
                sb.append(tag.get("title")).append(Constants.Others.SEPERATE);
            }
            cv.put(Constants.Book.TAGS, sb.toString());
        }

        cv.put(Constants.Book.RATING_AVERAGE, book.getRatingAverage());
        cv.put(Constants.Book.RATING_MAX, book.getRatingMax());
        cv.put(Constants.Book.RATING_MIN, book.getRatingMin());
        cv.put(Constants.Book.RATING_NUMRATERS, book.getRatingNum());
        return cv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scan) {
            startActivity(new Intent(BaseActivity.this, ScannerActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
