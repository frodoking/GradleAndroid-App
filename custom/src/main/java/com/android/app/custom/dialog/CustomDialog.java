package com.android.app.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.android.app.custom.R;
import com.android.app.custom.ScreenUtil;
import com.android.app.custom.view.RoundedRectangleTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义统一风格的dialog,只需要重新填入中间部分 。<p/>
 * （组合方式实现，避免Dialog本身功能的外露造成风格的不统一）
 * Created by xuwei on 2015/5/18.
 */
public class CustomDialog {

    private Dialog dialog;
    private Context context;

    private View rootView;
    private TextView titleTV;
    private RoundedRectangleTextView okTV, neutralTV, cancelTv;

    private View titleDividerV, neutralDividerV, confirmDividerV;
    private LinearLayout containerVG;

    public int minHeight;
    public int maxHeight;
    public int titleHeight;
    public int buttonHeight;

    private int bgColor;
    private int radius;

    public CustomDialog(Context context) {
        this(context, R.style.CustomDialog_Progress);
    }

    public CustomDialog(Context context, int theme) {
        this.context = context;
        dialog = new Dialog(context, theme);

        bgColor = Color.WHITE;
        radius = context.getResources().getDimensionPixelSize(R.dimen.dp_3);

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_layout, null);
        initViews(rootView);

        final int screenWidth = ScreenUtil.getDisplayWidth(context);
        final int screenHeight = ScreenUtil.getDisplayHeight(context);

        final int maxWidth = screenWidth * 4 / 5;
        minHeight = screenHeight / 4;
        maxHeight = screenHeight * 3 / 4;

        titleHeight = context.getResources().getDimensionPixelSize(R.dimen.item_second_height);
        buttonHeight = titleHeight;

        setBackgroundColor(bgColor);
        updateButton(okTV, null, cancelTv);

        dialog.setContentView(rootView, new LinearLayout.LayoutParams(maxWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initViews(View rootView) {
        titleTV = (TextView) rootView.findViewById(R.id.title);
        titleDividerV = rootView.findViewById(R.id.title_divider);

        containerVG = (LinearLayout) rootView.findViewById(R.id.container);

        okTV = (RoundedRectangleTextView) rootView.findViewById(R.id.ok);
        confirmDividerV = rootView.findViewById(R.id.confirm_btn_divider);

        neutralDividerV = rootView.findViewById(R.id.neutral_btn_divider);
        neutralTV = (RoundedRectangleTextView) rootView.findViewById(R.id.neutral);

        cancelTv = (RoundedRectangleTextView) rootView.findViewById(R.id.cancel);
    }

    public View getRootView() {
        return rootView;
    }

    public void setBackgroundColor(int color) {
        setBackgroundColor(color, 255);
    }

    public void setBackgroundColor(int color, int alpha) {
        GradientDrawable drawable = RoundedRectangleTextView.createGradientDrawable(color, radius);
        drawable.setAlpha(alpha);
        rootView.setBackgroundDrawable(drawable);
    }

    private void updateButton(RoundedRectangleTextView okTV, RoundedRectangleTextView neutralTV, RoundedRectangleTextView cancelTv) {
        Resources resources = context.getResources();
        int whiteColor = Color.WHITE;
        int blueColor = resources.getColor(R.color.blue);
        int bluePressedColor = resources.getColor(R.color.light_blue);
        int grayColor = resources.getColor(R.color.general_gray_light);
        if (okTV != null && cancelTv != null) {
            okTV.normalDrawable(whiteColor, whiteColor)
                    .pressedDrawable(grayColor, grayColor)
                    .textColor(blueColor, bluePressedColor, bluePressedColor)
                    .radii(new float[]{0, 0, 0, 0, 0, 0, radius, radius})
                    .update();
            cancelTv.normalDrawable(whiteColor, whiteColor)
                    .pressedDrawable(grayColor, grayColor)
                    .textColor(blueColor, bluePressedColor, bluePressedColor)
                    .radii(new float[]{0, 0, 0, 0, radius, radius, 0, 0})
                    .update();
            if (neutralTV != null) {
                neutralTV.normalDrawable(whiteColor, whiteColor)
                        .pressedDrawable(grayColor, grayColor)
                        .textColor(blueColor, bluePressedColor, bluePressedColor)
                        .radii(new float[]{0, 0, 0, 0, 0, 0, 0, 0})
                        .update();
            }
        } else {
            if (okTV != null) {
                okTV.normalDrawable(whiteColor, whiteColor)
                        .pressedDrawable(grayColor, grayColor)
                        .textColor(blueColor, bluePressedColor, bluePressedColor)
                        .radii(new float[]{0, 0, 0, 0, radius, radius, radius, radius})
                        .update();
            }

            if (cancelTv != null) {
                cancelTv.normalDrawable(whiteColor, whiteColor)
                        .pressedDrawable(grayColor, grayColor)
                        .textColor(blueColor, bluePressedColor, bluePressedColor)
                        .radii(new float[]{0, 0, 0, 0, radius, radius, radius, radius})
                        .update();
            }
        }
    }

    public void setNoTitle() {
        titleTV.setVisibility(View.GONE);
        titleDividerV.setVisibility(View.GONE);
    }

    public void setTitle(String text) {
        titleTV.setVisibility(View.VISIBLE);
        titleDividerV.setVisibility(View.VISIBLE);
        titleTV.setText(text);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        dialog.onRestoreInstanceState(savedInstanceState);
    }

    public Bundle onSaveInstanceState() {
        return dialog.onSaveInstanceState();
    }

    /**
     * 只保留中间填充内容
     */
    public void onlyKeepContainerView() {
        setNoTitle();
        rootView.findViewById(R.id.container_divider).setVisibility(View.GONE);
        rootView.findViewById(R.id.btn_view_group).setVisibility(View.GONE);
    }

    public void measureContainerMinHeight(int height) {
        containerVG.setMinimumHeight(height);
    }

    public void measureContainerMaxHeight(int height) {
        containerVG.getLayoutParams().height = height;
    }

    public void measureContainerDefaultMaxHeight() {
        containerVG.getLayoutParams().height = maxHeight - titleHeight - buttonHeight;
    }

    public void setContainerResId(int resId) {
        View v = LayoutInflater.from(context).inflate(resId, null);
        setContainerView(v);
    }

    public void setContainerView(View v) {
        containerVG.addView(v,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setSingleConfirmBtn() {
        confirmDividerV.setVisibility(View.GONE);

        neutralDividerV.setVisibility(View.GONE);
        neutralTV.setVisibility(View.GONE);

        cancelTv.setVisibility(View.GONE);
        updateButton(okTV, null, null);
    }

    public void setSingleCancelBtn() {
        okTV.setVisibility(View.GONE);
        confirmDividerV.setVisibility(View.GONE);

        neutralDividerV.setVisibility(View.GONE);
        neutralTV.setVisibility(View.GONE);

        updateButton(null, null, cancelTv);
    }

    private void needThreeBtn() {
        okTV.setVisibility(View.VISIBLE);
        confirmDividerV.setVisibility(View.VISIBLE);

        neutralDividerV.setVisibility(View.VISIBLE);
        neutralTV.setVisibility(View.VISIBLE);

        cancelTv.setVisibility(View.VISIBLE);

        updateButton(okTV, neutralTV, cancelTv);
    }

    /**
     * //点击外部不会消失
     *
     * @param cancel 是否消失
     */
    public void setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
    }

    public void setConfirmBtn(String text, View.OnClickListener l) {
        okTV.setText(text);
        okTV.setOnClickListener(l);
    }

    public void setNeutralBtn(String text, View.OnClickListener l) {
        neutralTV.setText(text);
        neutralTV.setOnClickListener(l);

        needThreeBtn();
    }

    public void setCancelBtn(String text, View.OnClickListener l) {
        cancelTv.setText(text);
        cancelTv.setOnClickListener(l);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public Context getContext() {
        return context;
    }

    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    /**
     * 简单消息类弹窗
     */
    public static final class MessageDialog extends CustomDialog {
        private TextView msgTV;

        public MessageDialog(Context context) {
            super(context);

            setTitle("提示");
            //setSingleConfirmBtn();
            measureContainerMinHeight(context.getResources().getDimensionPixelSize(R.dimen.dialog_content_height));

            msgTV = new TextView(getContext());
            msgTV.setGravity(Gravity.CENTER_VERTICAL);
            int padding = (int) getContext().getResources().getDimension(R.dimen.normal_padding_length);
            msgTV.setPadding(padding, 0, padding, 0);

            int textSize = (int) getContext().getResources().getDimension(R.dimen.text_small);
            msgTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            setContainerView(msgTV);
            setCanceledOnTouchOutside(false);
        }

        public void setMsg(String msg) {
            msgTV.setText(msg);
        }
    }

    /**
     * 简单字符列表类弹窗 (只带有取消按钮)
     */
    public static final class ArrayListViewDialog extends CustomDialog {
        private ListView lv;
        private BaseAdapter adapter;

        /**
         * @param context 上下文
         * @param list    数据列表
         *                Default  Gravity.START
         */
        public ArrayListViewDialog(final Context context, final List<String> list) {
            this(context, list, Gravity.START | Gravity.CENTER_VERTICAL);
        }

        /**
         * @param context 上下文
         * @param list    数据列表
         * @param gravity item Gravity
         */
        public ArrayListViewDialog(final Context context, final List<String> list, final int gravity) {
            super(context);
            setSingleCancelBtn();
            setCanceledOnTouchOutside(false);

            final int itemHeight = context.getResources().getDimensionPixelSize(R.dimen.item_second_height);
            final int paddingLR = context.getResources().getDimensionPixelSize(R.dimen.normal_padding_length);

            lv = new ListView(context);
            lv.setDividerHeight(1);
            lv.setSelector(R.drawable.selector_listview_item_bg);
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public String getItem(int position) {
                    return list.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final ViewHolder vh;
                    if (convertView == null) {
                        TextView itemView = new TextView(context);
                        itemView.setTextColor(context.getResources().getColor(R.color.general_black));
                        itemView.setGravity(gravity);
                        itemView.setHeight(itemHeight);
                        itemView.setPadding(paddingLR, 0, paddingLR, 0);

                        convertView = itemView;
                        vh = new ViewHolder();
                        vh.tv = itemView;
                        convertView.setTag(vh);
                    } else {
                        vh = (ViewHolder) convertView.getTag();
                    }

                    vh.tv.setText(getItem(position));
                    return convertView;
                }

                class ViewHolder {
                    TextView tv;
                }
            };
            lv.setAdapter(adapter);

            int totalSize = titleHeight + buttonHeight + itemHeight * list.size();
            if (totalSize > maxHeight) {
                measureContainerMinHeight(maxHeight);
            }

            setContainerView(lv);

            setCancelBtn("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        @Override
        @Deprecated
        public void setConfirmBtn(String text, View.OnClickListener l) {
            super.setConfirmBtn(text, l);
        }

        public final void setOnItemClickListener(final AdapterView.OnItemClickListener l) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismiss();
                    l.onItemClick(parent, view, position, id);
                }
            });
        }
    }

    /**
     * 带有单选框的列表
     */
    public static final class SingleChoiceListViewDialog extends CustomDialog {
        private ListView listView;
        private List<Pair<Pair<Integer, String>, Boolean>> pairs;
        private CheckedListViewAdapter adapter;

        /**
         * @param context         上下文
         * @param list            数据列表(包含默认项目，也可以没有，但是不可以多个选择)
         * @param confirmListener 默认选中项
         */
        public SingleChoiceListViewDialog(final Context context, final List<Pair<Pair<Integer, String>, Boolean>> list, final ConfirmListener confirmListener) {
            super(context);
            this.pairs = list;

            setCanceledOnTouchOutside(false);
            int itemHeight = context.getResources().getDimensionPixelSize(R.dimen.item_second_height);

            listView = new ListView(context);
            listView.setDividerHeight(1);
            listView.setSelector(R.drawable.selector_listview_item_bg);

            adapter = new CheckedListViewAdapter(context, pairs);
            listView.setAdapter(adapter);
            setSingleCancelBtn();

            int totalSize = titleHeight + buttonHeight + itemHeight * list.size();
            if (totalSize > maxHeight) {
                measureContainerMaxHeight(maxHeight);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setChooseIndex(position);
                    confirmListener.onConfirmIndex(position);
                    dismiss();
                }
            });

            setContainerView(listView);

            setCancelBtn("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public void setChooseIndex(int index) {
            for (Pair<Pair<Integer, String>, Boolean> pair : pairs) {
                pair.second = pair.first.first == index;
            }
        }

        public static List<Pair<Pair<Integer, String>, Boolean>> convertListToPairList(List<String> list, int selection){
            List<Pair<Pair<Integer, String>, Boolean>> pairList = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                Pair<Integer, String> pairDetail = new Pair<>(i, list.get(i));
                Pair<Pair<Integer, String>, Boolean> pair = new Pair<>(pairDetail, selection == i);
                pairList.add(pair);
            }
            return  pairList;
        }

        public interface ConfirmListener {
            /**
             * @param position 选择位置
             */
            void onConfirmIndex(int position);
        }
    }

    /**
     * 带有多选框的列表
     */
    public static final class MultiChoiceListViewDialog extends CustomDialog {
        private ListView listView;
        private CheckedListViewAdapter adapter;
        private ConfirmListener confirmListener;

        /**
         * @param context 上下文
         * @param list    数据列表
         * @param l       确定返回接口
         */
        public MultiChoiceListViewDialog(final Context context, final List<Pair<Pair<Integer, String>, Boolean>> list, ConfirmListener l) {
            super(context);
            this.confirmListener = l;
            setCanceledOnTouchOutside(false);

            final int itemHeight = context.getResources().getDimensionPixelSize(R.dimen.item_second_height);

            listView = new ListView(context);
            listView.setDividerHeight(1);
            listView.setSelector(R.drawable.selector_listview_item_bg);

            adapter = new CheckedListViewAdapter(context, list);
            listView.setAdapter(adapter);
            setContainerView(listView);

            int totalSize = titleHeight + buttonHeight + itemHeight * list.size();
            if (totalSize > maxHeight) {
                measureContainerMaxHeight(maxHeight);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pair<Pair<Integer, String>, Boolean> item = adapter.getItem(position);
                    item.second = !item.second;
                    updateList();
                }
            });

            setConfirmBtn("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmListener.onConfirmIndexes(list);
                    dismiss();
                }
            });
            setCancelBtn("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public void updateList() {
            adapter.notifyDataSetChanged();
        }

        public interface ConfirmListener {
            /**
             * @param chooseList 选择后的状态列表
             */
            void onConfirmIndexes(List<Pair<Pair<Integer, String>, Boolean>> chooseList);
        }
    }

    private static class CheckedListViewAdapter extends BaseListViewAdapter<Pair<Pair<Integer, String>, Boolean>> {

        public CheckedListViewAdapter(Context context, List<Pair<Pair<Integer, String>, Boolean>> list) {
            super(context, R.layout.listview_item_with_checkable, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                convertView = inflateItemView();
                vh = new ViewHolder();
                vh.textView = (TextView) convertView.findViewById(R.id.text_view);
                vh.checkBox = (CheckedTextView) convertView.findViewById(R.id.checkbox);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Pair<Pair<Integer, String>, Boolean> item = getItem(position);
            vh.textView.setText(item.first.second);
            vh.checkBox.setChecked(item.second);
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            CheckedTextView checkBox;
        }
    }

    public static class Pair<F, S> {
        public F first;
        public S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("[id:%s, text:%s]", first, second);
        }
    }

    /**
     * 带有自动消失功能的弹窗(类似Toast)
     */
    public static final class AutoDismissDialog extends CustomDialog {

        private ImageView closeBtn;
        private TextView msgTV;

        public AutoDismissDialog(Context context) {
            super(context);
            onlyKeepContainerView();
            setCanceledOnTouchOutside(true);
            getRootView().setBackgroundColor(context.getResources().getColor(R.color.general_black));
            int padding = (int) getContext().getResources().getDimension(R.dimen.normal_padding_length);

            FrameLayout frameLayout = new FrameLayout(context);

            closeBtn = new ImageView(context);
            closeBtn.setImageResource(R.drawable.ic_close);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.END | Gravity.TOP;
            params.rightMargin = padding / 4;
            params.topMargin = padding / 4;
            frameLayout.addView(closeBtn, params);

            msgTV = new TextView(getContext());
            msgTV.setGravity(Gravity.CENTER);
            msgTV.setTextColor(context.getResources().getColor(R.color.general_white));

            msgTV.setPadding(padding, padding, padding, padding);
            FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLayout.addView(msgTV, params2);

            measureContainerMinHeight(context.getResources().getDimensionPixelSize(R.dimen.dialog_content_height));
            setContainerView(frameLayout);

            setBackgroundColor(Color.BLACK, 192);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        public void setMsg(String msg) {
            msgTV.setText(msg);
        }

        public void setMsg(Spanned msgSpanned) {
            msgTV.setText(msgSpanned);
        }

        public void noNeedCloseButton() {
            closeBtn.setVisibility(View.GONE);
        }

        @Override
        public void show() {
            super.show();
            getRootView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isShowing()) {
                        dismiss();
                    }
                }
            }, 4000);
        }
    }
}
