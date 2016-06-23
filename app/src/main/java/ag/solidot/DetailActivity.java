package ag.solidot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class DetailActivity extends AppCompatActivity {
    private TextView tvd_title;
    private TextView tvd_content;
    private TextView tvd_author;
    private TextView tvd_date;
    private String sid;

    private List<Map<String, String>> details = new ArrayList<>();

    GetWebData gwd = new GetWebData();

    final String addh = "http://www.solidot.org/story?sid=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar dtb = (Toolbar) findViewById(R.id.dtoolbar);
//        dtb.setLogo(R.mipmap.ic_launcher);
        dtb.setTitle("Solidot+");
        setSupportActionBar(dtb);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


        tvd_title = (TextView) findViewById(R.id.detail_title);
        tvd_content = (TextView) findViewById(R.id.detail_content);
        tvd_author = (TextView) findViewById(R.id.detail_author);
        tvd_date = (TextView) findViewById(R.id.detail_date);
        tvd_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        Intent tmpi = getIntent();
        sid = tmpi.getStringExtra("sid");

        OkHttpUtils
                .get()
                .url(addh+sid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("网络错误"+ e);
                        Toast.makeText(DetailActivity.this,"网络出现错误,请检查网络连接",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        details = gwd.parseStoryDom(response);
//                        String tmp = String.valueOf(details.size());
                        tvd_title.setText(details.get(0).get("title"));
                        tvd_content.setText("        " + details.get(0).get("content"));
                        tvd_author.setText(details.get(0).get("author"));
                        tvd_date.setText("   |   " + details.get(0).get("date"));

                    }
                });


    }
}
