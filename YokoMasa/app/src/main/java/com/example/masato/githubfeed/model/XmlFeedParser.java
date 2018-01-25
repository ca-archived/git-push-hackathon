package com.example.masato.githubfeed.model;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/20.
 */

public class XmlFeedParser {

    private static XmlPullParser xmlPullParser;

    public static List<FeedEntry> parse(String feedXml) {
        List<FeedEntry> list = new ArrayList<>();
        try {
            if (xmlPullParser == null) {
                initXmlPullParser();
            }
            xmlPullParser.setInput(new StringReader(feedXml));
            FeedEntry feedEntry = findFeedEntry(xmlPullParser);
            while (feedEntry != null) {
                list.add(feedEntry);
                feedEntry = findFeedEntry(xmlPullParser);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static FeedEntry findFeedEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("entry")) {
                    parser.next();
                    return mapToFeedEntryObject(parser);
                }
            }
            eventType = parser.next();
        }
        return null;
    }

    private static FeedEntry mapToFeedEntryObject(XmlPullParser parser) throws XmlPullParserException, IOException {
        FeedEntry feedEntry = new FeedEntry();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_TAG || !parser.getName().equals("entry")) {
            if (eventType == XmlPullParser.START_TAG) {
                String tagName = xmlPullParser.getName();
                if (tagName.equals("title")) {
                    feedEntry.title = getContent(parser);
                } else if (tagName.equals("thumbnail")) {
                    feedEntry.thumbnailUrl = parser.getAttributeValue(null, "url");
                } else if (tagName.equals("author")) {
                    feedEntry.name = getAuthorName(parser);
                }
            }
            eventType = parser.next();
        }
        return feedEntry;
    }

    private static String getContent(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.next();
        while (eventType != XmlPullParser.TEXT) {
            eventType = parser.next();
        }
        return parser.getText();
    }

    private static String getAuthorName(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.next();
        while (eventType != XmlPullParser.END_TAG || !parser.getName().equals("author")) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equals("name")) {
                    return getContent(parser);
                }
            }
            eventType = parser.next();
        }
        return "";
    }

    private static void initXmlPullParser() throws XmlPullParserException{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xmlPullParser = factory.newPullParser();
    }
}
