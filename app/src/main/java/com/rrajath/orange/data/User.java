package com.rrajath.orange.data;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by rrajath on 3/18/15.
 */

@Data
public class User {
    long id;
    long delay;
    long created;
    long karma;
    String about;
    ArrayList<Long> submitted;
}
