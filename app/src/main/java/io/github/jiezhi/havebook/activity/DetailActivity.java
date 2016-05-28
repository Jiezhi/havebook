package io.github.jiezhi.havebook.activity;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.fragment.BooksFragment;
import io.github.jiezhi.havebook.model.DoubanBook;
import io.github.jiezhi.havebook.utils.ViewUtils;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";

    private DoubanBook book;
    private FloatingActionButton faButton;
    private FrameLayout contentCard;
    private View mainContainer;
    private View titlesContainer;
    private Toolbar toolbar;
    private LinearLayout bookInfoLayout;
    private TextView contentTextView;
    private TextView ratingTextView;
    private TextView ratingValueTextView;
    private TextView summaryTitle;
    private TextView titleTextView;
    private TextView subtitleTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_detail_full);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        initView();

        int position = getIntent().getIntExtra("position", 0);
        book = (DoubanBook) getIntent().getSerializableExtra("select_book");


        final Bitmap bookCover = BooksFragment.photoCache.get(position);

        ((ImageView) findViewById(R.id.book_detail_cover)).setImageBitmap(bookCover);

        // define toolbar as the shared element
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bookCover);
        bitmapDrawable.setAlpha(150);
        toolbar.setBackground(bitmapDrawable);
        toolbar.setTransitionName("cover" + position);

        setEnterAnimation();

        fillBookInfo();
        


        // Generate palette colors
        Palette.from(bookCover).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    getWindow().setStatusBarColor(vibrantSwatch.getRgb());
                    getWindow().setNavigationBarColor(vibrantSwatch.getRgb());

                    mainContainer.setBackgroundColor(vibrantSwatch.getRgb());
//                    contentTextView.setTextColor(vibrantSwatch.getBodyTextColor());

                    subtitleTextView.setTextColor(vibrantSwatch.getTitleTextColor());

                    summaryTitle.setTextColor(vibrantSwatch.getRgb());

//                    bookInfoLayout.setBackgroundColor(vibrantSwatch.getRgb());
                    titleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                    subtitleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                    ratingTextView.setTextColor(vibrantSwatch.getRgb());
                }
            }
        });
    }

    private void fillBookInfo() {
        String content = book.getSummary();
        if (content != null) contentTextView.setText(content);
        titleTextView.setText(book.getTitle());
        String[] authors = book.getAuthors();
        StringBuilder sb = new StringBuilder();
        for (String author : authors) {
            sb.append(author).append(" ");
        }
        subtitleTextView.setText(sb.toString());
        ratingValueTextView.setText(book.getRating().get("average") + "/10"); 
    }

    private void setEnterAnimation() {
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                ViewPropertyAnimator showTitleAnimator = ViewUtils.showViewByScale(mainContainer);
                showTitleAnimator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        titlesContainer.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this, R.anim.alpha_on));
                        titlesContainer.setVisibility(View.VISIBLE);

                        ViewUtils.showViewByScale(faButton).start();
                        ViewUtils.showViewByScale(bookInfoLayout).start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                showTitleAnimator.start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void initView() {
        faButton = (FloatingActionButton) findViewById(R.id.book_detail_fab);
        if (faButton != null) {
            faButton.setScrollX(0);
            faButton.setScaleY(0);
            faButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailActivity.this, "You clicked the button", Toast.LENGTH_SHORT).show();
                    faButton.setBackgroundColor(Color.RED);
                }
            });
        }

        contentCard = (FrameLayout) findViewById(R.id.card_view);
        mainContainer = findViewById(R.id.book_detail_main_container);
        titlesContainer = findViewById(R.id.book_detail_titles_container);
        toolbar = (Toolbar) findViewById(R.id.book_detail_toolbar);
        bookInfoLayout = (LinearLayout) findViewById(R.id.book_detail_info);
        contentTextView = (TextView) findViewById(R.id.book_detail_content);
        ratingTextView = (TextView) findViewById(R.id.book_detail_rating_title);
        ratingValueTextView = (TextView) findViewById(R.id.book_detail_rating_value);
        summaryTitle = (TextView) findViewById(R.id.book_detail_summary_title);
        titleTextView = (TextView) findViewById(R.id.book_detail_title);
        subtitleTextView = (TextView) findViewById(R.id.book_detail_subtitle);


        // Confugur the views setting animation start point
        ViewUtils.configHideYView(contentCard);
        ViewUtils.configHideYView(bookInfoLayout);
        ViewUtils.configHideYView(mainContainer);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ViewPropertyAnimator hideTitleAnimator = ViewUtils.hideViewByScaleXY(faButton);
        titlesContainer.startAnimation(AnimationUtils.loadAnimation(DetailActivity.this, R.anim.alpha_off));
        titlesContainer.setVisibility(View.INVISIBLE);
        ViewUtils.hideViewByScaleY(bookInfoLayout);

        hideTitleAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewPropertyAnimator hideFabAnimator = ViewUtils.hideViewByScaleY(mainContainer);
                hideFabAnimator.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        coolBack();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void coolBack() {
        // TODO: 5/25/16 workaround 
    }
}
