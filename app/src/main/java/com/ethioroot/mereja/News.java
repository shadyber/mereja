package com.ethioroot.mereja;

/**
 * Created by Lincoln on 18/05/16.
 */
public class News {
    private String name;
    private String geners;
    private String thumbnail;
    private int movieid;
    private String director;
    private  String casts;
    private  String length;
    private  String rating;
    private  String writer;
    private  String video;
    private  String releasedon;
    private String commingto;
    private String shortDescription;
    private String longDescription;

    public News() {
    }

    public News(String name, String geners, String thumbnail) {
        this.name = name;
        this.geners = geners;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }



    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }


    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public int getMovieid() {
        return movieid;
    }

    public void setShortDescription(int movieid) {
        this.movieid = movieid;
    }








    public String getgeners() {
        return geners;
    }

    public void setGeners(String numOfSongs) {
        this.geners = geners;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
