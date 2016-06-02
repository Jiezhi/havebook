package io.github.jiezhi.havebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.jiezhi.havebook.utils.Constants;

/**
 * Created by jiezhi on 6/2/16.
 * Function:
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteHelper";

    public static final String DB_NAME = "book.db";
    public static final String TABLE_LIKED_BOOK = "book_liked";
    private static final String CREATE_TABLE_LIKED_BOOK =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_LIKED_BOOK +
                    "(" + Constants.Book.ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE NOT NULL," +
                    Constants.Book.AUTHOR_INTRO + " TEXT," +
                    Constants.Book.BINDING + " TEXT," +
                    Constants.Book.CATALOG + " TEXT," +
                    Constants.Book.IMAGE + " TEXT," +
                    Constants.Book.LARGE_IMG + " TEXT," +
                    Constants.Book.MEDIUM_IMG + " TEXT," +
                    Constants.Book.SMALL_IMG + " TEXT," +
                    Constants.Book.ISBN10 + " TEXT," +
                    Constants.Book.ISBN13 + " TEXT," +
                    Constants.Book.ORIGIN_TITLE + " TEXT," +
                    Constants.Book.PAGES + " TEXT," +
                    Constants.Book.PRICE + " TEXT," +
                    Constants.Book.PUBDATE + " DATE," +
                    Constants.Book.PUBLISHER + " TEXT," +
                    Constants.Book.SUBTITLE + " TEXT," +
                    Constants.Book.TITLE + " TEXT," +
                    Constants.Book.URL + " TEXT," +
                    Constants.Book.ALT + " TEXT," +
                    Constants.Book.ALT_TITLE + " TEXT," +
                    Constants.Book.AUTHOR + " TEXT," +
                    Constants.Book.TRANSLATOR + " TEXT," +
                    Constants.Book.RATING_AVERAGE + " TEXT," +
                    Constants.Book.RATING_MAX + " TEXT," +
                    Constants.Book.RATING_MIN + " TEXT," +
                    Constants.Book.RATING_NUMRATERS + " INTEGER," +
                    Constants.Book.TAGS + " TEXT);";

    private SQLiteDatabase db;

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
//        SQLiteStatement statement = db.compileStatement(DICTIONARY_TABLE_CREATE);
//        statement.execute();
        db.execSQL(CREATE_TABLE_LIKED_BOOK);
    }

    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LIKED_BOOK, null, values);
    }

    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_LIKED_BOOK, null, null, null, null, null, null);
    }


    public void delete(String id) {
        if (db == null) db = getWritableDatabase();
        db.delete(TABLE_LIKED_BOOK, "id=?", new String[]{id});
    }

    public void close() {
        if (db != null) db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
