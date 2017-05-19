package com.toolsclass.chenjun.general.generalcj.bean;

import java.util.List;

/**
 * 作者：陈骏 on 2017/5/15 15:45
 * QQ：200622550
 * 电影详细实体
 */

public class MovieDetailBean {
    private Rating rating; // 评分
    private int reviews_count; // 影评数量
    private int wish_count; // 想看人数
    private String douban_site;  // 豆瓣小站
    private String year; // 年代
    private Images images; // 电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
    private String alt; // 条目页URL
    private String id; // 条目id
    private String mobile_url; // 移动版条目页URL
    private String title; // 中文名
    private String original_title; // 原名
    private List<String> aka ; // 又名
    private int do_count; // 在看人数，如果是电视剧，默认值为0，如果是电影值为null
    private String schedule_url; // 影讯页URL(movie only)
    private String episodes_count; // 当前季的集数(tv only)
    private List<String> countries ; // 制片国家/地区
    private List<String> genres ; // 影片类型，最多提供3个
    private int collect_count; // 看过人数
    private List<Casts> casts ; // 主演，最多可获得4个，数据结构为影人的简化描述
    private String summary; // 摘要，100字以内
    private String subtype; // 条目分类, movie或者tv
    private List<Directors> directors ; // 导演，数据结构为影人的简化描述
    private int comments_count; // 短评数量
    private int ratings_count; // 评分人数

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDo_count() {
        return do_count;
    }

    public void setDo_count(int do_count) {
        this.do_count = do_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(String episodes_count) {
        this.episodes_count = episodes_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public List<Casts> getCasts() {
        return casts;
    }

    public void setCasts(List<Casts> casts) {
        this.casts = casts;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public List<Directors> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    @Override
    public String toString() {
        return "MovieDetailBean{" +
                "rating=" + rating +
                ", reviews_count=" + reviews_count +
                ", wish_count=" + wish_count +
                ", douban_site='" + douban_site + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", title='" + title + '\'' +
                ", do_count=" + do_count +
                ", schedule_url='" + schedule_url + '\'' +
                ", episodes_count='" + episodes_count + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                ", collect_count=" + collect_count +
                ", casts=" + casts +
                ", original_title='" + original_title + '\'' +
                ", summary='" + summary + '\'' +
                ", subtype='" + subtype + '\'' +
                ", directors=" + directors +
                ", comments_count=" + comments_count +
                ", ratings_count=" + ratings_count +
                ", aka=" + aka +
                '}';
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
}
