package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.FoodsOnService;
import es.source.code.activity.R;

/**
 * Created by adam on 2016/6/27.
 */
public class UpdateService extends IntentService {
    public List<FoodsOnService> mFoodOnService = new ArrayList<>();
    @Override
    protected void onHandleIntent(Intent intent) {
        // IntentService会使用单独的线程来执行该方法的代码
        /**
         * 模拟检查服务器是否有菜品种类更新信息
         */
        FoodsOnService item = new FoodsOnService();
        item.setFoodPrice(10);
        item.setFoodNum(4);
        item.setFoodName("锅包肉");
        item.setType("热菜");
        mFoodOnService.add(item);

        if(mFoodOnService != null){
            //发送状态栏通知
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            int icon = R.drawable.help;
            CharSequence tickerText = "hello";
            long when = System.currentTimeMillis();
            Notification notification = new Notification(icon,tickerText,when);

            Context context = getApplicationContext();
            CharSequence contentTitle = "notification";
            FoodsOnService foodItem = mFoodOnService.get(0);
            CharSequence contentText = "新品上架:"+mFoodOnService.get(0).getFoodName()
                    +"价格："+mFoodOnService.get(0).getFoodPrice()+"类型："+mFoodOnService.get(0).getType();

            Intent notificationIntent = new Intent(getBaseContext(), FoodDetailed.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("newFood",foodItem);
            notificationIntent.putExtras(bundle);


            PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,notificationIntent,0);
            Notification.Builder builder= new Notification.Builder(getBaseContext());
            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(false);
            builder.setTicker("notification");
            builder.setContentText(contentText);
            builder.setSmallIcon(icon);
            builder.setContentTitle(contentTitle);
            builder.setOngoing(true);
//            builder.setNumber(100);
//            builder.build();
            notification = builder.getNotification();
            notificationManager.notify(11,notification);

        }
    }
    public UpdateService(){
        super("UpdateService");
    }
}
