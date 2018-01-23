package com.example.masato.githubfeed.util;

import android.os.Handler;
import android.os.Looper;

import com.example.masato.githubfeed.githubapi.Failure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/20.
 */

public class HandyHttpURLConnection {

    private ExecutorService executorService;
    private String urlString;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    public void setHeader(String key, String value) {
        header.put(key, value);
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public void getRequestBodyString(final OnHttpResponseListener listener) {
        get(new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
               try {
                   InputStream is = (InputStream) body;
                   notifyResponseOnUIThread(statusCode, stringFromStream(is), listener);
               } catch (IOException ioe) {
                   notifyErrorOnUIThread(Failure.INTERNET, listener);
               }
            }

            @Override
            public void onError(Failure failure) {
                notifyErrorOnUIThread(failure, listener);
            }
        });
    }

    public void getRequestBodyBytes(final OnHttpResponseListener listener) {
        get(new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                try {
                    InputStream is = (InputStream) body;
                    notifyResponseOnUIThread(statusCode, bytesFromStream(is), listener);
                } catch (IOException ioe) {
                    notifyErrorOnUIThread(Failure.INTERNET, listener);
                }
            }

            @Override
            public void onError(Failure failure) {
                notifyErrorOnUIThread(failure, listener);
            }
        });
    }

    public void postRequestBodyString(final OnHttpResponseListener listener) {
        post(new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                try {
                    InputStream is = (InputStream) body;
                    notifyResponseOnUIThread(statusCode, stringFromStream(is), listener);
                } catch (IOException ioe) {
                    notifyErrorOnUIThread(Failure.INTERNET, listener);
                }
            }

            @Override
            public void onError(Failure failure) {
                notifyErrorOnUIThread(failure, listener);
            }
        });
    }

    private String stringFromStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            builder.append(line);
            line = reader.readLine();
        }
        return builder.toString();
    }

    private byte[] bytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while (i != -1) {
            baos.write(i);
            i = is.read();
        }
        return baos.toByteArray();
    }

    private void post(final OnHttpResponseListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    setHeadersToConnection(connection);
                    connection.connect();
                    writePostBody(connection.getOutputStream());
                    listener.onHttpResponse(connection.getResponseCode(), connection.getInputStream());
                    connection.disconnect();
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                    listener.onError(Failure.UNEXPECTED);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    listener.onError(Failure.INTERNET);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    private void get(final OnHttpResponseListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    urlString = setParamsToUrlString(urlString);
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    setHeadersToConnection(connection);
                    connection.connect();
                    listener.onHttpResponse(connection.getResponseCode(), connection.getInputStream());
                    connection.disconnect();
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                    listener.onError(Failure.UNEXPECTED);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    listener.onError(Failure.INTERNET);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    private void writePostBody(OutputStream outputStream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            writer.write(key);
            writer.write("=");
            writer.write(params.get(key));
            writer.write("&");
        }
        writer.flush();
    }

    private String setParamsToUrlString(String urlString) {
        Set<String> keys = params.keySet();
        StringBuilder builder = new StringBuilder(urlString);
        builder.append("?");
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.append(key);
            builder.append("=");
            builder.append(URLEncoder.encode(params.get(key)));
            builder.append("&");
        }
        return builder.toString();
    }

    private void setHeadersToConnection(HttpURLConnection connection) {
        Set<String> keys = header.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            connection.setRequestProperty(key, header.get(key));
        }
    }

    private void notifyResponseOnUIThread(final int statusCode, final Object body, final OnHttpResponseListener listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.onHttpResponse(statusCode, body);
            }
        });
    }

    private void notifyErrorOnUIThread(final Failure failure, final OnHttpResponseListener listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.onError(failure);
            }
        });
    }

    public HandyHttpURLConnection(String url, ExecutorService executorService) {
        this.executorService = executorService;
        this.urlString = url;
    }

    public interface OnHttpResponseListener {
        public void onHttpResponse(int statusCode, Object body);

        public void onError(Failure failure);
    }
}
