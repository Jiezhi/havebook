package io.github.jiezhi.havebook.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> images;
    private String isbn10;
    private String isbn13;
    private String origin_title;
    private String pages;
    private String price;
    private String pubdate;
    private String publisher;
    private Map<String, String> rating;
    private String subtitle;
    private String summary;
    private List<Map<String, String>> tags;
    private String title;
    private String url;
    private String[] translator;

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

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public Map<String, String> getRating() {
        return rating;
    }

    public void setRating(Map<String, String> rating) {
        this.rating = rating;
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
                ", catalog='" + catalog + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", images=" + images +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", origin_title='" + origin_title + '\'' +
                ", pages='" + pages + '\'' +
                ", price='" + price + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", rating=" + rating +
                ", subtitle='" + subtitle + '\'' +
                ", summary='" + summary + '\'' +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", translator=" + Arrays.toString(translator) +
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
