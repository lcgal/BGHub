package com.example.bghub.Repositories.Data;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;

import com.example.bghub.BGHubApplication;
import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.GameRooms.GameRoom;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Games.Versions;
import com.example.bghub.Models.Games.Versions_Table;
import com.example.bghub.Models.Session.Credentials;
import com.example.bghub.Models.Session.Profile;
import com.example.bghub.Models.Session.Session;
import com.example.bghub.Models.Session.User;
import com.example.bghub.Models.UserLocation;
import com.facebook.AccessToken;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import static com.example.bghub.Commons.AppConstants.Logged_in;

public class DataRepository implements DataContract.Repository {

    public AccessToken loginToken;

    public static Session session;

    private boolean isGameListReady;
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
    public void startSession () {
        session = new Session();
        saveToDatabase(session);
    }

    @Override
    public Session getSession() {
        if (session == null) {
            session = SQLite.select().from(Session.class).querySingle();
        }
        return session;
    }

    @Override
    public void endSession() {
        session = null;
        Delete.table(Session.class);
    }

    @Override
    public void saveProfile(Profile profile) {

        getSession().setProfile(profile);
        session.setStatus(Logged_in);
        updateDatabase(session);
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

    /**
     * Returns a UserLocation object
     *
     * Tries to fetch user information in order to provide a UserLocation. If the user hasn't set a location yet then use the device location.
     *
     * @author lcgal
     */
    @Override
    public UserLocation getUserLocation()
    {
        if (getSession() != null && session.getProfile() != null && session.getProfile().getUser() != null)
        {
            User user = session.getProfile().getUser();

            if (user.getLatitude() != 0 && user.getLatitude() != 0)
            {
                //TODO create a UserLocation atribute in User
                return new UserLocation(user.getUserId(),user.getLatitude(),user.getLongitude());
            } else {
                updateLocation(BGHubApplication.getAppContext());
                if (mLastLocation != null) {
                    return new UserLocation(user.getUserId(),user.getLatitude(),user.getLongitude());
                }
            }
        }
        return null;

    }

    @Override
    public Location getLocation(){
        return mLastLocation;
    }

    @Override
    public void insertGameRoom (GameRoom gameRoom) {
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                gameRoom.save();
            }
        });
    }

    private void saveToDatabase (BaseModel model) {
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                model.save();
            }
        });

    }

    private void updateDatabase (BaseModel model) {
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                model.update();
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
