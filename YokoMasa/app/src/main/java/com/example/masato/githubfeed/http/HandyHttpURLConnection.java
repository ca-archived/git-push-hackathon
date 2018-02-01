package com.example.masato.githubfeed.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
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

    public void put(final ConnectionResultListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("PUT");
                    connection.setDoOutput(true);
                    setHeadersToConnection(connection);
                    connection.connect();
                    writePutBody(connection.getOutputStream());
                    handleConnection(connection, listener);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    handleFailure(listener);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    public void get(final ConnectionResultListener listener) {
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
                    handleConnection(connection, listener);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    handleFailure(listener);
                }  finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    public void post(final ConnectionResultListener listener) {
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
                    handleConnection(connection, listener);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    handleFailure(listener);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    public void delete(final ConnectionResultListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");
                    setHeadersToConnection(connection);
                    connection.connect();
                    handleConnection(connection, listener);
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    handleFailure(listener);
                }  finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
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

    private void writePutBody(OutputStream outputStream) throws IOException, JSONException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        JSONObject jsonObject = new JSONObject();
        Set<String> keys = params.keySet();
        if (keys.size() != 0) {
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                jsonObject.put(key, params.get(key));
            }
            writer.write(jsonObject.toString());
        }
        writer.flush();
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

    private void handleFailure(ConnectionResultListener listener) {
        ConnectionResult result = new ConnectionResult();
        result.isConnectionSuccessful = false;
        notifyResponseOnUIThread(result, listener);
    }

    private void handleConnection(HttpURLConnection connection, ConnectionResultListener listener) throws IOException {
        ConnectionResult result = new ConnectionResult();
        result.responseCode = connection.getResponseCode();
        result.header = connection.getHeaderFields();
        if (result.responseCode >= 400) {
            result.bodyBytes =  bytesFromStream(connection.getErrorStream());
        } else {
            result.bodyBytes = bytesFromStream(connection.getInputStream());
        }
        result.isConnectionSuccessful = true;
        notifyResponseOnUIThread(result, listener);
    }

    private void notifyResponseOnUIThread(final ConnectionResult result, final ConnectionResultListener listener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.onConnectionResult(result);
            }
        });
    }

    public HandyHttpURLConnection(String url, ExecutorService executorService) {
        this.executorService = executorService;
        this.urlString = url;
    }

    public interface ConnectionResultListener {
        public void onConnectionResult(ConnectionResult result);
    }
}
