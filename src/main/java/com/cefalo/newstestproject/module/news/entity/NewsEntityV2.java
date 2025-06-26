package com.cefalo.newstestproject.module.news.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table(name = "News")
public class NewsEntityV2 extends NewsEntity {


    @NotNull
    // Use standard year-month-day format for parsing the publish date
    // "YYYY" parses week based year which is incorrect here
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "news_publish_date")
    private Date newsPublishDate;

    public NewsEntityV2() {
    }

    public NewsEntityV2(String newsTitle, String newsBody, String newsAuthor, Date newsPublishDate) {
        super(newsTitle, newsBody, newsAuthor);
        this.newsPublishDate = newsPublishDate;
    }

    public Date getNewsPublishDate() {
        return newsPublishDate;
    }

    public void setNewsPublishDate(Date newsPublishDate) {
        this.newsPublishDate = newsPublishDate;
    }


    @Override
    public String toString() {
        return "NewsEntityV2{" +
                "newsPublishDate=" + newsPublishDate +
                ", newsId=" + newsId +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsBody='" + newsBody + '\'' +
                ", newsAuthor='" + newsAuthor + '\'' +
                '}';
    }
}
