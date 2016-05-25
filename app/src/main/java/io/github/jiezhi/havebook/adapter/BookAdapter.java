package io.github.jiezhi.havebook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.app.MySingleton;
import io.github.jiezhi.havebook.fragment.BooksFragment;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.Utils;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private static final String TAG = "BookAdapter";

    private final List<DoubanBook> doubanBooks;
    private Context context;
    private DoubanBook currentDoubanBook;
    private Bitmap bitmap;
    private int defaultbkgcolor;
    private static final int SCALE_DELAY = 30;

    private BooksFragment.OnBookClickedListener onBookClickedListener;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;

    public void setOnBookClickedListener(BooksFragment.OnBookClickedListener onBookClickedListener) {
        this.onBookClickedListener = onBookClickedListener;
    }

    public BookAdapter(List<DoubanBook> doubanBooks) {
        this.doubanBooks = doubanBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rowView = LayoutInflater.from(context).inflate(R.layout.book_item2, parent, false);
        defaultbkgcolor = context.getResources().getColor(R.color.book_without_palette);

        imageLoader = MySingleton.getInstance(context).getImageLoader();
        requestQueue = MySingleton.getInstance(context).getRequestQueue();

        return new ViewHolder(rowView, onBookClickedListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        currentDoubanBook = doubanBooks.get(position);
        holder.bookTitle.setText(currentDoubanBook.getTitle());
        holder.bookAuthor.setText(currentDoubanBook.getAuthors()[0]);
        holder.bookCover.setDrawingCacheEnabled(true);
//        bitmap = holder.bookCover.getDrawingCache();

        ImageRequest imageRequest = new ImageRequest(currentDoubanBook.getImages().get("large"),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.bookCover.setImageBitmap(bitmap);
                        setCellColors(bitmap, holder, position);
                        animateCell(holder);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        requestQueue.add(imageRequest);
    }

    private void animateCell(ViewHolder holder) {
        int cellPosition = holder.getLayoutPosition();
        if (!holder.animated) {
            holder.animated = true;
            holder.bookContainer.setScrollX(0);
            holder.bookContainer.setScrollY(0);
            holder.bookContainer.animate()
                    .scaleY(1).scaleX(1)
                    .setDuration(200)
                    .setStartDelay(SCALE_DELAY * cellPosition)
                    .start();
        }
    }

    private void setCellColors(Bitmap bitmap, final ViewHolder holder, final int position) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    holder.bookTitle.setTextColor(vibrantSwatch.getTitleTextColor());
                    holder.bookAuthor.setTextColor(vibrantSwatch.getTitleTextColor());
                    holder.bookCover.setTransitionName("cover" + position);
                    holder.bookTextContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBookClickedListener.onclick(v, position);
                        }
                    });
                    Utils.animateViewColor(holder.bookTextContainer, defaultbkgcolor, vibrantSwatch.getRgb());
                } else {
                    Log.e(TAG, "BookAdapter onGenerated - The VibrantSwatch were null at: " + position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doubanBooks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected LinearLayout bookContainer;
        protected RelativeLayout bookTextContainer;
        protected ImageView bookCover;
        protected TextView bookTitle;
        protected TextView bookAuthor;

        protected boolean animated = false;

        public ViewHolder(View itemView, BooksFragment.OnBookClickedListener onBookClickedListener) {
            super(itemView);

            setOnBookClickedListener(onBookClickedListener);

            bookContainer = (LinearLayout) itemView.findViewById(R.id.book_item_container);
            bookTextContainer = (RelativeLayout) itemView.findViewById(R.id.book_item_text_container);
            bookCover = (ImageView) itemView.findViewById(R.id.book_item_img);
            bookTitle = (TextView) itemView.findViewById(R.id.book_item_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.book_item_author);
            bookCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBookClickedListener.onclick(v, getLayoutPosition());
        }
    }

}
