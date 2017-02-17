package com.example.hotfilm.filmclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yubin on 2017/2/16.
 */

public class FilmDetail implements Parcelable{

    //电影名称
    private String title;

    //电影上映日期
    private String releaseDate;

    //电影评分
    private double voteAverage;

    //电影图片url
    private String posterUrl;

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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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
        parcel.writeString(posterUrl);
        parcel.writeString(overview);
    }

    public static final Parcelable.Creator<FilmDetail> CREATOR =
            new Parcelable.Creator<FilmDetail>() {

                @Override
                public FilmDetail createFromParcel(Parcel parcel) {
                    FilmDetail filmDetail = new FilmDetail();
                    filmDetail.title = parcel.readString();
                    filmDetail.releaseDate = parcel.readString();
                    filmDetail.voteAverage = parcel.readDouble();
                    filmDetail.posterUrl = parcel.readString();
                    filmDetail.overview = parcel.readString();
                    return filmDetail;
                }

                @Override
                public FilmDetail[] newArray(int size) {
                    return new FilmDetail[size];
                }
            };
}
