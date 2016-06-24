package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinhe on 2016/6/24.
 *
 */
public class SCOSHelper extends Activity{

    public int imageId[] = {R.drawable.protocol,R.drawable.about,R.drawable.phone,R.drawable.message,R.drawable.email};
    public String textItem[] = {"用户协议","关于系统","电话帮助","短信帮助","邮件帮助"};
    public List<Item> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper);
        GridView gridView = (GridView)findViewById(R.id.helper_grid);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getBaseContext(),mItems);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(onItemClickListener);
        initData();

    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i == 0){
                //用户协议
                Toast.makeText(getBaseContext(),"点击用户协议",Toast.LENGTH_SHORT).show();
            }if(i==1){
                //关于系统

            }if(i == 2){
                //电话帮助
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:"+"5554"));
                SCOSHelper.this.startActivity(intent);
            }if(i== 3){
                //短信帮助
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("18569030124",null,"test scos helper",null,null);
                Toast.makeText(getBaseContext(),"求助短信发送成功",Toast.LENGTH_SHORT).show();
            }if(i==4){
                //邮件帮助
            }

        }
    };
    private void initData(){
        for(int i = 0;i<imageId.length;i++){
            Item object = new Item();
            object.resId = imageId[i];
            object.text = textItem[i];
            mItems.add(object);
        }
    }

}
 class Item{
    public String text;
    public int resId;
}
