package com.example.masato.githubfeed.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/31.
 */

public class ConnectionResult {

    public byte[] bodyBytes;
    public int responseCode;
    public Map<String, List<String>> header;
    public boolean isConnectionSuccessful;

    public String getBodyString() {
        String string = "";
        try {
            string = new String(this.bodyBytes, 0, this.bodyBytes.length, "utf-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return string;
    }

}
