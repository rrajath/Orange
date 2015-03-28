package com.rrajath.orange.data;

import lombok.Data;

/**
 * Created by rrajath on 3/28/15.
 */

@Data
public class NavDrawerItem {
    private int iconId;
    private String title;

    public NavDrawerItem(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }
}
