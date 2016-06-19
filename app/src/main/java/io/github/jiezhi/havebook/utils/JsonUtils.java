package io.github.jiezhi.havebook.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static List<DoubanBook> parseBookFromJSONArray(JSONArray array) {
        List<DoubanBook> doubanBooks = new ArrayList<>();
        int len = array.length();
        JSONObject object;
        DoubanBook doubanBook;
        try {
            for (int i = 0; i < len; i++) {
                object = array.getJSONObject(i);
                doubanBook = parseBook(object);
                doubanBooks.add(doubanBook);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return doubanBooks;
    }

    public static DoubanBook parseBook(JSONObject object) {
        DoubanBook doubanBook = new DoubanBook();
        JSONObject detailObject;
        Map<String, String> bookInfoMap;
        try {
            doubanBook.setAlt(object.getString("alt"));
            doubanBook.setAlt_title(object.getString("alt_title"));
            doubanBook.setAuthors(getBookDetailList(object.getJSONArray("author")));
            doubanBook.setAuthor_intro(object.getString("author_intro"));
            doubanBook.setBinding(object.getString("binding"));
            doubanBook.setCatalog(object.getString("catalog"));
            doubanBook.setId(object.getString("id"));
            doubanBook.setImage(object.getString("image"));
            doubanBook.setIsbn10(object.getString("isbn10"));
            doubanBook.setIsbn13(object.getString("isbn13"));
            doubanBook.setOrigin_title(object.getString("origin_title"));
            doubanBook.setPages(object.getString("pages"));
            doubanBook.setPrice(object.getString("price"));
            doubanBook.setPubdate(object.getString("pubdate"));
            doubanBook.setPublisher(object.getString("publisher"));
            doubanBook.setSubtitle(object.getString("subtitle"));
            doubanBook.setSummary(object.getString("summary"));
            doubanBook.setTitle(object.getString("title"));
            doubanBook.setUrl(object.getString("url"));
            doubanBook.setTranslator(getBookDetailList(object.getJSONArray("translator")));

            // for images map
            detailObject = object.getJSONObject("images");
            doubanBook.setImg_large(detailObject.getString("large"));
            doubanBook.setImg_medium(detailObject.getString("medium"));
            doubanBook.setImg_small(detailObject.getString("small"));

            // for rating map
            detailObject = object.getJSONObject("rating");
            doubanBook.setRatingAverage(detailObject.getString("average"));
            doubanBook.setRatingMax(detailObject.getString("max"));
            doubanBook.setRatingMin(detailObject.getString("min"));
            doubanBook.setRatingNum(detailObject.getInt("numRaters"));

            // for tags map
            JSONArray tagArray = object.getJSONArray("tags");
            int len = tagArray.length();
            List<Map<String, String>> tagList = new ArrayList<>(len);
            for (int i = 0; i < len; i++) {
                bookInfoMap = new HashMap<>();
                bookInfoMap.put("count", tagArray.getJSONObject(i).getString("count"));
                bookInfoMap.put("name", tagArray.getJSONObject(i).getString("name"));
                bookInfoMap.put("title", tagArray.getJSONObject(i).getString("title"));
                tagList.add(bookInfoMap);
            }
            doubanBook.setTags(tagList);


        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.d(TAG, doubanBook.toString());
        return doubanBook;
    }


    public static String[] getBookDetailList(JSONArray array) {
        int len = array.length();
        String[] lists = new String[len];
        try {
            for (int i = 0; i < len; i++) {
                lists[i] = array.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

}
