package ag.solidot;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ag.solidot.AdapterH.MyRecyclerAdapter;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import okhttp3.Call;

public class HotActivity extends AppCompatActivity {

    List<Map<String, String>> classL = new ArrayList<>();
    private GetWebData wdd = new GetWebData();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);

        Toolbar tbm = (Toolbar) findViewById(R.id.toolbarh);
        tbm.setTitle("Hot+");
        setSupportActionBar(tbm);

        final RecyclerView rv_hot = (RecyclerView) findViewById(R.id.rv_hot);
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.rvs_hot);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL); //设置垂直
        rv_hot.setLayoutManager(mLayoutManager);

        final MyRecyclerAdapter mra = new MyRecyclerAdapter(this,classL);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mra);
        rv_hot.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        rv_hot.setItemAnimator(new DefaultItemAnimator());


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
                                Toast.makeText(HotActivity.this,"网络异常,请打开网络连接或稍后重试",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                classL.clear();
                                classL = wdd.parseHotDom(response);

                                mra.removeAll();
                                mra.addAll(classL);
                                rv_hot.smoothScrollToPosition(0);
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
                Intent i = new Intent(HotActivity.this, DetailActivity.class);/////////////////////
                i.putExtra("sid",classL.get(position).get("sid"));
                startActivity(i);
            }

            @Override
            public void onLongClick(int position) {
//                Toast.makeText(this,"onLongClick事件 您点击了第："+position+"个Item",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
