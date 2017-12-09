package com.jumbomob.invistoo.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jumbomob.invistoo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maiko.trindade
 * @since 30/07/2016
 */
public class ChartUtil {

    public static final int[] MATERIAL_THEME = {
            R.color.material_red_300,
            R.color.material_light_green_300,
            R.color.material_blue_200,
            R.color.material_amber_200,
            R.color.material_deep_orange_200,
            R.color.material_deep_purple_200,
            R.color.material_blue_grey_200,
            R.color.material_yellow_A200,
            R.color.material_orange_A200,
            R.color.material_light_blue_A200,
            R.color.material_purple_A200,
            R.color.material_cyan_A200,
            R.color.material_brown_600,
            R.color.material_grey_500,
            R.color.material_teal_A200,
            R.color.material_pink_A200,
            R.color.material_green_A200,
            R.color.material_lime_A200,
            R.color.material_indigo_A200
    };

    public static List<Integer> getMaterialTheme() {
        List<Integer> colors = new ArrayList<>();
        final Context baseContext = InvistooApplication.getInstance().getBaseContext();
        for (int color : MATERIAL_THEME) {
            colors.add(ContextCompat.getColor(baseContext, color));
        }
        return colors;
    }

}
