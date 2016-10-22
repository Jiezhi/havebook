package io.github.jiezhi.havebook.utils;

/**
 * Created by jiezhi on 5/26/16.
 * Function:
 */
public class Constants {
    private static final String TAG = "Constants";

    public class Others {
        public static final String SEPERATE = "/";
    }

    public class DoubanApi {
        public static final String DOUBAN_BOOK_API_V2 = "https://api.douban.com/v2/book/";
        public static final String DOUBAN_BOOK_ISBN_API = DOUBAN_BOOK_API_V2 + "isbn/";
        public static final String DOUBAN_BOOK_SEARCH_API = DOUBAN_BOOK_API_V2 + "search";
    }

    public class Action {
        public static final String ACTION = "action";
        public static final String SHOW_COLLECT = "showCollect";
        public static final String SHOW_SEARCH = "showSearch";
    }


    public class Book {
        public static final String ID = "id";
        public static final String AUTHOR_INTRO = "author_intro";
        public static final String BINDING = "binding";
        public static final String CATALOG = "catalog";
        public static final String IMAGE = "image";
        public static final String LARGE_IMG = "large_img";
        public static final String MEDIUM_IMG = "medium_img";
        public static final String SMALL_IMG = "small_img";
        public static final String ISBN10 = "isbn10";
        public static final String ISBN13 = "isbn13";
        public static final String ORIGIN_TITLE = "origin_title";
        public static final String PAGES = "pages";
        public static final String PRICE = "price";
        public static final String PUBDATE = "pubdate";
        public static final String PUBLISHER = "publisher";
        public static final String SUBTITLE = "subtitle";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String ALT = "alt";
        public static final String ALT_TITLE = "alt_title";
        public static final String AUTHOR = "author";
        public static final String TRANSLATOR = "translator";
        public static final String RATING_AVERAGE = "rating_average";
        public static final String RATING_MAX = "rating_max";
        public static final String RATING_MIN = "rating_min";
        public static final String RATING_NUMRATERS = "rating_numRaters";
        public static final String TAGS = "tags";
    }
}
