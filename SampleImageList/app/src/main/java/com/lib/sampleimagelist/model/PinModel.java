package com.lib.sampleimagelist.model;

import java.util.Map;

public class PinModel
{
    private String id;

    private int height;

    private String[] current_user_collections;

    private String color;

    private Map<String, String> urls;

    private int likes;

    private int width;

    private String created_at;

    private Map<String, String> links;

    private Categories[] categories;

    private User user;

    private boolean liked_by_user;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public int getHeight ()
    {
        return height;
    }

    public void setHeight (int height)
    {
        this.height = height;
    }

    public String[] getCurrent_user_collections ()
    {
        return current_user_collections;
    }

    public void setCurrent_user_collections (String[] current_user_collections)
    {
        this.current_user_collections = current_user_collections;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public Map<String, String> getUrls ()
    {
        return urls;
    }

    public void setUrls (Map<String, String> urls)
    {
        this.urls = urls;
    }

    public int getLikes ()
    {
        return likes;
    }

    public void setLikes (int likes)
    {
        this.likes = likes;
    }

    public int getWidth ()
    {
        return width;
    }

    public void setWidth (int width)
    {
        this.width = width;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public Map<String, String> getLinks ()
    {
        return links;
    }

    public void setLinks (Map<String, String> links)
    {
        this.links = links;
    }

    public Categories[] getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories[] categories)
    {
        this.categories = categories;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public boolean getLiked_by_user ()
    {
        return liked_by_user;
    }

    public void setLiked_by_user (boolean liked_by_user)
    {
        this.liked_by_user = liked_by_user;
    }

}