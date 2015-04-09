package com.rrajath.orange.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;

import com.rrajath.orange.R;

/**
 * Created by rrajath on 4/8/15.
 */
public class AnimationUtils {

    public static void decelerate(Context context, RecyclerView.ViewHolder viewHolder) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.decelerate);
        viewHolder.itemView.startAnimation(animation);
    }
}
