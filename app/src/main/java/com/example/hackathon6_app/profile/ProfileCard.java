package com.example.hackathon6_app.profile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileCard {
    String name;
    String company;
    String title;
    String email;
    String location;
    String about;
    ArrayList<String> socialLinks;


    public ProfileCard(
            String name,
            String company,
            String title,
            String email,
            String location,
            String about,
            ArrayList<String> socialLinks) {
        this.name = name;
        this.company = company;
        this.title = title;
        this.email = email;
        this.location = location;
        this.about = about;
        this.socialLinks = new ArrayList<String>();
        for  (String link : socialLinks) {
            this.socialLinks.add(link);
        }
    }

    public String getName()
    {
        return name;
    }

    public String getCompany()
    {
        return company;
    }

    public String getTitle()
    {
        return title;
    }
    public String getEmail()
    {
        return email;
    }
    public String getLocation()
    {
        return location;
    }
    public String getAboutMe()
    {
        return about;
    }
    public ArrayList<String> getSocialLinks()
    {
        return socialLinks;
    }
}



