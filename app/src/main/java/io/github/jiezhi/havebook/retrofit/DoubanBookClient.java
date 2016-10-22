package io.github.jiezhi.havebook.retrofit;

import java.util.Map;

import io.github.jiezhi.havebook.model.DoubanBookModel;
import io.github.jiezhi.havebook.model.DoubanSearchModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Function
 *
 * @author Jiezhi
 * @version 1.0, 22/10/2016
 */
public interface DoubanBookClient {

    // 获取图书信息
    @GET("{id}")
    Observable<DoubanBookModel> getBookById(@Path("id") String id);

    //根据isbn获取图书信息
    @GET("isbn/{isbn}")
    Observable<DoubanBookModel> getBookByIsbn(@Path("isbn") String id);

    //搜索图书
    @GET("search")
    Observable<DoubanSearchModel> searchBook(@QueryMap Map<String, String> options);

    //获取某个图书中标记最多的标签
    @GET("{id}/tags")
    Observable<DoubanBookModel> getTagsById(@Path("id") String id);

}
