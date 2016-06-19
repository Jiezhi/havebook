package io.github.jiezhi.havebook.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.jiezhi.havebook.R;
import io.github.jiezhi.havebook.db.MySQLiteHelper;
import io.github.jiezhi.havebook.fragment.BooksFragment;
import io.github.jiezhi.havebook.utils.Constants;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Context context;
    private TextView textView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.nobook_tv);

        initTool();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBookCollections();
    }

    private void loadBookCollections() {
        dialog.setMessage("正在加载收藏的图书");

        BooksFragment fragment = new BooksFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Action.ACTION, Constants.Action.SHOW_COLLECT);
        fragment.setArguments(bundle);
        // get collections from db
        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        Cursor cursor = dbHelper.query();
        DoubanBook book;
        List<DoubanBook> doubanBooks = new ArrayList<>();
        while (cursor.moveToNext()) {
            book = new DoubanBook(cursor);
            doubanBooks.add(book);
        }
        // pass the books to the fragment
        cursor.close();
        dbHelper.close();
        Log.d(TAG, "collected books:" + doubanBooks.size());
        if (doubanBooks.size() == 0) { // no book display no content view
            textView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            fragment.setDoubanBooks(doubanBooks);
            getFragmentManager().beginTransaction().replace(R.id.liked_book_fragment, fragment).commit();
        }
        dialog.dismiss();
    }


    private void initTool() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.my_collections);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    onSearchRequested();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setMessage("请选择添加方式");
                    alertDialog.setPositiveButton("扫码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "scan");
                        }
                    });
                    alertDialog.setNegativeButton("Search", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "search");
                        }
                    });
                    alertDialog.create().show();
                }
            });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onSearchRequested() {
        Log.d(TAG, "onSearchRequested called");
        Bundle appData = new Bundle();
        appData.putBoolean("test", true);
        startSearch(null, false, appData, false);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_collect) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.setAction(Constants.Action.SHOW_COLLECT);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
