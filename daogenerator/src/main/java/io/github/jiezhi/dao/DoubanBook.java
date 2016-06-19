package io.github.jiezhi.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by jiezhi on 6/19/16.
 * Function:
 */
public class DoubanBook {
    private static final String TAG = "DoubanBook";

    public static void main(String[] args) {
        Schema schema = new Schema(1, "io.github.jiezhi.havebook.dao");
        createDoubanBook(schema);
        createUserBook(schema, "MyBook");
        generateDaoFiles(schema);
    }

    private static void createUserBook(Schema schema, String entityName) {
        Entity entity = schema.addEntity(entityName);
        entity.addIdProperty();
        entity.addStringProperty("doubanId");
        entity.addStringProperty("isbn13");
        entity.addStringProperty("img_local");
        entity.addDateProperty("collectDate");
        entity.addDateProperty("modifyDate");
        entity.addStringProperty("title");
        entity.addStringProperty("myComment");
        entity.addStringProperty("myTag");
        entity.addStringProperty("myRating");
        entity.addIntProperty("pages");
        entity.addIntProperty("readingPages");

        entity.addBooleanProperty("readFinish");

        entity.addBooleanProperty("isLike");
        entity.addBooleanProperty("isHave");
        entity.addStringProperty("whereFrom");
    }

    private static void createDoubanBook(Schema schema) {
        Entity book = schema.addEntity("DoubanBook");
        book.addStringProperty("alt");
        book.addStringProperty("alt_title");
        book.addStringProperty("authors");
        book.addStringProperty("author_intro");
        book.addStringProperty("binding");
        book.addStringProperty("catalog");
        book.addStringProperty("id").notNull();
        book.addStringProperty("image");
        book.addStringProperty("img_large");
        book.addStringProperty("img_medium");
        book.addStringProperty("img_small");
        book.addStringProperty("isbn10");
        book.addStringProperty("isbn13");
        book.addStringProperty("origin_title");
        book.addStringProperty("pages");
        book.addStringProperty("price");
        book.addStringProperty("pubdate");
        book.addStringProperty("publisher");
        book.addStringProperty("ratingAverage");
        book.addStringProperty("ratingMax");
        book.addStringProperty("ratingMin");
        book.addIntProperty("ratingNum");
        book.addStringProperty("subtitle");
        book.addStringProperty("summary");
        book.addStringProperty("title");
        book.addStringProperty("url");
        book.addStringProperty("translator");
        book.addStringProperty("tags");
    }

    private static void generateDaoFiles(Schema schema) {
        try {
            DaoGenerator generator = new DaoGenerator();
            generator.generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
