package jp.example.ghclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class baseAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layoutID;
    private String[] eventDate;
    private String[] userName;
    private String[] repoName;
    private String[] actionType;
    private String[] eventType;
    private String[] uniqueName;
    private int eventLength;
    private Bitmap bitMap;
    private ViewHolder holder;


    static class ViewHolder {
        TextView main;
        TextView date;
        ImageView icon;
    }

    public baseAdapter(Context context, int itemLayoutId, int eventLength, String[] eventDate,
                       String[] userName, String[] repoName, String[] actionType ,String[] eventType, String[] uniqueName){
        this.context = context;
        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        this.eventDate = eventDate;
        this.userName = userName;
        this.repoName = repoName;
        this.actionType = actionType;
        this.eventLength = eventLength;
        this.eventType = eventType;
        this.uniqueName = uniqueName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
        }
        holder = new ViewHolder();
        holder.main = convertView.findViewById(R.id.item_main);
        holder.date = convertView.findViewById(R.id.item_date);
        holder.icon = convertView.findViewById(R.id.item_icon);
        convertView.setTag(holder);

        File file = new File(context.getCacheDir(), userName[position].toLowerCase()+".bmp");
        try {
            InputStream is = new FileInputStream(file);
            bitMap = BitmapFactory.decodeStream(new BufferedInputStream(is));
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.icon.setImageBitmap(bitMap);

        setList(position);

        return convertView;
    }
    //  ListViewに表示するデータ(文章)を作成し、セットする
    protected void setList(int position) {
        int i = position;
        if (eventType[i].equals("WatchEvent")) {
            holder.main.setText(userName[i]+" starred "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if (eventType[i].equals("MemberEvent")) {
            holder.main.setText(userName[i]+" "+actionType[i]+" "+uniqueName[i]+" as a collaborator "+repoName[i]);
            setUrl(userName[i], uniqueName[i], repoName[i]);
        } else if (eventType[i].equals("CreateEvent")) {
            holder.main.setText(userName[i]+" created repository "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if (eventType[i].equals("CommitCommentEvent")) {
            holder.main.setText(userName[i]+" commented "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if (eventType[i].equals("DeleteEvent")) {
            holder.main.setText(userName[i]+" deleted "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if (eventType[i].equals("ForkEvent")) {
            holder.main.setText(userName[i]+" forked "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if (eventType[i].equals("PublicEvent")) {
            holder.main.setText(userName[i] + " has open sourced " + repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else if(eventType[i].equals("InstallationRepositories")){
            holder.main.setText(userName[i]+" changed "+repoName[i]);
            setUrl(userName[i], repoName[i]);
        } else {
            holder.main.setText(" 対応してないよ！ ");
        }
        holder.date.setText(eventDate[i]);

    }
    public void setUrl(String str1, String str2){
        final String url1,url2;
        Pattern pattern1,pattern2;
        Linkify.TransformFilter tfFilter1,tfFilter2;

        pattern1 = Pattern.compile(str1);
        url1 = "profile://xmz?username="+str1;
        tfFilter1 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url1;
            }
        };
        Linkify.addLinks(holder.main, pattern1, url1, null, tfFilter1);

        pattern2 = Pattern.compile(str2);
        String[] name = str2.split("/");
        url2 = "profile://xmz?username="+name[1];
        tfFilter2 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url2;
            }
        };
        Linkify.addLinks(holder.main, pattern2, url2, null, tfFilter2);

    }
    public void setUrl(String str1, String str2, String str3){
        final String url1,url2,url3;
        Pattern pattern1,pattern2,pattern3;
        Linkify.TransformFilter tfFilter1,tfFilter2,tfFilter3;

        pattern1 = Pattern.compile(str1);
        url1 = "profile://xmz?username="+str1;
        tfFilter1 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url1;
            }
        };
        Linkify.addLinks(holder.main, pattern1, url1, null, tfFilter1);

        pattern2 = Pattern.compile(str2);
        url2 = "profile://xmz?username="+str2;
        tfFilter2 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url2;
            }
        };
        Linkify.addLinks(holder.main, pattern2, url2, null, tfFilter2);

        pattern3 = Pattern.compile(str3);
        String[] name = str3.split("/");
        url3 = "profile://xmz?username="+name[1];
        tfFilter3 = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url3;
            }
        };
        Linkify.addLinks(holder.main, pattern3, url3, null, tfFilter3);

    }

    @Override
    public int getCount() {
        return eventLength;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}