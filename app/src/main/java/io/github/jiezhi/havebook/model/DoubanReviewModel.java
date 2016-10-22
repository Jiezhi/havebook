package io.github.jiezhi.havebook.model;

/**
 * Function
 *
 * @author Jiezhi
 * @version 1.0, 22/10/2016
 */
public class DoubanReviewModel {
    private static final String TAG = DoubanReviewModel.class.getSimpleName();


    /**
     * id : 1000104
     * title : 长大就笨了
     * alt : https://book.douban.com/review/1000104/
     * author : User
     * book : Book
     * rating : {"max":5,"value":"5","min":1}
     * votes : 1376
     * useless : 36
     * comments : 292
     * summary : 「我在」：http://www.bighead.cn/?p=67


     那天我第一次打开《小王子》，就呆了。不是因为它的清新剔透，而是圣...
     * published : 2005-04-06 11:51:52
     * updated : 2012-08-30 20:48:23
     */

    private int id;
    private String title;
    private String alt;
    private String author;
    private String book;
    /**
     * max : 5
     * value : 5
     * min : 1
     */

    private RatingEntity rating;
    private int votes;
    private int useless;
    private int comments;
    private String summary;
    private String published;
    private String updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getUseless() {
        return useless;
    }

    public void setUseless(int useless) {
        this.useless = useless;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public static class RatingEntity {
        private int max;
        private String value;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
}
