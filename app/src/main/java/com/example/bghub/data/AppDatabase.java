package com.example.bghub.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.bghub.data.daos.GameDao;
import com.example.bghub.data.daos.UserDao;
import com.example.bghub.data.models.Games.Family;
import com.example.bghub.data.models.Games.GameEntity;
import com.example.bghub.data.models.Games.Mechanic;
import com.example.bghub.data.models.Games.Version;
import com.example.bghub.data.models.users.User;

@Database(entities = {GameEntity.class,
        Family.class,
        Mechanic.class,
        Version.class,
        User.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract GameDao gameDao();
}