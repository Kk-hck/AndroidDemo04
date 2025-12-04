package com.example.android.notepad;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

// AppWidget.java
public class AppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // 为每个小部件实例更新界面
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // 创建 PendingIntent，点击小部件时启动主应用
        Intent intent = new Intent(context, NotesList.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 构建 RemoteViews 对象
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // 从ContentProvider获取最新的一条笔记
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    NotePad.Notes.CONTENT_URI,
                    new String[]{NotePad.Notes.COLUMN_NAME_TITLE,NotePad.Notes.COLUMN_NAME_NOTE},
                    null,
                    null,
                    NotePad.Notes.DEFAULT_SORT_ORDER + " LIMIT 1"
            );

            if (cursor != null && cursor.moveToFirst()) {
                String noteTitle = cursor.getString(0);   // 获取标题
                String noteContent = cursor.getString(1); // 获取内容
                views.setTextViewText(R.id.widget_text, noteContent);
                views.setTextViewText(R.id.widget_title, noteTitle);
            } else {
                views.setTextViewText(R.id.widget_text, "暂无笔记");
                views.setTextViewText(R.id.widget_title, "便签应用");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // 更新小部件
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // 添加一个静态方法用于通知小部件更新
    public static void notifyWidgetUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, AppWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        // 调用 onUpdate 方法更新所有小部件实例
        if (appWidgetIds.length > 0) {
            AppWidget provider = new AppWidget();
            provider.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }



}

