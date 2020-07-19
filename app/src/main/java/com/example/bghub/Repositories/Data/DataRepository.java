package com.example.bghub.Repositories.Data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Games.Versions;
import com.example.bghub.Models.Games.Versions_Table;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.Session.Session;
import com.facebook.AccessToken;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import javax.inject.Inject;

public class DataRepository implements DataContract.Repository {

    public AccessToken loginToken;

    public static Session session;

    public static List<Game> gamesList;

    public static Location mLastLocation;

    private static final int LOCATION_PERMISSION_CODE = 100;

    @Inject
    public DataRepository() {
    }

    @Override
    public AccessToken getLoginToken() {
        return loginToken;
    }

    @Override
    public void saveLoginToken(AccessToken loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public Session getSession() {
        if (session == null){
            session = new Session();
            session.setProfile(SQLite.select().from(Profile.class).querySingle());
            if(session.getProfile() != null) {
                session.setStatus(1);
            } else {
                session.setStatus(0);
            }
        }
        return session;
    }

    @Override
    public void saveSession(Session session) {
        this.session = session;
    }

    @Override
    public void changeSessionStatus(int status) {
        session.setStatus(status);
    }

    @Override
    public List<Game> getGamesList() {
        return gamesList;
    }

    @Override
    public void saveGamesList(List<Game> gamesList, String version) {
        DataRepository.gamesList = gamesList;

        try {
            if (insertGamesToDB(gamesList)) {
                updateDBVersion(version);
            }
        } catch (RuntimeException e) {
            String teste = e.getMessage();
            return;
        }
    }

    @Override
    public void setGamesList() {
        gamesList = SQLite.select().from(Game.class).queryList();
    }

    @Override
    public String getGamesListVersion() {
        Versions versionObj = SQLite.select().from(Versions.class).where(Versions_Table.Key.eq("GamesListVersion")).querySingle();
        if (versionObj != null) {
            return versionObj.getValue();
        } else {
            return "0";
        }
    }

    @Override
    public void updateLocation(Context context) {
        //Checking permission and getting last known location from LocationClient
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions ((Activity) context,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLastLocation = location;
                        }
                    }
                });
        return;
    }

    @Override
    public Location getLocation(){
        return mLastLocation;
    }

    @Override
    public void saveProfile (Profile profile) {
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                profile.save();
            }
        });
    }

    private boolean insertGamesToDB(List<Game> gamesList){
        Delete.table(Game.class);

        FastStoreModelTransaction fastModel = FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(Game.class))
                .addAll(gamesList)
                .build();

        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        Transaction transaction = database.beginTransactionAsync(fastModel).build();
        //TODO catch errors on the transaction execution. Try catching the method does not seem to work.
        transaction.execute();

        return true;

    }



    private void updateDBVersion(String version){
        Versions dbVersion = SQLite.select().from(Versions.class).where(Versions_Table.Key.eq("GamesListVersion")).querySingle();
        //TODO Entender melhor como utilizar o DBFLOW.
        if (dbVersion != null){
            SQLite.update(Versions.class)
                    .set(Versions_Table.Value.eq(version))
                    .where(Versions_Table.Key.is("GamesListVersion"))
                    .execute(); // non-UI blocking
        } else {
            SQLite.insert(Versions.class)
                    .columns(Versions_Table.Key,Versions_Table.Value)
                    .values("GamesListVersion",version)
                    .execute(); // non-UI blocking
        }
    }

    private static void executeSaveTransaction(FastStoreModelTransaction fastModel) {
        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        Transaction transaction = database.beginTransactionAsync(fastModel).build();
        transaction.execute();
    }

}
