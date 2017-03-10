package com.example.hotfilm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import com.example.hotfilm.BuildConfig;
import com.example.hotfilm.R;
import com.example.hotfilm.adapter.FilmAdapter;
import com.example.hotfilm.bean.Film;
import com.example.hotfilm.fragment.MainFragment;
import com.example.hotfilm.util.MyOkHttp;
import com.example.hotfilm.util.ShowToastUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int screenWidth;

    public static List<Film> filmList = new ArrayList<>();

    private MainFragment mainFragment;
    private RecyclerView recyclerview;
    private FilmAdapter adapter;
    private String u = null;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_poster);

        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;

        //初始化布局
        initViews();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        if (isOnline()) {
            new FetchFilmTask().execute(u);
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        } else {
            ShowToastUtil.showToast(R.string.overtime);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String unitType = updateFilm();
        if (isOnline()) {
            if (!unitType.equals(u)) {
                u = unitType;
                if (unitType.equals(getString(R.string.pref_units_popular))) {
                    new FetchFilmTask().execute(getString(R.string.pref_units_popular));
                }
                if (unitType.equals(getString(R.string.pref_units_top_rated))) {
                    new FetchFilmTask().execute(getString(R.string.pref_units_top_rated));
                }
            }
        } else {
            ShowToastUtil.showToast(R.string.overtime);
        }
    }

    private String updateFilm() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String unitType = sharedPrefs.getString(
                getString(R.string.pref_units_key),
                getString(R.string.pref_units_popular));
        return unitType;
    }

    private void initViews() {
        mainFragment = (MainFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragment_main);
        swipeRefresh = (SwipeRefreshLayout)
                mainFragment.getView().findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        recyclerview = (RecyclerView)
                mainFragment.getView().findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(
                MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public class FetchFilmTask extends AsyncTask<String, Void, String> {

        final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185/";

        final String POSTER_BASE_URL_BIG = "https://image.tmdb.org/t/p/w500/";

        final String FILM_BASE_URL = "http://api.themoviedb.org/3/movie/";

        final String QUERY_LANGUAGE = "?language=zh&";

        final String API_KEY = "api_key=";

        @Override
        protected String doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }

            String address = FILM_BASE_URL
                    + strings[0]
                    + QUERY_LANGUAGE
                    + API_KEY
                    + BuildConfig.OPEN_HOT_FILM_API_KEY;

            //返回JSON数据
            return MyOkHttp.get(address);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!TextUtils.isEmpty(result)) {
                handleJsonData(result);
            }

            if (adapter == null) {
                adapter = new FilmAdapter(MainActivity.this, filmList);
                recyclerview.setAdapter(adapter);
            } else {
                //让适配器刷新数据
                adapter.notifyDataSetChanged();
            }
        }

        private void handleJsonData(String result) {
            try {
                JSONObject jsonData = new JSONObject(result);
                JSONArray jsonArray = jsonData.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.opt(i);

                    Film film = new Film();

                    String posterPath = jsonObject.getString("poster_path");
                    String posterUrlSmall = POSTER_BASE_URL + posterPath;
                    String posterUrlBig = POSTER_BASE_URL_BIG + posterPath;

                    String filmTitle = jsonObject.getString("title");

                    double voteAverage = jsonObject.getDouble("vote_average");

                    film.setTitle(filmTitle);
                    film.setOverview(jsonObject.getString("overview"));
                    film.setSmallPosterUrl(posterUrlSmall);
                    film.setBigPosterUrl(posterUrlBig);
                    film.setRealseDate(jsonObject.getString("release_date"));
                    film.setVoteAverage(voteAverage);

                    if (filmList.size() == jsonArray.length()) {
                        filmList.set(i, film);
                    } else {
                        filmList.add(film);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
