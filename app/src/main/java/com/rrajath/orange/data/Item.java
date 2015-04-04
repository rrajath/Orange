package com.rrajath.orange.data;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by rrajath on 3/18/15.
 */

@Parcel
public class Item {
    public long id;
    boolean deleted;
    String type;
    public String username;
    public long time;
    String text;
    boolean dead;
    long parent;
    ArrayList<Long> kids;
    public String url;
    public long score;
    public String title;
    ArrayList<Long> parts;
    public int descendants;
}
