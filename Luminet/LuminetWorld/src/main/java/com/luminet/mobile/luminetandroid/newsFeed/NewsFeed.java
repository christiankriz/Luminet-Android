package com.luminet.mobile.luminetandroid.newsFeed;

/**
 * Created by chris on 2017/10/03.
 */

public class NewsFeed {
    private String ImageURL;
    private String enterpriseID;
    private String status;
    private String publishedTime;
    private String id;
    private String NewsFeedHeader;
    private String createdTime;
    private String reachedConsumers;
    private String expiryTime;
    private String Tags;
    private String guid;
    private String searchID;
    private  String NewsFeedBody;

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsFeedHeader() {
        return NewsFeedHeader;
    }

    public void setNewsFeedHeader(String newsFeedHeader) {
        NewsFeedHeader = newsFeedHeader;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getReachedConsumers() {
        return reachedConsumers;
    }

    public void setReachedConsumers(String reachedConsumers) {
        this.reachedConsumers = reachedConsumers;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getNewsFeedBody() {
        return NewsFeedBody;
    }

    public void setNewsFeedBody(String newsFeedBody) {
        NewsFeedBody = newsFeedBody;
    }
}
