package com.rrajath.orange.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rrajath.orange.R;

/**
 * Created by rrajath on 4/8/15.
 */
public class Animations {

    public static void fadeIn(Context context, RecyclerView.ViewHolder viewHolder) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(1000);
        viewHolder.itemView.startAnimation(animation);
    }

    public static void decelerate(Context context, RecyclerView.ViewHolder viewHolder) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.decelerate);
        animation.setDuration(1000);
        viewHolder.itemView.startAnimation(animation);
    }
}
