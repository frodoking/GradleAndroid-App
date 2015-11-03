package com.android.app.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.app.custom.dialog.CustomDialog.*;

/**
 * Created by frodo on 2015/4/21.
 */
public class GlobalDialog {

    public static void showMsgDialog(Context context) {
        final MessageDialog messageDialog =  new MessageDialog(context);
        messageDialog.setMsg("是否接受文件?");
        messageDialog.setConfirmBtn("是", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setCancelBtn("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.dismiss();
            }
        });
        messageDialog.setCancelable(false);
        messageDialog.setCanceledOnTouchOutside(false);
        messageDialog.show();
    }

    public static void showArrayListViewDialog(final Context context) {
        final List<String> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add("item " + i);
        }

        final ArrayListViewDialog dialog = new ArrayListViewDialog(context, list);
        dialog.setTitle("Title");
        dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "当前点击了" + list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public static void showToastDialog(Context context) {
        final AutoDismissDialog dialog = new AutoDismissDialog(context);
        dialog.setMsg("this is a  auto dismiss dialog");
        dialog.show();
    }

    public static void showSingleChoiceListViewDialog(final Context context) {
        int size = 10;
        final List<Pair<Pair<Integer, String>, Boolean>>  entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Pair<Integer, String> pairDetail = new Pair<>(i, "item " + i);
            Pair<Pair<Integer, String>, Boolean> pair = new Pair<>(pairDetail, false);
            entries.add(pair);
        }

        SingleChoiceListViewDialog dialog = new SingleChoiceListViewDialog(context, entries, new SingleChoiceListViewDialog.ConfirmListener() {
            @Override
            public void onConfirmIndex(int position) {
                Toast.makeText(context, "当前点击了" + entries.get(position).first.second, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setTitle("Title");
        dialog.show();
    }

    public static void showMultiChoiceListViewDialog(final Context context) {
        int size = 10;
        List<Pair<Pair<Integer, String>, Boolean>>  entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Pair<Integer, String> pairDetail = new Pair<>(i, "item " + i);
            Pair<Pair<Integer, String>, Boolean> pair = new Pair<>(pairDetail, false);
            entries.add(pair);
        }

        MultiChoiceListViewDialog dialog = new MultiChoiceListViewDialog(context, entries, new MultiChoiceListViewDialog.ConfirmListener() {
            @Override
            public void onConfirmIndexes(List<Pair<Pair<Integer, String>, Boolean>> chooseList) {
                StringBuilder sb = new StringBuilder();
                boolean isSelected = false;
                if (chooseList != null && !chooseList.isEmpty()) {
                    for (Pair<Pair<Integer, String>, Boolean> pair : chooseList) {
                        isSelected |= pair.second;
                        if (pair.second) {
                            sb.append(pair.first.second).append("、");
                        }
                    }
                }

                String text = "未选择";
                if (isSelected) {
                    text = "你总共选择了" + sb.substring(0, sb.length() - 1);
                }

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setTitle("Title");
        dialog.show();
    }


}
