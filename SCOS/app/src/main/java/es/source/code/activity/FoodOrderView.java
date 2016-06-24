package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import es.source.code.model.User;

/**
 * Created by adam on 2016/6/18.
 */
public class FoodOrderView extends Activity{
    private User user;
    /**
     * 选项卡文字
     */
    private TextView textView1 ,textView2;
    //菜品总量
    private TextView totalNumTv;
    private TextView totalMoneyTv;
    private Button submitOrderBtn;
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

    /**
     * 未下单菜品集合
     */
    List<OrderedFoodItem> orderedFoodItems = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("currentuser");
        initData();
        initTextView();
        initViewPager();
    }
    private void initData(){
        orderedFoodItems = new ArrayList<>();
        OrderedFoodItem item ;
        for(int i = 0; i < 10;i++){
            item = new OrderedFoodItem();
            item.name = i+"";
            item.price = i;
            item.numember = i;
            item.comment = i+"";
            orderedFoodItems.add(item);
        }
    }
    private void initTextView(){
        textView1 = (TextView) findViewById(R.id.unordered_food);
        textView2 = (TextView) findViewById(R.id.ordered_food);
        textView1.setTextColor(Color.BLUE);
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));

        totalNumTv = (TextView)findViewById(R.id.total_num);
        totalMoneyTv = (TextView)findViewById(R.id.total_money);
        submitOrderBtn = (Button)findViewById(R.id.submit_order);
    }
    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.ordered_food_viewpager);
        views = new ArrayList<>();
        LayoutInflater filter = getLayoutInflater();
        view1 = filter.inflate(R.layout.lay1,null);
        view2 = filter.inflate(R.layout.lay2,null);
        listView1 = (ListView) view1.findViewById(R.id.list_view1);
        listView2 = (ListView) view2.findViewById(R.id.list_view2);
        views.add(view1);
        views.add(view2);

        MyListViewAdapter myListViewAdapter1 = new MyListViewAdapter(getBaseContext(),orderedFoodItems,onClickListener,2);
        listView1.setAdapter(myListViewAdapter1);//未下单菜品，显示退点按钮

        MyListViewAdapter myListViewAdapter2 = new MyListViewAdapter(getBaseContext(),orderedFoodItems,null,1);
        listView2.setAdapter(myListViewAdapter2);


        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    private  View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getBaseContext(),"退点成功",Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 未下单已下单菜品
     */
    class OrderedFoodItem{
        String name;
        int price;
        int numember;
        String comment;//备注

    }
    public class MyListViewAdapter extends BaseAdapter{
        private Context context;
        /**
         * 食物列表
         */
        private List<OrderedFoodItem>  mOrderedFoodItems = new ArrayList<>();

        private View.OnClickListener onClickListener;

        private LayoutInflater mInflater;
        private int flag;//如果为1，则不显示退点按钮，为2显示退点按钮。
        public MyListViewAdapter(Context con, List<OrderedFoodItem> mOrderedFoodItems, View.OnClickListener onClickListener,int i) {
            this.context = con;
            this.mOrderedFoodItems = mOrderedFoodItems;
            this.onClickListener = onClickListener;
            this.flag = i;
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
            ViewHolder viewHolder;
            if(view == null){
                view = mInflater.inflate(R.layout.ordered_food_item,null);
                viewHolder = new ViewHolder();
                viewHolder.nameTv = (TextView) view.findViewById(R.id.ordered_food_name);
                viewHolder.priceTv = (TextView) view.findViewById(R.id.ordered_food_price);
                viewHolder.numemberTv = (TextView)view.findViewById(R.id.ordered_food_num);
                viewHolder.commentTv  = (TextView) view.findViewById(R.id.ordered_food_comment);
                viewHolder.quitBtn = (Button) view.findViewById(R.id.ordered_quit);
                if(flag == 1){
                    viewHolder.quitBtn.setVisibility(View.GONE);
                }
                if(flag == 2){
                    viewHolder.quitBtn.setVisibility(View.VISIBLE);
                }
                view.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            OrderedFoodItem item = mOrderedFoodItems.get(i);
            if(item != null){
                viewHolder.nameTv.setText(item.name);
                viewHolder.priceTv.setText(item.price+"");
                viewHolder.numemberTv.setText(item.numember+"");
                viewHolder.commentTv.setText(item.comment);
                viewHolder.quitBtn.setOnClickListener(this.onClickListener);
            }

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
            if(currentIndex == 0){
                textView1.setTextColor(Color.BLUE);
                textView2.setTextColor(Color.BLACK);
                submitOrderBtn.setText("提交订单");
            }else {
                textView1.setTextColor(Color.BLACK);
                textView2.setTextColor(Color.BLUE);
                submitOrderBtn.setText("结账");
            }
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
            if(index == 0){
                textView1.setTextColor(Color.BLUE);
                textView2.setTextColor(Color.BLACK);
            }else {
                textView1.setTextColor(Color.BLACK);
                textView2.setTextColor(Color.BLUE);
            }
        }
    }
}
