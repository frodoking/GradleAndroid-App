package com.android.app.custom.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.app.custom.R;


/**
 * Created by frodo on 2015/9/17. Rounded Rectangle TextView
 */
public class RoundedRectangleTextView extends TextView {

    private int radius;
    private int strokeWidth;
    private int solidColor;
    private int strokeColor;

    private GradientDrawable normalDrawable;
    private GradientDrawable focusedDrawable;
    private GradientDrawable pressedDrawable;
    private GradientDrawable selectedDrawable;
    private GradientDrawable unableDrawable;

    private StateListDrawable stateListDrawable;

    private int normalTextColor;
    private int pressedTextColor;
    private int focusedTextColor;
    private int selectedTextColor;
    private int unableTextColor;

    public RoundedRectangleTextView(Context context) {
        this(context, null);
    }

    public RoundedRectangleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        strokeWidth = getResources().getDimensionPixelSize(R.dimen.px_1) * 2;
        solidColor = getResources().getColor(R.color.transparent);
        strokeColor = getResources().getColor(R.color.general_black);
        radius = getResources().getDimensionPixelSize(R.dimen.dp_3);

        normalTextColor = getResources().getColor(R.color.general_black);
        pressedTextColor = normalTextColor;
        focusedTextColor = normalTextColor;
        unableTextColor = normalTextColor;

        normalDrawable = new GradientDrawable();
        focusedDrawable = new GradientDrawable();
        pressedDrawable = new GradientDrawable();
        selectedDrawable = new GradientDrawable();
        unableDrawable = new GradientDrawable();

        normalDrawable(strokeColor, strokeWidth, solidColor);
        focusedDrawable(strokeColor, strokeWidth, solidColor);
        pressedDrawable(strokeColor, strokeWidth, solidColor);
        selectedDrawable(strokeColor, strokeWidth, solidColor);
        unableDrawable(strokeColor, strokeWidth, getResources().getColor(R.color.general_gray_deep));
        radius(radius);

        stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focusedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_window_focused}, unableDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);

        update();
    }

    public RoundedRectangleTextView normalDrawable(int solidColor) {
        return drawable(normalDrawable, strokeColor, 0, solidColor);
    }

    public RoundedRectangleTextView normalDrawable(int strokeColor, int solidColor) {
        return drawable(normalDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView normalDrawable(int strokeColor, int strokeWidth, int solidColor) {
        return drawable(normalDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView focusedDrawable(int solidColor) {
        return drawable(focusedDrawable, strokeColor, 0, solidColor);
    }

    public RoundedRectangleTextView focusedDrawable(int strokeColor, int solidColor) {
        return drawable(focusedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView focusedDrawable(int strokeColor, int strokeWidth, int solidColor) {
        return drawable(focusedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView pressedDrawable(int solidColor) {
        return drawable(pressedDrawable, strokeColor, 0, solidColor);
    }

    public RoundedRectangleTextView pressedDrawable(int strokeColor, int solidColor) {
        return drawable(pressedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView pressedDrawable(int strokeColor, int strokeWidth, int solidColor) {
        return drawable(pressedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView selectedDrawable(int solidColor) {
        return drawable(selectedDrawable, strokeColor, 0, solidColor);
    }

    public RoundedRectangleTextView selectedDrawable(int strokeColor, int solidColor) {
        return drawable(selectedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView selectedDrawable(int strokeColor, int strokeWidth, int solidColor) {
        return drawable(selectedDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView unableDrawable(int solidColor) {
        return drawable(unableDrawable, strokeColor, 0, solidColor);
    }

    public RoundedRectangleTextView unableDrawable(int strokeColor, int solidColor) {
        return drawable(unableDrawable, strokeColor, strokeWidth, solidColor);
    }

    public RoundedRectangleTextView unableDrawable(int strokeColor, int strokeWidth, int solidColor) {
        return drawable(unableDrawable, strokeColor, strokeWidth, solidColor);
    }

    private RoundedRectangleTextView drawable(GradientDrawable drawable, int strokeColor, int strokeWidth, int solidColor) {
        setDrawable(drawable, strokeColor, strokeWidth, solidColor, radius);
        return this;
    }

    public static void setDrawable(GradientDrawable drawable, int strokeColor, int strokeWidth, int solidColor, int radius) {
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(radius);
        drawable.setColor(solidColor);
    }

    public static GradientDrawable createGradientDrawable(int solidColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        drawable.setColor(solidColor);
        return drawable;
    }

    public static GradientDrawable createGradientDrawable(int strokeColor, int strokeWidth, int solidColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        setDrawable(drawable, strokeColor, strokeWidth, solidColor, radius);
        return drawable;
    }

    public RoundedRectangleTextView radius(int radius) {
        this.radius = radius;
        normalDrawable.setCornerRadius(radius);
        focusedDrawable.setCornerRadius(radius);
        pressedDrawable.setCornerRadius(radius);
        selectedDrawable.setCornerRadius(radius);
        unableDrawable.setCornerRadius(radius);
        return this;
    }

    public RoundedRectangleTextView radii(float[] radii) {
        normalDrawable.setCornerRadii(radii);
        focusedDrawable.setCornerRadii(radii);
        pressedDrawable.setCornerRadii(radii);
        selectedDrawable.setCornerRadii(radii);
        unableDrawable.setCornerRadii(radii);
        return this;
    }

    public RoundedRectangleTextView textColor(int normal) {
        int grayColor = getResources().getColor(R.color.gray);
        return textColor(normal, grayColor, grayColor);
    }

    public RoundedRectangleTextView textColor(int normal, int pressed, int unable) {
        return textColor(normal, pressed, pressed, pressed, unable);
    }

    public RoundedRectangleTextView textColor(int normal, int pressed, int focused, int selected, int unable) {
        this.normalTextColor = normal;
        this.pressedTextColor = pressed;
        this.focusedTextColor = focused;
        this.selectedTextColor = selected;
        this.unableTextColor = unable;
        return this;
    }

    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    private ColorStateList createColorStateList(int normal, int pressed, int focused, int selected, int unable) {
        int[] colors = new int[]{pressed, focused, selected, normal, pressed, focused, selected, unable, normal};
        int[][] states = new int[9][];
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled, android.R.attr.state_selected};
        states[3] = new int[]{android.R.attr.state_enabled};
        states[4] = new int[]{android.R.attr.state_pressed};
        states[5] = new int[]{android.R.attr.state_focused};
        states[6] = new int[]{android.R.attr.state_selected};
        states[7] = new int[]{android.R.attr.state_window_focused};
        states[8] = new int[]{};
        return new ColorStateList(states, colors);
    }

    public void update() {
        fillViewBackgroundDrawable(this, stateListDrawable);
        setTextColor(createColorStateList(normalTextColor, pressedTextColor, focusedTextColor, selectedTextColor, unableTextColor));
    }

    /**
     * 解决AbsList的选中情况
     * @param selected 是否选中
     */
    public void setSelectedByBadWay(boolean selected) {
        if (selected) {
            setTextColor(pressedTextColor);
            fillViewBackgroundDrawable(this, selectedDrawable);
        } else {
            setTextColor(normalTextColor);
            fillViewBackgroundDrawable(this, normalDrawable);
        }
    }

    /**
     * 解决enable 和 selected的重叠逻辑
     * @param enabled 是否可用
     */
    public void setEnabledByBadWay(boolean enabled) {
        setEnabled(enabled);
        if (enabled) {
            setTextColor(normalTextColor);
            fillViewBackgroundDrawable(this, normalDrawable);
        } else {
            setTextColor(unableTextColor);
            fillViewBackgroundDrawable(this, unableDrawable);
        }
    }


    public static void fillViewBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
