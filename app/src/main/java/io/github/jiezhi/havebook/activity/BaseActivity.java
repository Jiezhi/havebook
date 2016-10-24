package io.github.jiezhi.havebook.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
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

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.app.MySingleton;

/**
 * Created by jiezhi on 5/24/16.
 * Function:
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected RequestQueue requestQueue;
    private Context context;
    protected ProgressDialog dialog;

    protected static final String BOOK_RELATED = "bookRelated";
    protected static final int ADD_LIKED_BOOK = 1;
    protected static final int DEL_LIKED_BOOK = ADD_LIKED_BOOK << 1;
    protected static final int QUERY_LIKED_BOOK = DEL_LIKED_BOOK << 1;

    protected Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            DoubanBook book;
//            if (sqLiteHelper == null)
//                sqLiteHelper = new MySQLiteHelper2(context);
//            switch (msg.arg1) {
//                case ADD_LIKED_BOOK:
//                    book = (DoubanBook) msg.getData().getSerializable(BOOK_RELATED);
//                    ContentValues cv = getContentValuesFromBook(book);
//                    // TODO: 6/3/16 Change sqlitehelper to singleton
//                    sqLiteHelper.insert(cv);
//                    break;
//                case DEL_LIKED_BOOK:
//                    book = (DoubanBook) msg.getData().getSerializable(BOOK_RELATED);
//                    sqLiteHelper.delete(book.getId());
//                    break;
//                case QUERY_LIKED_BOOK:
//
//                    break;
//            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

        dialog = new ProgressDialog(this);

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
//        if (sqLiteHelper != null) sqLiteHelper.close();
    }
}
