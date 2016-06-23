package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 2016/6/18.
 */
public class FoodOrderView extends Activity{
    /**
     * 选项卡文字
     */
    private TextView textView1 ,textView2;
    /**
     * ViewPager中的view
     */
    private View view1,view2;
    /**
     * view中的ListView 存放菜品信息
     */
    private ListView listView1;
    private ListView listView2;

    /**
     * Tap页面视图列表
     */
    private List<View> views;

    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 当前页面
     */
    private  int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);
        initTextView();
        initViewPager();
    }
    private void initTextView(){
        textView1 = (TextView) findViewById(R.id.unordered_food);
        textView2 = (TextView) findViewById(R.id.ordered_food);
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
    }
    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.ordered_food_viewpager);
        views = new ArrayList<>();
        LayoutInflater filter = getLayoutInflater();
        view1 = filter.inflate(R.layout.lay1,null);
        view2 = filter.inflate(R.layout.lay2,null);
        listView1 = (ListView) findViewById(R.id.list_view1);
        listView2 = (ListView) findViewById(R.id.list_view2);
        views.add(view1);
        views.add(view2);

        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 未下单已下单菜品
     */
    class OrderedFoodItem{
        String name;
        int price;
        int numember;
        int comment;//备注

    }
    public class MyListViewAdapter extends BaseAdapter{
        private Context context;
        /**
         * 食物列表
         */
        private List<OrderedFoodItem>  mOrderedFoodItems = new ArrayList<>();

        private View.OnClickListener onClickListener;

        private LayoutInflater mInflater;
        public MyListViewAdapter(Context con, List<OrderedFoodItem> mOrderedFoodItems, View.OnClickListener onClickListener) {
            this.context = con;
            this.mOrderedFoodItems = mOrderedFoodItems;
            this.onClickListener = onClickListener;
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return mOrderedFoodItems.size();
        }

        @Override
        public Object getItem(int i) {
            return mOrderedFoodItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            return view;
        }
        class ViewHolder{
            TextView nameTv;
            TextView priceTv;
            TextView numemberTv;
            TextView commentTv;
            Button quitBtn;//未下单中的退选按钮
        }
    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentIndex = position;
            viewPager.setCurrentItem(currentIndex);
        }
    }
    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) 	{
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }

    }
    private class MyOnClickListener implements View.OnClickListener{
        private int index = 0;
        private MyOnClickListener(int i){
            index = i;

        }
        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(index);
        }
    }
}
