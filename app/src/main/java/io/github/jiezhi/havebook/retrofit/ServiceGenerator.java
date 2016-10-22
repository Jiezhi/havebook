package io.github.jiezhi.havebook.retrofit;

import java.io.IOException;

import io.github.jiezhi.havebook.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Function
 *
 * @author Jiezhi
 * @version 1.0, 22/10/2016
 */
public class ServiceGenerator {
    private static final String TAG = ServiceGenerator.class.getSimpleName();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.DoubanApi.DOUBAN_BOOK_API_V2)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, String TAG, boolean needToken) {
        if (needToken) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("x-auth-token", "")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        httpClient.addInterceptor(new LoggerInterceptor(TAG, true));
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
