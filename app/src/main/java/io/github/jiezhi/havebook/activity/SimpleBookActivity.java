package io.github.jiezhi.havebook.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.model.DoubanBookModel;
import io.github.jiezhi.havebook.views.FlowLayout;

/**
 * Created by jiezhi on 5/26/16.
 * Function: Display book
 */
public class SimpleBookActivity extends BaseActivity {
    private static final String TAG = "SimpleBookActivity";

    private ImageView bookCover;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;
    private TextView bookPublisher;
    private TextView bookCatalogTitle;
    private FlowLayout tagFlowLayout;
    private ExpandableTextView summaryExpandableTextView;
    private ExpandableTextView catalogExpandableTextView;
    private FloatingActionButton faButton;

    private AppBarLayout appbarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private boolean isLiked = false;

    private SQLiteDatabase db;

    private DoubanBookModel doubanBook = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        initView();
        Intent intent = getIntent();
        doubanBook = (DoubanBookModel) intent.getSerializableExtra("book");
        String isbn;
        if (doubanBook != null) { // start from book list
            Log.d(TAG, doubanBook.toString());
            loadBookData(doubanBook);
            isbn = doubanBook.getIsbn13();
        } else {// start from scan activity
            isbn = intent.getStringExtra("isbn");
            if (isbn == null) isbn = "9787115416292";
            Log.d(TAG, "isbn: " + isbn);
            collapsingToolbarLayout.setTitle(isbn);
            getBookInfoByISBN(isbn);
        }

        checkBookLiked(isbn);


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

        tagFlowLayout = (FlowLayout) findViewById(R.id.tag_flow_layout);
        faButton = (FloatingActionButton) findViewById(R.id.fab);

        // Switch of book collection
        faButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "long click", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (isLiked) { // Cancel collect the book
                    msg.arg1 = DEL_LIKED_BOOK;
                    setLiked(!isLiked);
                    Snackbar.make(v, "Book deleted from your collect", Snackbar.LENGTH_SHORT).show();
                } else { // add the book to collect
                    setLiked(!isLiked);
                    msg.arg1 = ADD_LIKED_BOOK;
                    Snackbar.make(v, "Book added from your collect", Snackbar.LENGTH_SHORT).show();
                }

//                Bundle bundle = new Bundle();
//                bundle.putSerializable(BOOK_RELATED, doubanBook);
//                msg.setData(bundle);
//                handler.sendMessage(msg);
            }
        });

        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // judge whether collapsingtoolbar scroll to top
                int delta = collapsingToolbarLayout.getHeight() + verticalOffset - toolbar.getHeight();
//                Log.d(TAG, "delta" + delta);
                if (delta <= 100) {
//                    Log.d(TAG, "Got place");
                }
            }
        });
    }

    private void setLiked(boolean isLike) {
        this.isLiked = isLike;
        if (faButton == null) return;
        if (isLike) {
            faButton.setImageDrawable(getDrawable(R.drawable.ic_menu_heart_liked));
        } else {
            faButton.setImageDrawable(getDrawable(R.drawable.ic_menu_heart_like));
        }
    }


    private void checkBookLiked(String isbn) {
        // TODO: 6/20/16
        /*
        DoubanBookDao doubanBookDao = MyDBHelper.getInstance(this).getDaoSession().getDoubanBookDao();
        List<DoubanBook> list = doubanBookDao.queryBuilder().where(DoubanBookDao.Properties.Isbn13.eq(isbn)).list();
        Log.d(TAG, "list size:" + list.size());
        if (list.size() > 0) {
            setLiked(true);
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (mySQLiteHelper.isLiked(isbn)) {
//                    Log.d(TAG, "this book is in liked list");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setLiked(true);
//                        }
//                    });
//                }
//                mySQLiteHelper.close();
//            }
//        }).start();
*/
    }

    private void getBookInfoByISBN(String isbn) {
        if (isbn == null || isbn.length() < 10) {
            Log.e(TAG, "isbn error:");
            setNoContentView();
            return;
        }

        // FIXME: 24/10/2016
        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.DoubanApi.DOUBAN_BOOK_ISBN_API + isbn, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
//                        Log.d(TAG, object.toString());
                        doubanBook = JsonUtils.parseBook(object);

                        loadBookData(doubanBook);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, "get book error");
                    }
                });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
        */
    }

    private void loadBookData(DoubanBookModel doubanBook) {
        bookTitle.setText(doubanBook.getTitle());
        bookPublisher.setText(doubanBook.getPublisher());
        summaryExpandableTextView.setText(doubanBook.getSummary());
        collapsingToolbarLayout.setTitle(doubanBook.getTitle());
        catalogExpandableTextView.setText(doubanBook.getCatalog());

        if (doubanBook.getTags() != null) {
            loadTags();
        } else {
            tagFlowLayout.setVisibility(View.GONE);
        }

        ImageRequest imageRequest = new ImageRequest(doubanBook.getImages().getLarge(),
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

        // make the button clickable after data loaded
        faButton.setVisibility(View.VISIBLE);
    }

    /**
     * Load book tags
     */
    private void loadTags() {
        TextView tagTV;
        LayoutInflater inflater = LayoutInflater.from(this);
        String tagName;
        for (DoubanBookModel.TagsEntity tag : doubanBook.getTags()) {
            tagName = tag.getName();
            tagTV = (TextView) inflater.inflate(R.layout.tag_textview, tagFlowLayout, false);
            tagTV.setText(tagName);
            tagFlowLayout.addView(tagTV);
        }
    }

    private void setPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch == null) {
                    Log.e(TAG, "swatch is null");
                    return;
                }
                appbarLayout.setBackgroundColor(swatch.getRgb());
                bookTitle.setTextColor(swatch.getTitleTextColor());

                Window window = getWindow();
                window.setStatusBarColor(swatch.getRgb());
                window.setNavigationBarColor(swatch.getRgb());
                tagFlowLayout.setBackgroundColor(swatch.getBodyTextColor());
//                toolbar.setBackgroundColor(swatch.getRgb());
                faButton.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
//                toolbar.setBackgroundColor(swatch.getRgb());
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
        // TODO: 6/11/16
    }
}
