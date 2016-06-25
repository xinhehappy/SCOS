package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.source.code.model.User;

/**
 * Created by xinhe on 2016/6/11.
 *
 */
public class MainScreen extends Activity {


    public GridView gridView;
    public boolean isMenuHide = false;
    /**
     * 当前用户
     */
    public User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        gridView = (GridView)findViewById(R.id.menu_grid);

        Intent intent = getIntent();
        String value = intent.getStringExtra("value");


        //从首页进入，或者从注册界面返回
        if("FromEntry".equals(value) || "Return".equals(value)){
            gridView.setAdapter(new GridViewAdapter(getBaseContext()));
            gridView.setOnItemClickListener(new GridViewListener());
            user = null;
        }else if("LoginSuccess".equals(value)){
            gridView.setAdapter(new GridViewAdapter(getBaseContext(),isMenuHide));
            gridView.setOnItemClickListener(new GridViewListener());
            //获取传递过来的用户
            user = (User)intent.getSerializableExtra("loginUser");
        }else if("RegisterSuccess".equals(value)){
            gridView.setAdapter(new GridViewAdapter(getBaseContext(),isMenuHide));
            gridView.setOnItemClickListener(new GridViewListener());
            //获取传递过来的用户
            user = (User)intent.getSerializableExtra("loginUser");
            Toast.makeText(getApplicationContext(),"欢迎您成为SCOS新用户",Toast.LENGTH_LONG).show();
        }



    }

    class GridViewListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Item item ;
            item = (Item) adapterView.getItemAtPosition(i);
            if(item.text == "登录/注册")
            {
                Intent loginIntent = new Intent(MainScreen.this,LoginOrRegister.class);
                MainScreen.this.startActivity(loginIntent);
            }
            if(item.text == "点菜"){
                Intent orderIntent = new Intent(MainScreen.this,FoodView.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentuser",user);
                orderIntent.putExtras(bundle);
                MainScreen.this.startActivity(orderIntent);
            }if(item.text == "查看订单"){
                Intent orderIntent = new Intent(MainScreen.this,FoodOrderView.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentuser",user);
                orderIntent.putExtras(bundle);
                MainScreen.this.startActivity(orderIntent);
            }if(item.text == "系统帮助"){
                Intent intent = new Intent(MainScreen.this,SCOSHelper.class);
                MainScreen.this.startActivity(intent);
            }

        }
    }

}
