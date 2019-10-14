package com.tydk.mymvp.http;

import com.orhanobut.logger.Logger;
import com.tydk.mymvp.common.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;


/**
 * @author: zzs
 * @date: 2019-07-30 下午 2:53
 * @description: 自定义Interceptor拦截器
 */
public class InterceptorUtil {
    public static String TAG = "InterceptorUtil";

    /**
     * @author: zzs
     * @date: 2019-07-30 下午 2:55
     * @description: 日志拦截器
     */
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            public void log(String message) {
                //LogToFile.i("网络请求",message);
                Logger.i(message);
            }
        }).setLevel(Level.BODY);
    }

    /**
     * @author: zzs
     * @date: 2019-07-30 下午 2:56
     * @description: 多baseUrl拦截器
     */
    public static Interceptor MoreBaseUrlInterceptor() {
        return new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                //获取原始的originalRequest
                Request originalRequest = chain.request();
                //获取老的url
                HttpUrl oldUrl = originalRequest.url();
                //获取originalRequest的创建者builder
                Request.Builder builder = originalRequest.newBuilder();
                //获取头信息的集合如：manage,mdffx
                List<String> urlNameList = originalRequest.headers("urlName");
                if (urlNameList != null && urlNameList.size() > 0) {
                    //删除原有配置中的值,就是namesAndValues集合里的值
                    builder.removeHeader("urlName");
                    //获取头信息中配置的value,如：manage或者mdffx
                    String urlName = urlNameList.get(0);
                    HttpUrl baseURL = null;
                    //根据头信息中配置的value,来匹配新的base_url地址
                    if ("manage".equals(urlName)) {
                        baseURL = HttpUrl.parse(Constant.URL);
                    }
                    //重建新的HttpUrl，需要重新设置的url部分
                    HttpUrl newHttpUrl = oldUrl.newBuilder()
                            .scheme(baseURL.scheme())//http协议如：http或者https
                            .host(baseURL.host())//主机地址
                            .port(baseURL.port())//端口
                            .build();
                    //获取处理后的新newRequest
                    Request newRequest = builder.url(newHttpUrl).build();
                    return chain.proceed(newRequest);
                } else {
                    return chain.proceed(originalRequest);
                }
            }
        };
    }
}
