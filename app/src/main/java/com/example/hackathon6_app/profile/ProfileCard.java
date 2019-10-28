package com.example.hackathon6_app.profile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileCard {
    String firstName;
    String lastName;
    String company;
    String title;
    String email;
    String location;
    String about;
    ArrayList<String> socialLinks;


    public ProfileCard() {
        this.socialLinks = new ArrayList<String>();
    }

    public ProfileCard(
            String firstName,
            String lastName,
            String company,
            String title,
            String email,
            String location,
            String about,
            ArrayList<String> socialLinks) {
        this.firstName = firstName;
        this.lastName = lastName;

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

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFullName()
    {
        return firstName + ' ' + lastName;
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


