package io.github.jiezhi.havebook.fragment;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.activity.DetailActivity;
import io.github.jiezhi.havebook.adapter.BookAdapter;
import io.github.jiezhi.havebook.app.MySingleton;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.Constants;
import io.github.jiezhi.havebook.utils.JsonUtils;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class BooksFragment extends Fragment {
    private static final String TAG = "BooksFragment";

    public static SparseArray<Bitmap> photoCache = new SparseArray<>(1);

    private ProgressDialog loadingDialog;
    private List<DoubanBook> doubanBooks;
    private RecyclerView bookRecycler;
    private Toolbar toolbar;

    private RequestQueue requestQueue;

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        Log.d(TAG, "toolbar:" + toolbar);
    }

    public void setDoubanBooks(List<DoubanBook> doubanBooks){
        this.doubanBooks = doubanBooks;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        // Configure recyclerview
        bookRecycler = (RecyclerView) rootView.findViewById(R.id.framgment_books_recycler);
        int COLUMNS = 2;
        bookRecycler.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
//        bookRecycler.addItemDecoration(new RecyclerInsetDecoration(getActivity()));
        bookRecycler.addOnScrollListener(recyclerScrollListener);
        bookRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        // Init and show progress dialog
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage("Loading books...");
        loadingDialog.show();

        getBooksInfo();

        return rootView;
    }

    private void getBooksInfo() {
        String action = getArguments().getString(Constants.Action.ACTION);
        if (Constants.Action.SHOW_SEARCH.equals(action)) {
            Log.d(TAG, "searching books");
            String keyWord = getArguments().getString("keyword");
            searchBooks(keyWord);
        } else if (Constants.Action.SHOW_COLLECT.equals(action)) {
            BookAdapter adapter = new BookAdapter(doubanBooks);
            adapter.setOnBookClickedListener(onBookClickedListener);
            bookRecycler.setAdapter(adapter);
            loadingDialog.dismiss();
        }
    }


    private void searchBooks(String keyWord) {
        String searchurl = Constants.DoubanApi.DOUBAN_BOOK_SEARCH_API + Uri.encode(keyWord);
        Log.d(TAG, searchurl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
//                        searchTV.setText(jsonObject.toString());
                        try {
                            String count = jsonObject.getString("count");
                            String start = jsonObject.getString("start");
                            String total = jsonObject.getString("total");
//                            Log.d(TAG, "Json:" + jsonObject.toString());
                            Log.d(TAG, "count:" + count + "  start:" + start + " total:" + total);
                            JSONArray array = jsonObject.getJSONArray("books");
                            doubanBooks = JsonUtils.parseBookFromJSONArray(array);
                            BookAdapter adapter = new BookAdapter(doubanBooks);
                            adapter.setOnBookClickedListener(onBookClickedListener);
                            bookRecycler.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loadingDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, "Error: " + volleyError.toString());
                        loadingDialog.dismiss();
                    }
                });

        request.setTag(TAG);

        requestQueue.add(request);
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {

        public int lastDy;
        public boolean flag;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (toolbar == null)
                return;
//                throw new IllegalStateException("no reference of toolbar");
            // scrolling up
//            if (dy > 50) {
//                if (!flag) {
//                    hideToolBar();
//                    flag = true;
//                }
//            }
//            // scrolling down
//            else if (dy < -50) {
//                if (flag) {
//                    showToolbar();
//                    flag = false;
//                }
//            }
//
//            lastDy = dy;
        }
    };

    private OnBookClickedListener onBookClickedListener = new OnBookClickedListener() {
        @Override
        public void onclick(View view, int position) {
            DoubanBook selectedDoubanBook = doubanBooks.get(position);
            Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
            detailIntent.putExtra("position", position);
            detailIntent.putExtra("select_book", selectedDoubanBook);

            ImageView coverImage = (ImageView) view.findViewById(R.id.book_item_img);
            ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
            photoCache.put(position, coverImage.getDrawingCache());

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(getActivity(),
                            new Pair<View, String>(coverImage, "cover" + position));

            startActivity(detailIntent, options.toBundle());
        }
    };

    private void hideToolBar() {
        toolbar.setVisibility(View.GONE);
        toolbar.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up_on));
    }

    private void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        toolbar.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.translate_up_off));
    }



    public interface OnBookClickedListener {

        void onclick(View view, int position);
    }
}
