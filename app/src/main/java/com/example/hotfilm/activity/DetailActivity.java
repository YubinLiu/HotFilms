package com.example.hotfilm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotfilm.R;
import com.example.hotfilm.bean.Film;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView filmTitle;
    private ImageView filmImg;
    private TextView filmReleaseDate;
    private TextView filmVoteAverage;
    private TextView filmOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //初始化控件
        initViews();

        Film film = getIntent().getParcelableExtra("film_detail_data");
        filmTitle.setText(film.getTitle());
        Picasso.with(this).load(film.getBigPosterUrl())
                .placeholder(R.mipmap.ic_launcher) //正在下载的时候将会显示这张图片
                .error(R.mipmap.ic_launcher) //下载失败的时候将会显示这张图片
                .into(filmImg);
        filmReleaseDate.setText("上映时间:\n" + film.getRealseDate());
        filmVoteAverage.setText("评分:\n" + film.getVoteAverage() + " / 10");
        filmOverview.setText("主要剧情:\n" + film.getOverview());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //该活动启动所需要的数据
    public static void actionStart(Context context, Film film) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("film_detail_data", film);
        context.startActivity(intent);
    }

    private void initViews() {
        filmTitle = (TextView) findViewById(R.id.film_title);
        filmImg = (ImageView) findViewById(R.id.film_img);
        filmReleaseDate = (TextView) findViewById(R.id.film_release_date);
        filmVoteAverage = (TextView) findViewById(R.id.film_vote_average);
        filmOverview = (TextView) findViewById(R.id.film_overview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
