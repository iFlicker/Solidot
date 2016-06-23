package ag.solidot;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ag.solidot.AdapterH.MyRecyclerAdapter;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {
    
    List<Map<String, String>> classL = new ArrayList<>();
    private GetWebData wdd = new GetWebData();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar tbm = (Toolbar) findViewById(R.id.toolbarm);
        tbm.setTitle("Solidot+");
        setSupportActionBar(tbm);
        tbm.inflateMenu(R.menu.menus);
        tbm.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.toclass) {
//                    Toast.makeText(MainActivity.this,"fsdfsdfdsfsdfsfsdfsd",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,ClassActivity.class));
                }
                return true;
            }
        });


        final RecyclerView rv_main = (RecyclerView) findViewById(R.id.rv_main);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.rvs_main);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL); //设置垂直
        rv_main.setLayoutManager(mLayoutManager);

        final MyRecyclerAdapter mra = new MyRecyclerAdapter(this,classL);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mra);
        rv_main.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        rv_main.setItemAnimator(new DefaultItemAnimator());


        //设置下拉刷新控件样式
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl.setColorSchemeResources(R.color.srl1,R.color.srl2,R.color.srl3,R.color.srl4);
        //SwipeRefreshLayout Resresh监听器
        SwipeRefreshLayout.OnRefreshListener srlListener = new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                OkHttpUtils
                        .get()
                        .url("http://www.solidot.org/")////////////////////////////////////////////////////////////
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                System.out.println("网络错误"+e);
                                srl.setRefreshing(false);
                                Toast.makeText(MainActivity.this,"网络异常,请打开网络连接或稍后重试",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                classL.clear();
                                classL = wdd.parseClassDom(response);

                                mra.removeAll();
                                mra.addAll(classL);
                                rv_main.smoothScrollToPosition(0);
                                srl.setRefreshing(false);
                            }
                        });
            }
        };
        srl.setOnRefreshListener(srlListener);

        //---------首次加载自动下拉刷新
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        srlListener.onRefresh();
        //---------

        mra.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);/////////////////////
                i.putExtra("sid",classL.get(position).get("sid"));
                startActivity(i);
            }

            @Override
            public void onLongClick(int position) {
//                Toast.makeText(this,"onLongClick事件 您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        boomMenuButton.init(
//                subButtonDrawables, // 子按钮的图标Drawable数组，不可以为null
//                subButtonTexts,     // 子按钮的文本String数组，可以为null
//                subButtonColors,    // 子按钮的背景颜色color二维数组，包括按下和正常状态的颜色，不可为null
//                ButtonType.HAM,     // 子按钮的类型
//                BoomType.PARABOLA,  // 爆炸类型
//                PlaceType.HAM_3_1,  // 排列类型
//                null,               // 展开时子按钮移动的缓动函数类型
//                null,               // 展开时子按钮放大的缓动函数类型
//                null,               // 展开时子按钮旋转的缓动函数类型
//                null,               // 隐藏时子按钮移动的缓动函数类型
//                null,               // 隐藏时子按钮缩小的缓动函数类型
//                null,               // 隐藏时子按钮旋转的缓动函数类型
//                null                // 旋转角度
//        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate( R.menu.menus , menu);
        return true;
    }

}
