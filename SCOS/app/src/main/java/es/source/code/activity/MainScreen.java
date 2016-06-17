package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinhe on 2016/6/11.
 */
public class MainScreen extends Activity {


    public GridView gridView;
    public boolean isMenuHide = false;
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
        }else if("LoginSuccess".equals(value)){
            gridView.setAdapter(new GridViewAdapter(getBaseContext(),isMenuHide));
            gridView.setOnItemClickListener(new GridViewListener());
        }

    }

    class GridViewListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            GridViewAdapter.Item item ;
            item = (GridViewAdapter.Item) adapterView.getItemAtPosition(i);
            if(item.text == "登录/注册")
            {
                Intent loginIntent = new Intent(MainScreen.this,LoginOrRegister.class);
                MainScreen.this.startActivity(loginIntent);
            }

        }
    }

}
