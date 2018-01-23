package com.example.masato.githubfeed.model;

import android.content.Context;
import android.content.res.Resources;

import com.example.masato.githubfeed.R;

/**
 * Created by Masato on 2018/01/20.
 */

public class FeedTitle {

    private static final String[] IDENTIFIERS = new String[] {
            "timeline_url",
            "current_user_public_url",
            "current_user_url",
            "current_user_actor_url",
            "current_user_organization_url"
    };
    private static final int[] TITLE = new int[] {
            R.string.feed_timeline,
            R.string.feed_current_user_public,
            R.string.feed_current_user,
            R.string.feed_current_user_action,
            R.string.feed_current_user_organization
    };

    public static String getTitleFromIdentifier(String identifier, Resources resources) {
        for (int i = 0;i<IDENTIFIERS.length;i++) {
            if (IDENTIFIERS[i].equals(identifier)) {
                return resources.getString(TITLE[i]);
            }
        }
        return identifier;
    }

    private FeedTitle() {}
}
