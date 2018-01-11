package com.thinkgem.jeesite.modules.bis.entity;

import java.util.List;
import java.util.Map;

public class BisMovie {
    private String id;
    /**
     * 图片
     */
    private String img;
    /**
     * 名称
     */
    private String name;
    /**
     * 别名
     */
    private String alias;
    /**
     * 演员
     */
    private String performer;
    /**
     * 类型
     */
    private String type;
    /**
     * 地区
     */
    private String region;
    /**
     * 语言
     */
    private String language;
    /**
     * 导演
     */
    private String director;
    /**
     * 上映日期
     */
    private String ReleaseDate;
    /**
     * 豆瓣评分
     */
    private String BeanScore;
    /**
     * 剧情介绍
     */
    private String PlotIntroduction;
    /**
     * 下载链接
     */
    private String thunder;
    /**
     * 下载链接 全部
     */
    private String thunderAll;

    /**
     * 下载链接 全部
     */
    private List<Map> thunderList;

    public List<Map> getThunderList() {
        return thunderList;
    }

    public void setThunderList(List<Map> thunderList) {
        this.thunderList = thunderList;
    }

    public String getThunderAll() {
        return thunderAll;
    }

    public void setThunderAll(String thunderAll) {
        this.thunderAll = thunderAll;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getBeanScore() {
        return BeanScore;
    }

    public void setBeanScore(String beanScore) {
        BeanScore = beanScore;
    }

    public String getPlotIntroduction() {
        return PlotIntroduction;
    }

    public void setPlotIntroduction(String plotIntroduction) {
        PlotIntroduction = plotIntroduction;
    }

    public String getThunder() {
        return thunder;
    }

    public void setThunder(String thunder) {
        this.thunder = thunder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
