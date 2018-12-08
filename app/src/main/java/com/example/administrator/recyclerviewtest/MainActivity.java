package com.example.administrator.recyclerviewtest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_BOTTOM;
import static com.chad.library.adapter.base.BaseQuickAdapter.SLIDEIN_RIGHT;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Model> datas;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        //模拟的数据（实际开发中一般是从网络获取的）
        datas = new ArrayList<>();
        Model model;
        for (int i = 0; i < 15; i++) {
            model = new Model();
            model.setTitle("我是第" + i + "条标题");
            model.setContent("第" + i + "条内容");
            datas.add(model);
        }

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new MyAdapter(R.layout.item_rv, datas);

        //给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);

        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(MainActivity.this, "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
            }
        });

        //条目长按事件
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(MainActivity.this, "长按了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        /*//自定义动画效果
        adapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f, 1),
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 0.5f, 1)
                };
            }
        });*/

        //开启动画（默认为渐显效果）
        adapter.openLoadAnimation(SCALEIN);
        //设置重复执行动画
        adapter.isFirstOnly(false);
        adapter.setUpFetchEnable(true);
    }
}

