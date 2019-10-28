package com.example.hackathon6_app.profile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileCard {
    public String firstName;
    public String lastName;
    public String company;
    public String title;
    public String email;
    public String location;
    public String about;
    public ArrayList<String> socialLinks;


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
        if (title != null) {
            this.title = title;
        } else {
            this.title = "";
        }
        if (email != null) {
            this.email = email;
        } else {
            this.email = "";
        }
        if (location != null) {
            this.location = location;
        } else {
            this.location = "";
        }
        if (about != null) {
            this.about = about;
        } else {
            this.about = "";
        }

        this.socialLinks = new ArrayList<String>();
        for  (String link : socialLinks) {
            this.socialLinks.add(link);
        }
    }

//    public void setFirstName(String name) {
//        this.firstName = name;
//    }
//
//    public void setLastName(String name) {
//        this.lastName = name;
//    }
//
//    public String getFirstName()
//    {
//        return firstName;
//    }
//
//    public String getLastName()
//    {
//        return lastName;
//    }
//
//    public String getFullName()
//    {
//        return firstName + ' ' + lastName;
//    }
//
//    public String getCompany()
//    {
//        return company;
//    }
//
//    public String getTitle()
//    {
//        return title;
//    }
//    public String getEmail()
//    {
//        return email;
//    }
//    public String getLocation()
//    {
//        return location;
//    }
//    public String getAboutMe()
//    {
//        return about;
//    }
//    public ArrayList<String> getSocialLinks()
//    {
//        return socialLinks;
//    }
}


