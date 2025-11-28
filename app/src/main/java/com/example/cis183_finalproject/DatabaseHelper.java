package com.example.cis183_finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String database_name = "Boxd.db";
    private static final String users_table_name = "users";
    private static final String movies_table_name = "movies";
    private static final String reviews_table_name = "reviews";
    public DatabaseHelper(Context c)
    {
        super(c, database_name, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(
                "CREATE TABLE " + users_table_name +
                        "(userid integer primary key autoincrement not null, " +
                        "username text, " +
                        "email text, " +
                        "password text);"
        );
        db.execSQL(
                "CREATE TABLE " + movies_table_name +
                        "(movieid integer primary key autoincrement not null, " +
                        "userid integer, " +
                        "title text, " +
                        "synopsis text, " +
                        "year integer, " +
                        "foreign key(userid) references " + users_table_name + " (userid));"
        );
        db.execSQL(
                "CREATE TABLE " + reviews_table_name +
                        "(reviewid integer primary key autoincrement not null, " +
                        "userid integer, " +
                        "movieid integer, " +
                        "score integer, " +
                        "text text, " +
                        "date text, " +
                        "foreign key(userid) references " + users_table_name + " (userid), " +
                        "foreign key(movieid) references " + movies_table_name + " (movieid));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //delete tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + movies_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + reviews_table_name + ";");

        //recreate tables
        onCreate(db);
    }

    public void initAllTables()
    {
        initUsers();
        initMovies();
        initReviews();
    }
    private void initUsers()
    {
        //only adds if empty
        if(countRecordsFromTable(users_table_name) == 0)
        {
            //get writeable version of database
            SQLiteDatabase db = this.getWritableDatabase();

            //columns: userid (primary key), username, email, password

            db.execSQL("INSERT INTO users (username, email, password) VALUES ('georgeTrucker53', 'countrylivin@gmail.com', 'gogogeoge!67');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('pixelPilot', 'pilot123@example.com', 'flyhigh2024');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('sunnySideUp', 'sunny.eggs@example.com', 'breakfastTime!');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('retroRider', 'retro.rider@example.com', 'arcadeKing44');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('mellowCinnamon', 'cinnamon_rolls@example.com', 'sweettooth99');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('dataDiver', 'diver.data@example.com', 'deepSea$ql');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('coffeeQuant', 'cquant@example.com', 'latteLogic123');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('blueNebula', 'nebula.blue@example.com', 'starfall_777');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('ghostType', 'ghost.type@example.com', 'booPower42');");
            db.execSQL("INSERT INTO users (username, email, password) VALUES ('ironSparrow', 'sparrow.iron@example.com', 'flightForge21');");

            //close database
            db.close();
        }
    }
    private void initMovies()
    {
        if(countRecordsFromTable(movies_table_name) == 0)
        {
            //get writeable version of database
            SQLiteDatabase db = this.getWritableDatabase();

            //columns: movieid (primary key), userid (foreign key), title, synopsis, year

            //user 1
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (1, 'The Count Of Monte Carlo', 'Betrayed by former friends and allies alike, Edward Dente is imprisoned in the castle div and seeks revenge', 2003);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (1, 'Rust Valley Riders', 'A lonely mechanic uncovers a conspiracy hidden within abandoned vehicles.', 2018);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (1, 'Shadows on Route 9', 'A drifter is followed by an unseen presence during a cross-country drive.', 2011);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (1, 'The Last Frame', 'A photographer discovers their pictures predict tragic events.', 2016);");

            //user 2
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (2, 'Pixel Skies', 'A retro pilot enters a glitching digital world to save a lost AI navigator.', 2022);");

            //user 4
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (4, 'Cinnamon Horizon', 'A baker accidentally becomes the face of a revolution after inventing the perfect pastry.', 2019);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (4, 'Sweetwater Waltz', 'Two strangers bond over mysterious letters appearing in their café.', 2014);");

            //user 5
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (5, 'The Deep Query', 'A data scientist discovers encrypted messages hidden in ocean wave patterns.', 1973);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (5, 'Neon Regressor', 'A hacker must regress their own neural memories to stop an AI uprising.', 2020);");

            //user 7
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (7, 'Starless Blue', 'A lone astronomer searches for a missing star only they remember existed.', 2013);");

            //user 8
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (8, 'Shade Protocol', 'A covert agent who can phase through shadows uncovers a world-shaking secret.', 2015);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (8, 'Steel Feathers', 'A bounty hunter with cybernetic wings is torn between freedom and loyalty.', 2023);");
            db.execSQL("INSERT INTO movies (userid, title, synopsis, year) VALUES (8, 'Zero Harbor', 'Detectives in a fog-covered port city investigate vanishings tied to a hidden cult.', 2010);");


            //close database
            db.close();
        }
    }
    private void initReviews()
    {
        //only adds if empty
        if(countRecordsFromTable(reviews_table_name) == 0)
        {
            //get writeable version of database
            SQLiteDatabase db = this.getWritableDatabase();

            //columns: reviewid (primary key), userid (foreign key), movie id (foreign key), score, text, date

            //num of reviews - movies
            //1 - 2,4,5,9,10,11,12,13
            //2 - 1,2,8
            //3 - 7,3
            //5 - 6

            // USER 1 (made movies 1-4)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (1, 6, 3, 'fun concept but pacing felt off', '2025-11-23 19:30:55');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (1, 7, 5, 'this was the greatest movie i have ever seen', '2025-11-23 21:53:10');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (1, 9, 4, 'really engaging mystery, loved the twists', '2025-11-23 19:14:21');");

            // USER 2 (made movies 5)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (2, 3, 2, 'not my cup of tea ngl', '2025-11-23 20:20:19');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (2, 13, 5, 'beautiful visuals, im obsessed', '2025-11-23 20:01:10');");

            // USER 3 (no movies)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (3, 1, 4, 'surprisingly sharp writing, didnt expect it', '2025-11-23 14:11:10');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (3, 3, 4, 'pretty fun watch overall, i''d recommend it', '2025-11-24 18:21:19');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (3, 5, 2, 'cool premise but slow execution', '2025-11-23 15:20:44');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (3, 6, 4, 'i actually liked this way more than i expected', '2025-11-24 14:11:32');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (7, 8, 5, 'one of the best sci-fi stories Ive seen', '2025-11-23 21:03:33');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (3, 10, 5, 'absolute banger, loved every second', '2025-11-23 16:55:02');");

            //user 4 (made movies 6-7)

            // USER 5 (made movies 8-9)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (5, 2, 3, 'cool idea but felt unfinished', '2025-11-23 17:55:02');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (5, 7, 4, 'dark but really thought-provoking', '2025-11-23 17:40:51');");

            // USER 6 (no movies)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (6, 3, 3, 'pretty average but entertaining enough', '2025-11-23 12:44:30');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (6, 6, 3, 'decent but the plot wasn''t super strong', '2025-11-24 17:10:47');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (6, 8, 4, 'really liked the aesthetic and theme', '2025-11-23 18:10:21');");

            // USER 7 (made movies 10)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (7, 1, 4, 'classic revenge done right', '2025-11-23 21:22:18');");

            //user 8 (made movies 11-13)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (8, 6, 2, 'it dragged a lot in the middle, kinda lost me', '2025-11-24 15:02:09');");

            // USER 9 (no movies)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (9, 2, 4, 'honestly way better than expected', '2025-11-23 14:11:52');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (9, 4, 3, 'solid but nothing mindblowing', '2025-11-23 13:32:05');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (9, 6, 5, 'absolutely loved it, genuinely fantastic', '2025-11-24 16:44:55');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (9, 12, 5, 'insanely cool worldbuilding, new favorite', '2025-11-23 11:23:10');");

            // USER 10 (no movies)
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (10, 7, 5, 'i cried real tears, masterpiece.', '2025-11-23 12:15:44');");
            db.execSQL("INSERT INTO reviews (userid, movieid, score, text, date) VALUES (10, 11, 4, 'great atmosphere and vibes', '2025-11-23 13:50:26');");

            //close database
            db.close();
        }
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        //count the number of entries in the table that was passed to the function
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        db.close();

        return numRows;
    }



    public User getUserGivenUname(String uname)
    {
        //query to get the user data
        String selectAll = "SELECT * FROM " + users_table_name + " WHERE username = '" + uname + "';";

        //get a readable version of the database
        SQLiteDatabase db = this.getReadableDatabase();

        //run the query
        Cursor cursor = db.rawQuery(selectAll, null);

        //move the cursor to the first record
        cursor.moveToFirst();

        User user = null;
        if(cursor.moveToFirst())
        {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUname(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        }

        cursor.close();
        db.close();
        
        return user;
    }
    public boolean doesUsernameExist(String u)
    {
        String selectStatement = "SELECT username FROM " + users_table_name + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst())
        {
            do
            {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                if(u.equals(username))
                {
                    cursor.close();
                    db.close();

                    return true;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return false;
    }
    public boolean doesEmailExist(String e)
    {
        String selectStatement = "SELECT email FROM " + users_table_name + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor.moveToFirst())
        {
            do
            {
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                if(e.equals(email))
                {
                    cursor.close();
                    db.close();

                    return true;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return false;
    }

    public void addUserToDatabase(User u)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " +
                users_table_name +
                "(username, email, password) VALUES ('" +
                u.getUname() + "','" +
                u.getEmail() + "','" +
                u.getPassword() + "');");
        //close database
        db.close();
    }
}
