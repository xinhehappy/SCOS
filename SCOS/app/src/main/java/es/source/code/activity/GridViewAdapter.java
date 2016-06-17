package es.source.code.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 2016/6/17.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<Item> mItems = new ArrayList<Item>();
    public GridViewAdapter(Context context,boolean isMenuHide){
        for(int i = 0;i<imageId.length-2;i++){
            Item object = new Item();
            object.resId = imageId[i];
            object.text = textItem[i];
            mItems.add(object);
        }
        isMenuHide = true;

        this.context = context;
    }
    public GridViewAdapter(Context context){
        for(int i = 0;i<imageId.length;i++){
            Item object = new Item();
            object.resId = imageId[i];
            object.text = textItem[i];
            mItems.add(object);
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.gridview_item,null);
        }
        ImageView image = (ImageView)view.findViewById(R.id.icon);
        TextView text = (TextView)view.findViewById(R.id.text);
        Item item = (Item)getItem(i);
        image.setImageResource(item.resId);
        text.setText(item.text);
        return view;
    }
    public int imageId[] = {R.drawable.order,R.drawable.check,R.drawable.loginorout,R.drawable.help};
    public String textItem[] = {"点菜","查看菜单","登录/注册","系统帮助"};
    public class Item{
        public String text;
        public int resId;
    }
}
