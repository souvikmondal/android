package com.lib.sampleimagelist.model;

import java.util.Map;

/**
 * Created by souvik on 7/9/2016.
 */
public class User
{
    private String id;

    private String username;

    private Map<String, String> profile_image;

    private String name;

    private Map<String, String> links;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public Map<String, String> getProfile_image ()
    {
        return profile_image;
    }

    public void setProfile_image (Map<String, String> profile_image)
    {
        this.profile_image = profile_image;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Map<String, String> getLinks ()
    {
        return links;
    }

    public void setLinks (Map<String, String> links)
    {
        this.links = links;
    }

}
