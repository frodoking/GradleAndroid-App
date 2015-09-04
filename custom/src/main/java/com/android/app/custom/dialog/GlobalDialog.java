package com.android.app.custom.dialog;

import android.content.Context;
import android.view.View;

/**
 * Created by frodo on 2015/4/21.
 */
public class GlobalDialog {

    public static void showDialog(Context context) {
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

}
