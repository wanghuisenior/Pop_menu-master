package com.wanghui.www.popu_menu_master.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wanghui.www.popu_menu_master.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout ll_topmenu1;
    private LinearLayout ll_topmenu2;
    private LinearLayout ll_topmenu3;
    private TextView tv_topmenu1;
    private TextView tv_topmenu2;
    private TextView tv_topmenu3;
    private ListView lv_content;
    private ArrayList<HashMap<String, String>> menuData1;
    private ArrayList<HashMap<String, String>> menuData2;
    private ArrayList<HashMap<String, String>> menuData3;
    private PopupWindow popupWindow;
    private SimpleAdapter menuAdapter1;
    private SimpleAdapter menuAdapter2;
    private SimpleAdapter menuAdapter3;
    private String currentTopMenu1, currentTopMenu2, currentTopMenu3;
    private int menuIndex = 0;
    private TextView tv_title;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注意更改主题为@style/Theme.AppCompat.Light.NoActionBar
        initView();
        initPopuMenu();
    }


    /**
     * 初始化所有控件
     */
    private void initView() {
        //左上角标题文字,展示被选中的listview条目文字
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        //右上角购物车图片
        ImageView iv_cart = (ImageView) findViewById(R.id.iv_cart);
        //顶级菜单项,点击展示字菜单
        ll_topmenu1 = (LinearLayout) findViewById(R.id.ll_topmenu1);
        ll_topmenu2 = (LinearLayout) findViewById(R.id.ll_topmenu2);
        ll_topmenu3 = (LinearLayout) findViewById(R.id.ll_topmenu3);
        //顶级菜单中的文字,选择子菜单后改变为选中的文字
        tv_topmenu1 = (TextView) findViewById(R.id.tv_topmenu1);
        tv_topmenu2 = (TextView) findViewById(R.id.tv_topmenu2);
        tv_topmenu3 = (TextView) findViewById(R.id.tv_topmenu3);
        //展示列表的listview
        ListView lv_content = ((ListView) findViewById(R.id.lv_content));
        /** 给内容添加一些数据  **/
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("我是listview的内容条目" + i);
        }
        lv_content.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        //进度条
        ProgressBar pb_loading = (ProgressBar) findViewById(R.id.pb_loading);

        //设置点击事件
        ll_topmenu1.setOnClickListener(this);
        ll_topmenu2.setOnClickListener(this);
        ll_topmenu3.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        pb_loading.setVisibility(View.INVISIBLE);

    }

    /**
     * 初始化弹出窗的listview
     */
    private void initPopuMenu() {
        //初始化三个子菜单的内容
        initMenuData();
        //初始化弹窗中的listview
        View view = View.inflate(this, R.layout.pop_listview, null);
        //创建PopupWindow
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击区域外使对话框消失必须调用以下三个方法
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        //弹出的动画
        //popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        //设置取消弹窗后顶级菜单颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                tv_topmenu1.setTextColor(Color.parseColor("#5a5959"));
                tv_topmenu2.setTextColor(Color.parseColor("#5a5959"));
                tv_topmenu3.setTextColor(Color.parseColor("#5a5959"));
            }
        });
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                // 如果点击了popupwindow的外部，popupwindow也会消失
//                if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    Toast.makeText(MainActivity.this, "点击了外面", Toast.LENGTH_SHORT).show();
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });

        //弹窗中的listview
        lv_content = ((ListView) view.findViewById(R.id.lv_content));
        //每一个listview中条目的点击事件
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //取消弹窗
                popupWindow.dismiss();
                //由于点顶级菜单时已经记录了当次点击的menuIndex
                //如果是关联查询,不需要重置顶级菜单文字.  否则需要重置
                if (menuIndex == 0) {
                    currentTopMenu1 = menuData1.get(position).get("name");
                    //左上角标题
                    tv_title.setText(currentTopMenu1);
                    //当前点击的顶级菜单
                    tv_topmenu1.setText(currentTopMenu1);
                    Toast.makeText(MainActivity.this, currentTopMenu1, Toast.LENGTH_SHORT).show();
                } else if (menuIndex == 1) {
                    currentTopMenu2 = menuData2.get(position).get("name");
                    //左上角标题
                    tv_title.setText(currentTopMenu2);
                    //当前点击的顶级菜单
                    tv_topmenu2.setText(currentTopMenu2);
                    Toast.makeText(MainActivity.this, currentTopMenu2, Toast.LENGTH_SHORT).show();
                } else if (menuIndex == 2) {
                    currentTopMenu3 = menuData3.get(position).get("name");
                    //左上角标题
                    tv_title.setText(currentTopMenu3);
                    //当前点击的顶级菜单
                    tv_topmenu3.setText(currentTopMenu3);
                    Toast.makeText(MainActivity.this, currentTopMenu3, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //初始化三个弹窗中istview的三个adapter
        initListAdapter();
    }

    private void initListAdapter() {
        menuAdapter1 = new SimpleAdapter(this, menuData1,
                R.layout.item_pop_listview, new String[]{"name"},
                new int[]{R.id.tv_item_pop});

        menuAdapter2 = new SimpleAdapter(this, menuData2,
                R.layout.item_pop_listview, new String[]{"name"},
                new int[]{R.id.tv_item_pop});
        menuAdapter3 = new SimpleAdapter(this, menuData3,
                R.layout.item_pop_listview, new String[]{"name"},
                new int[]{R.id.tv_item_pop});
    }

    private void initMenuData() {
        //菜单1的内容
        menuData1 = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 5; i++) {//添加了5个子条目
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "菜单1条目" + i);
            menuData1.add(map);
        }
        //菜单2的内容
        menuData2 = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 3; i++) {//添加了3个子条目
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "菜单2条目" + i);
            menuData2.add(map);
        }
        //菜单3的内容
        menuData3 = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 7; i++) {//添加了7个子条目
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "菜单3条目" + i);
            menuData3.add(map);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_topmenu1://点击了第一个顶级菜单
                tv_topmenu1.setTextColor(Color.parseColor("#39ac69"));
                lv_content.setAdapter(menuAdapter1);
                popupWindow.showAsDropDown(ll_topmenu1, 0, 2);
                menuIndex = 0;
                break;
            case R.id.ll_topmenu2://点击了第2个顶级菜单
                tv_topmenu2.setTextColor(Color.parseColor("#39ac69"));
                lv_content.setAdapter(menuAdapter2);
                popupWindow.showAsDropDown(ll_topmenu1, 0, 2);
                menuIndex = 1;
                break;
            case R.id.ll_topmenu3://点击了第3个顶级菜单
                tv_topmenu3.setTextColor(Color.parseColor("#39ac69"));
                lv_content.setAdapter(menuAdapter3);
                popupWindow.showAsDropDown(ll_topmenu1, 0, 2);
                menuIndex = 2;
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
