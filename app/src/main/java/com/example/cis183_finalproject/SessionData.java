package com.example.cis183_finalproject;

public class SessionData
{
    private static User loggedInUser;


    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser)
    {
        SessionData.loggedInUser = loggedInUser;
    }
}
