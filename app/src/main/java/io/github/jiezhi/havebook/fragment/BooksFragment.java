package io.github.jiezhi.havebook.fragment;

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
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.activity.SimpleBookActivity;
import io.github.jiezhi.havebook.adapter.BookAdapter;
import io.github.jiezhi.havebook.app.MySingleton;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.Constants;
import io.github.jiezhi.havebook.utils.JsonUtils;
import io.github.jiezhi.havebook.views.RecyclerInsetDecoration;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class BooksFragment extends Fragment {
    private static final String TAG = "BooksFragment";

    public static SparseArray<Bitmap> photoCache = new SparseArray<>(1);

    private int start = 0;//book search start offset
    private int count = 20;// book search count per request
    private String keyWord;

    private ProgressDialog loadingDialog;
    private List<DoubanBook> doubanBooks;
    private RecyclerView bookRecycler;
    private GridLayoutManager gridLayoutManager;
    private Toolbar toolbar;

    private BookAdapter adapter;

    private RequestQueue requestQueue;

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        Log.d(TAG, "toolbar:" + toolbar);
    }

    public void setDoubanBooks(List<DoubanBook> doubanBooks) {

        this.doubanBooks = doubanBooks;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        doubanBooks = new ArrayList<>();
        requestQueue = MySingleton.getInstance(getActivity()).getRequestQueue();
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        // Configure recycler view
        bookRecycler = (RecyclerView) rootView.findViewById(R.id.framgment_books_recycler);
        int COLUMNS = 2;
        gridLayoutManager = new GridLayoutManager(getActivity(), COLUMNS);
        bookRecycler.setLayoutManager(gridLayoutManager);
//        bookRecycler.addItemDecoration(new RecyclerInsetDecoration(getActivity()));
        bookRecycler.addOnScrollListener(recyclerScrollListener);
        bookRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        bookRecycler.addItemDecoration(new RecyclerInsetDecoration(getActivity()));

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
            keyWord = getArguments().getString("keyword");
            searchBooks(keyWord, 0, count);
        } else if (Constants.Action.SHOW_COLLECT.equals(action)) {
            BookAdapter adapter = new BookAdapter(doubanBooks);
            adapter.setOnBookClickedListener(onBookClickedListener);
            bookRecycler.setAdapter(adapter);
            loadingDialog.dismiss();
        }
    }


    private void searchBooks(String keyWord, int start, int count) {
        String params = String.format("?q=%s&start=%d&count=%d", Uri.encode(keyWord), start, count);
        String searchurl = Constants.DoubanApi.DOUBAN_BOOK_SEARCH_API + params;
        Log.d(TAG, searchurl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, searchurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
//                        searchTV.setText(jsonObject.toString());
                        try {
                            int count = jsonObject.getInt("count");
                            int start = jsonObject.getInt("start");
                            int total = jsonObject.getInt("total");
//                            Log.d(TAG, "Json:" + jsonObject.toString());
                            setStart(start);
                            setCount(count);
                            Log.d(TAG, "count:" + count + "  start:" + start + " total:" + total);
                            JSONArray array = jsonObject.getJSONArray("books");
                            doubanBooks.addAll(JsonUtils.parseBookFromJSONArray(array));
//                            doubanBooks = JsonUtils.parseBookFromJSONArray(array);
                            if (adapter == null) {
                                adapter = new BookAdapter(doubanBooks);
                            } else {
                                adapter.setDoubanBooks(doubanBooks);
                                // FIXME: 6/4/16 
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyItemInserted(doubanBooks.size() - 1);
                                    }
                                });
                            }
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
        public int lastViewPosition;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && lastViewPosition + 1 == recyclerView.getAdapter().getItemCount()) {
                Log.d(TAG, "It's time to load more data");
//                searchBooks(keyWord, start + count, count);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastViewPosition = gridLayoutManager.findLastVisibleItemPosition();
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

            Intent intent = new Intent(getActivity(), SimpleBookActivity.class);
            intent.putExtra("book", selectedDoubanBook);
            startActivity(intent);

//            Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
//            detailIntent.putExtra("position", position);
//            detailIntent.putExtra("select_book", selectedDoubanBook);

//            ImageView coverImage = (ImageView) view.findViewById(R.id.book_item_img);
//            ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
//            photoCache.put(position, coverImage.getDrawingCache());

//            ActivityOptions options = ActivityOptions.
//                    makeSceneTransitionAnimation(getActivity(),
//                            new Pair<View, String>(coverImage, "cover" + position));

//            startActivity(detailIntent, options.toBundle());
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public interface OnBookClickedListener {

        void onclick(View view, int position);
    }
}
