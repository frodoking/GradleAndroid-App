package com.android.app.custom.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frodo on 2015/4/21.
 */
public class GlobalDialog {

    public static void showMsgDialog(Context context) {
        final CustomDialog.MessageDialog messageDialog =  new CustomDialog.MessageDialog(context);
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

        final CustomDialog.ArrayListViewDialog dialog = new CustomDialog.ArrayListViewDialog(context, list);
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
        final CustomDialog.AutoDismissDialog dialog = new CustomDialog.AutoDismissDialog(context);
        dialog.setMsg("this is a  auto dismiss dialog");
        dialog.show();
    }


}
