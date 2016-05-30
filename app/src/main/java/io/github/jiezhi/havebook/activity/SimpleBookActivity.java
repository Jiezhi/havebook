package io.github.jiezhi.havebook.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONObject;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.Constants;
import io.github.jiezhi.havebook.utils.JsonUtils;

/**
 * Created by jiezhi on 5/26/16.
 * Function:
 */
public class SimpleBookActivity extends BaseActivity {
    private static final String TAG = "SimpleBookActivity";

    private ImageView bookCover;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;
    private TextView bookPublisher;
    private TextView bookCatalogTitle;
    private ExpandableTextView summaryExpandableTextView;
    private ExpandableTextView catalogExpandableTextView;

    private AppBarLayout appbarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        String isbn = getIntent().getStringExtra("isbn");
        if (isbn == null) isbn = "9787115416292";
        Log.d(TAG, "isbn: " + isbn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(isbn);

        initView();
        getBookInfoByISBN(isbn);
    }

    private void initView() {
        bookCover = (ImageView) findViewById(R.id.book_cover);
        bookTitle = (TextView) findViewById(R.id.book_title);
        bookAuthor = (TextView) findViewById(R.id.book_author);
        bookRating = (TextView) findViewById(R.id.book_rating);
        bookPublisher = (TextView) findViewById(R.id.book_publisher);
        bookCatalogTitle = (TextView) findViewById(R.id.book_catalog_title);
        appbarLayout = (AppBarLayout) findViewById(R.id.appbar);

        summaryExpandableTextView = (ExpandableTextView) findViewById(R.id.summary_text);
        catalogExpandableTextView = (ExpandableTextView) findViewById(R.id.catalog_text);
    }

    private void getBookInfoByISBN(String isbn) {
        if (isbn == null || isbn.length() < 10) {
            Log.e(TAG, "isbn error:");
            setNoContentView();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DOUBAN_BOOK_ISBN_API + isbn, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
//                        Log.d(TAG, object.toString());
                        DoubanBook doubanBook = JsonUtils.parseBook(object);

                        loadBookData(doubanBook);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, "getbook error");
                    }
                });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void loadBookData(DoubanBook doubanBook) {
        bookTitle.setText(doubanBook.getTitle());
        bookPublisher.setText(doubanBook.getPublisher());
        summaryExpandableTextView.setText(doubanBook.getSummary());
        collapsingToolbarLayout.setTitle(doubanBook.getTitle());
        catalogExpandableTextView.setText(doubanBook.getCatalog());

        ImageRequest imageRequest = new ImageRequest(doubanBook.getImages().get("large"),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        bookCover.setImageBitmap(bitmap);
                        setPalette(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        requestQueue.add(imageRequest);
    }

    private void setPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                appbarLayout.setBackgroundColor(swatch.getRgb());
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        requestQueue.cancelAll(TAG);
    }

    /**
     * if no content
     */
    private void setNoContentView() {

    }
}
