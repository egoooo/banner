package www.viewpager.com.autoviewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import www.viewpager.com.autoviewpager.utils.ToastUtils;
import www.viewpager.com.autoviewpager.view.RollViewPager;

public class MainActivity extends AppCompatActivity {
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    //存放指示点的集合
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private int lastPosition=0;
    private int[] urls={R.drawable.aa,R.drawable.bb,R.drawable.cc,R.drawable.dd,R.drawable.e};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_roll_view);
        initView();
    }

    private void initView() {
        lastPosition=0;
        linearLayout= (LinearLayout) findViewById(R.id.top_news_viewpager_ll);
        pointLinearLayout= (LinearLayout) findViewById(R.id.dots_ll);

        //第一步：添加轮播图（也可以直接将布局写成咱们自定义的viewpager）
        RollViewPager rollViewPager = new RollViewPager(MainActivity.this);
        linearLayout.addView(rollViewPager);

        //第二步：传递轮播图需要的图片url集合或者数组（在真实项目中我们需要获得图片的url集合，然后传递给轮播图）
        rollViewPager.setImageUrls(urls);

        //第三步：添加指示点
        addPoints();

        //第四步：给轮播图添加界面改变监听，切换指示点
        rollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                position=position%urls.length;
                //切换指示点
                pointList.get(lastPosition).setImageResource(R.drawable.dot_normal);
                pointList.get(position).setImageResource(R.drawable.dot_focus);
                lastPosition=position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //第五步：轮播图点击监听
        rollViewPager.setOnItemClickListener(new RollViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showStaticToast(MainActivity.this,position+"");
            }
        });

        //第六步：设置当前页面，最好不要写最大数除以2，其实写了50就足够了，谁没事无聊到打开app二话不说直接对着轮播图往相反方向不停的划几十下
        rollViewPager.setCurrentItem(50 - 50 % urls.length);

        //第七步：设置完之后就可以轮播了：开启自动轮播
        rollViewPager.startRoll();

    }

    //整个方法直接复制就可以
    private void addPoints() {
        pointList.clear();
        pointLinearLayout.removeAllViews();
        for(int x=0;x<urls.length;x++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.dot_normal);
            //导报的时候指示点的父View是什么布局就导什么布局的params,这里导的是线性布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50);
            //设置间隔
            params.leftMargin=10;
            params.rightMargin=10;
            pointLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccentt));
            //添加到线性布局;params指定布局参数，不然点就按在一起了
            pointLinearLayout.addView(imageView,params);

            //把指示点存放到集合中
            pointList.add(imageView);


        }
    }
}
