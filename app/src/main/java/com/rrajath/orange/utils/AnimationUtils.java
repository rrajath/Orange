package com.rrajath.orange.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;

/**
 * Created by rrajath on 4/8/15.
 */
public class AnimationUtils {

    public static void decelerate(Context context, RecyclerView.ViewHolder viewHolder) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(1000);
        viewHolder.itemView.startAnimation(animation);
    }
}
