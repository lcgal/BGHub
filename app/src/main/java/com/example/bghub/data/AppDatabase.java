package com.example.bghub.data;

import androidx.room.Database;

import com.example.bghub.data.daos.UserDao;
import com.example.bghub.data.models.Games.Family;
import com.example.bghub.data.models.Games.Game;
import com.example.bghub.data.models.Games.Mechanic;
import com.example.bghub.data.models.Games.Version;
import com.example.bghub.data.models.users.User;

@Database(entities = {Game.class,
        Family.class,
        Mechanic.class,
        Version.class,
        User.class},
        version = 1)
public abstract  class AppDatabase {
    public abstract UserDao userDao();

}