package com.zzn.pay.util;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpConnectionUtil {
    private HttpURLConnection conn;
    private String connectUrl;

    public HttpConnectionUtil(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public void init() throws Exception {
        URL url = new URL(connectUrl);
        System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return urlHostName.equals(session.getPeerHost());
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setReadTimeout(60000);
        conn.setDoOutput(true);
        conn.setConnectTimeout(30000);
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            httpsConn.setSSLSocketFactory(SSLUtil.getInstance().getSSLSocketFactory());
        } else if (conn instanceof HttpURLConnection) {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
        } else {
            throw new Exception("不是http/https协议的url");
        }
        this.conn = (HttpURLConnection) conn;
        initDefaultPost();
    }

    public void destory() {
        try {
            if (this.conn != null) {
                this.conn.disconnect();
            }
        } catch (Exception e) {

        }
    }

    private void initDefaultPost() throws Exception {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

    public byte[] postParams(Map<String, String> params, boolean readreturn) throws IOException {
        StringBuilder outBuf = new StringBuilder();
        params.forEach((k, v) -> {
            if (StringUtils.isNotBlank(v)) {
                try {
                    outBuf
                            .append('&')
                            .append(k)
                            .append('=')
                            .append(URLEncoder.encode(v, "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return postParams(outBuf.toString().substring(1), readreturn);
    }

    public byte[] postParams(String message, boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message.getBytes(StandardCharsets.UTF_8));
        out.close();
        if (readreturn) {
            return readBytesFromStream(conn.getInputStream());
        } else {
            return null;
        }
    }

    public byte[] postParams(byte[] message, boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message);
        out.close();
        if (readreturn) {
            return readBytesFromStream(conn.getInputStream());
        } else {
            return null;
        }
    }

    private byte[] readBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int readLen;
        byte[] tmpBuf = new byte[4096];
        while ((readLen = is.read(tmpBuf)) > 0)
            baos.write(tmpBuf, 0, readLen);
        is.close();
        return baos.toByteArray();
    }

    public HttpURLConnection getConn() {
        return conn;
    }

}
