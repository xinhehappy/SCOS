package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.source.code.service.UpdateService;

/**
 *
 * Created by adam on 2016/6/27.
 */
public class DeviceStartedListener extends BroadcastReceiver{
   private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
   private static final String TAG = "DeviceStartedListener";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Boot this system,DeviceStartedListener onReceive()");
        if(intent.getAction().equals(ACTION_BOOT)){
            //启动UpdateService服务
            Intent updateServiceIntent = new Intent(context, UpdateService.class);
            context.startService(updateServiceIntent);
        }
    }
}
