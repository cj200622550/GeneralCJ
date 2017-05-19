package com.toolsclass.chenjun.general.generalcj.bean;

import java.util.List;

/**
 * Created by CJ
 * Date:2017/5/15
 * Time:14:18
 * <p/>
 * 电影实体类
 */

public class MovieBean {
    private Rating rating;
    private List<String> genres ;
    private String title; // 电影名字
    private List<Casts> casts ;
    private int collect_count;
    private String original_title; // 英文标题
    private String subtype;
    private List<Directors> directors ; // 导演
    private String year; // 年份
    private Images images; // 图片
    private String alt; // 标题
    private String id;

    public void setRating(Rating rating){
        this.rating = rating;
    }
    public Rating getRating(){
        return this.rating;
    }
    public void setString(List<String> genres){
        this.genres = genres;
    }
    public List<String> getString(){
        return this.genres;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setCasts(List<Casts> casts){
        this.casts = casts;
    }
    public List<Casts> getCasts(){
        return this.casts;
    }
    public void setCollect_count(int collect_count){
        this.collect_count = collect_count;
    }
    public int getCollect_count(){
        return this.collect_count;
    }
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }
    public String getOriginal_title(){
        return this.original_title;
    }
    public void setSubtype(String subtype){
        this.subtype = subtype;
    }
    public String getSubtype(){
        return this.subtype;
    }
    public void setDirectors(List<Directors> directors){
        this.directors = directors;
    }
    public List<Directors> getDirectors(){
        return this.directors;
    }
    public void setYear(String year){
        this.year = year;
    }
    public String getYear(){
        return this.year;
    }
    public void setImages(Images images){
        this.images = images;
    }
    public Images getImages(){
        return this.images;
    }
    public void setAlt(String alt){
        this.alt = alt;
    }
    public String getAlt(){
        return this.alt;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "MovieBean{" +
                "rating=" + rating +
                ", genres=" + genres +
                ", title='" + title + '\'' +
                ", casts=" + casts +
                ", collect_count=" + collect_count +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype + '\'' +
                ", directors=" + directors +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public class Casts {
        private String name;
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

    public class Images {
        private String small;
        private String large;
        private String medium;

        public void setSmall(String small){
            this.small = small;
        }
        public String getSmall(){
            return this.small;
        }
        public void setLarge(String large){
            this.large = large;
        }
        public String getLarge(){
            return this.large;
        }
        public void setMedium(String medium){
            this.medium = medium;
        }
        public String getMedium(){
            return this.medium;
        }

    }

    public class Rating {
        private int max;

        private double average;

        private String stars;

        private int min;

        public void setMax(int max){
            this.max = max;
        }
        public int getMax(){
            return this.max;
        }
        public void setAverage(double average){
            this.average = average;
        }
        public double getAverage(){
            return this.average;
        }
        public void setStars(String stars){
            this.stars = stars;
        }
        public String getStars(){
            return this.stars;
        }
        public void setMin(int min){
            this.min = min;
        }
        public int getMin(){
            return this.min;
        }

    }

    public class Directors {
        private String name;

        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }

    }
}


