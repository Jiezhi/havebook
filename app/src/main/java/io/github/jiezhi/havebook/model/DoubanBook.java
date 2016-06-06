package io.github.jiezhi.havebook.model;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jiezhi.havebook.utils.Constants;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class DoubanBook implements Serializable {
    private static final String TAG = "DoubanBook";

    private String alt;
    private String alt_title;
    private String[] authors;
    private String author_intro;
    private String binding;
    private String catalog;
    private String id;
    private String image;
    private String img_large;
    private String img_medium;
    private String img_small;
    private String isbn10;
    private String isbn13;
    private String origin_title;
    private String pages;
    private String price;
    private String pubdate;
    private String publisher;
    private String ratingAverage;
    private String ratingMax;
    private String ratingMin;
    private int ratingNum;
    private String subtitle;
    private String summary;
    private List<Map<String, String>> tags;
    private String title;
    private String url;
    private String[] translator;

    public DoubanBook() {
    }

    public DoubanBook(Cursor c) {
        setId(c.getString(c.getColumnIndex(Constants.Book.ID)));
        setAuthor_intro(c.getString(c.getColumnIndex(Constants.Book.AUTHOR_INTRO)));
        setBinding(c.getString(c.getColumnIndex(Constants.Book.BINDING)));
        setCatalog(c.getString(c.getColumnIndex(Constants.Book.CATALOG)));
        setImage(c.getString(c.getColumnIndex(Constants.Book.IMAGE)));
        setImg_large(c.getString(c.getColumnIndex(Constants.Book.LARGE_IMG)));
        setImg_medium(c.getString(c.getColumnIndex(Constants.Book.MEDIUM_IMG)));
        setImg_small(c.getString(c.getColumnIndex(Constants.Book.SMALL_IMG)));
        setIsbn10(c.getString(c.getColumnIndex(Constants.Book.ISBN10)));
        setIsbn13(c.getString(c.getColumnIndex(Constants.Book.ISBN13)));
        setOrigin_title(c.getString(c.getColumnIndex(Constants.Book.ORIGIN_TITLE)));
        setPages(c.getString(c.getColumnIndex(Constants.Book.PAGES)));
        setPrice(c.getString(c.getColumnIndex(Constants.Book.PRICE)));
        setPubdate(c.getString(c.getColumnIndex(Constants.Book.PUBDATE)));
        setPublisher(c.getString(c.getColumnIndex(Constants.Book.PUBLISHER)));
        setSubtitle(c.getString(c.getColumnIndex(Constants.Book.SUBTITLE)));
        setTitle(c.getString(c.getColumnIndex(Constants.Book.TITLE)));
        setUrl(c.getString(c.getColumnIndex(Constants.Book.URL)));
        setAlt(c.getString(c.getColumnIndex(Constants.Book.ALT)));
        setAlt_title(c.getString(c.getColumnIndex(Constants.Book.ALT_TITLE)));
        setRatingAverage(c.getString(c.getColumnIndex(Constants.Book.RATING_AVERAGE)));
        setRatingMax(c.getString(c.getColumnIndex(Constants.Book.RATING_MAX)));
        setRatingMin(c.getString(c.getColumnIndex(Constants.Book.RATING_MIN)));
        setRatingNum(c.getInt(c.getColumnIndex(Constants.Book.RATING_NUMRATERS)));
        String tmp = c.getString(c.getColumnIndex(Constants.Book.AUTHOR));
        if (tmp != null && !tmp.equals("")) {
            String[] authors = tmp.split(Constants.Others.SEPERATE);
            setAuthors(authors);
        }

        tmp = c.getString(c.getColumnIndex(Constants.Book.TRANSLATOR));
        if (tmp != null && tmp.equals("")) {
            String[] translators = tmp.split(Constants.Others.SEPERATE);
            setTranslator(translators);
        }

        // TODO: 6/4/16 decide whether keep tag counts or not
        tmp = c.getString(c.getColumnIndex(Constants.Book.TAGS));
        if (tmp != null && tmp.equals("")) {
            String[] tmpTags = tmp.split(Constants.Others.SEPERATE);
            tags = new ArrayList<>();
            Map<String, String> tagMap;
            for (String tag:tmpTags){
                tagMap = new HashMap<>();
                tagMap.put("title", tag);
                tags.add(tagMap);
            }
        }

//        setTags(c.getString(c.getColumnIndex(Constants.Book.ID)));
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String[] getAuthors() {
        if (authors == null) return new String[]{"null"};
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getImg_large() {
        return img_large;
    }

    public void setImg_large(String img_large) {
        this.img_large = img_large;
    }

    public String getImg_medium() {
        return img_medium;
    }

    public void setImg_medium(String img_medium) {
        this.img_medium = img_medium;
    }

    public String getImg_small() {
        return img_small;
    }

    public void setImg_small(String img_small) {
        this.img_small = img_small;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getTranslator() {
        return translator;
    }

    public void setTranslator(String[] translator) {
        this.translator = translator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(String ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public String getRatingMax() {
        return ratingMax;
    }

    public void setRatingMax(String ratingMax) {
        this.ratingMax = ratingMax;
    }

    public String getRatingMin() {
        return ratingMin;
    }

    public void setRatingMin(String ratingMin) {
        this.ratingMin = ratingMin;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public List<Map<String, String>> getTags() {
        return tags;
    }

    public void setTags(List<Map<String, String>> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return "DoubanBook{" +
                "alt='" + alt + '\'' +
                ", alt_title='" + alt_title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", author_intro='" + author_intro + '\'' +
                ", binding='" + binding + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", img_large='" + img_large + '\'' +
                ", img_medium='" + img_medium + '\'' +
                ", img_small='" + img_small + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", origin_title='" + origin_title + '\'' +
                ", pages='" + pages + '\'' +
                ", price='" + price + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ratingAverage='" + ratingAverage + '\'' +
                ", ratingMax='" + ratingMax + '\'' +
                ", ratingMin='" + ratingMin + '\'' +
                ", ratingNum=" + ratingNum +
                ", subtitle='" + subtitle + '\'' +
                ", summary='" + summary + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", translator=" + Arrays.toString(translator) +
                ", catalog='" + catalog.substring(0, catalog.length() > 50 ? 50 : catalog.length()) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // We assume that two books are same if both has the same id
        return o instanceof DoubanBook && this.getId().equals(((DoubanBook) o).getId());
//        if (o instanceof DoubanBook)
//            return this.getId().equals(((DoubanBook) o).getId());
//        return false;
    }
}
