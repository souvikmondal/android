package com.lib.sampleimagelist.model;

import java.util.Map;

public class Categories
{
    private int id;

    private String title;

    private Map<String, String> links;

    private int photo_count;

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public Map<String, String> getLinks ()
    {
        return links;
    }

    public void setLinks (Map<String, String> links)
    {
        this.links = links;
    }

    public int getPhoto_count ()
    {
        return photo_count;
    }

    public void setPhoto_count (int photo_count)
    {
        this.photo_count = photo_count;
    }


}