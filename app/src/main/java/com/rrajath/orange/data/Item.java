package com.rrajath.orange.data;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by rrajath on 3/18/15.
 */

@Data
public class Item {
    long id;
    boolean deleted;
    Enum type;
    String username;
    long time;
    String text;
    boolean dead;
    long parent;
    ArrayList<Long> kids;
    String url;
    long score;
    String title;
    ArrayList<Long> parts;
    long descendants;
}
