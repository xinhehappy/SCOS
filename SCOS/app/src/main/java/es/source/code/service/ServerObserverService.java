package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import es.source.code.activity.FoodsOnService;

/**
 * Created by adam on 2016/6/27.
 */
public class ServerObserverService extends Service{
    @Override
    public void onCreate() {
        super.onCreate();
        for(int i=0 ;  i<10;i++){
            FoodsOnService item = new FoodsOnService();
            item.setFoodName("菜名："+i);
            item.setFoodNum(i);
            item.setFoodPrice(i);
            mFoodOnService.add(item);
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serverMsger.getBinder();
    }

    public Handler cMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //关闭模拟任务的线程

                    break;
                case 1:
                    new Thread(new MyThread(msg)).start();
                    break;
                case 10:
                    break;
            }
        }
    };

    private Messenger serverMsger = new Messenger(cMessageHandler);
    public String MY_PKG_NAME = "es.source.code.activity";
    /**
     * 模拟服务器传回菜品库存信息。
     * 菜名和库存
     */
    public List<FoodsOnService> mFoodOnService = new ArrayList<>();

    public class MyThread extends Thread{
        private Message myMsg;
        public MyThread (Message msg){
            this.myMsg = msg;
        }
        @Override
        public void run() {

            while (true){
                try{
                    Thread.sleep(300);
                    if(mFoodOnService != null){//接收到新数据
                        ActivityManager activityManager = (ActivityManager) ServerObserverService.this.getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(100);
                        for(ActivityManager.RunningTaskInfo info:list){
                            if(info.baseActivity.getPackageName().equals(MY_PKG_NAME)){
                                //SCOS应用正在运行
                                Message replayMsg =  new Message();
                                replayMsg.what = 10;
                                replayMsg.obj = mFoodOnService;
                                try{
                                    myMsg.replyTo.send(replayMsg);
                                }catch (RemoteException e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
//class FoodsOnService {
//    String foodName;//菜名
//    int foodNum;//库存数量
//}
