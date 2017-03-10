package com.example.hotfilm.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yubin on 2017/2/16.
 */

public class Film implements Parcelable{

    //电影名称
    private String title;

    //电影上映日期
    private String releaseDate;

    //电影评分
    private double voteAverage;

    //电影小图片url
    private String smallPosterUrl;

    //电影大图片url
    private String bigPosterUrl;

    //电影简介
    private String overview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRealseDate() {
        return releaseDate;
    }

    public void setRealseDate(String realseDate) {
        this.releaseDate = realseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeString(smallPosterUrl);
        parcel.writeString(bigPosterUrl);
        parcel.writeString(overview);
    }

    public static final Parcelable.Creator<Film> CREATOR =
            new Parcelable.Creator<Film>() {

                @Override
                public Film createFromParcel(Parcel parcel) {
                    Film film = new Film();
                    film.title = parcel.readString();
                    film.releaseDate = parcel.readString();
                    film.voteAverage = parcel.readDouble();
                    film.smallPosterUrl = parcel.readString();
                    film.bigPosterUrl = parcel.readString();
                    film.overview = parcel.readString();
                    return film;
                }

                @Override
                public Film[] newArray(int size) {
                    return new Film[size];
                }
            };

    public String getSmallPosterUrl() {
        return smallPosterUrl;
    }

    public void setSmallPosterUrl(String smallPosterUrl) {
        this.smallPosterUrl = smallPosterUrl;
    }

    public String getBigPosterUrl() {
        return bigPosterUrl;
    }

    public void setBigPosterUrl(String bigPosterUrl) {
        this.bigPosterUrl = bigPosterUrl;
    }
}
