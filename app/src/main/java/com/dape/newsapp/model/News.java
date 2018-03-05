package com.dape.newsapp.model;

/**
 * Created by Danale on 01/03/2018.
 */

@SuppressWarnings("ALL")
public class News {
    private String pillarName;
    private String sectionName;
    private String publicationDate;
    private String webTitle;
    private String webUrl;
    private String name;

    public News() {
    }

    public News(String pillarName, String sectionName, String publicationDate, String webTitle, String webUrl, String name) {
        this.pillarName = pillarName;
        this.sectionName = sectionName;
        this.publicationDate = publicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
