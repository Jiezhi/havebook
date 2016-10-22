package io.github.jiezhi.havebook.model;

import java.util.List;

/**
 * Function
 *
 * @author Jiezhi
 * @version 1.0, 22/10/2016
 */
public class DoubanCollectionModel {
    private static final String TAG = DoubanCollectionModel.class.getSimpleName();


    /**
     * book : Book
     * book_id : 7056972
     * comment : 各种成长的喜悦与痛苦，讲故事的功力比起过往短篇经典有过之而无不及，依旧是巅峰之作！
     * id : 593151296
     * rating : {"max":5,"min":0,"value":"5"}
     * status : read
     * tags : ["吴淼","奇幻","中国","塔希里亚"]
     * updated : 2012-10-19 15:29:41
     * user_id : 33388491
     */

    private String book;
    private String book_id;
    private String comment;
    private int id;
    /**
     * max : 5
     * min : 0
     * value : 5
     */

    private RatingEntity rating;
    private String status;
    private String updated;
    private String user_id;
    private List<String> tags;

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static class RatingEntity {
        private int max;
        private int min;
        private String value;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
