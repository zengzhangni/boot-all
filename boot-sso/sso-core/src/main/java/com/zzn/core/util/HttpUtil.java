package com.zzn.core.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author zengzhangni
 * @date 2019/8/19
 */
public class HttpUtil {

    public static String doGet(String url) throws IOException {
        HttpGet httpGet;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(url);


        HttpResponse httpResponse = httpClient.execute(httpGet);
        // 发送成功
        return EntityUtils.toString(httpResponse.getEntity());
    }

    public static String doPost(String url, StringEntity entity) throws IOException {
        HttpPost httpPost;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpPost = new HttpPost(url);
        if (entity != null) {
            httpPost.setEntity(entity);
        }
        HttpResponse httpResponse = httpClient.execute(httpPost);
        // 发送成功
        return EntityUtils.toString(httpResponse.getEntity());
    }
}
