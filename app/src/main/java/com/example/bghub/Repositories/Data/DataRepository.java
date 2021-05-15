package com.example.bghub.Repositories.Data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.bghub.BGHubApplication;
import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.GameRooms.GameRoom;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Games.Game_Table;
import com.example.bghub.Models.Games.Versions;
import com.example.bghub.Models.Games.Versions_Table;
import com.example.bghub.Models.User;
import com.example.bghub.Models.UserLocation;
import com.facebook.AccessToken;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.example.bghub.Commons.AppConstants.Logged_in;

public class DataRepository implements DataContract.Repository {

    private boolean isGameListReady;
    public static List<Game> gamesList;

    public static List<GameRoom> gameRooms;

    public static Location mLastLocation;

    private static final int LOCATION_PERMISSION_CODE = 100;

    private static FirebaseFirestore fbdatabase;

    private static FirebaseAuth fbauth;

    @Inject
    public DataRepository() {

        fbauth = FirebaseAuth.getInstance();
        fbdatabase = FirebaseFirestore.getInstance();
    }

    @Override
    public void saveUserInfo(User user) {
        DocumentReference documentReference = fbdatabase.collection("Users").document(user.getId());

        Map<String,Object> dbUser = new HashMap<>();
        dbUser.put("firstName",user.getFirstName());
        dbUser.put("lastName",user.getLastName());
        dbUser.put("email",user.getEmail());
        dbUser.put("latitude",user.getLatitude());
        dbUser.put("longitude",user.getLongitude());
        dbUser.put("description",user.getDescription());
        dbUser.put("photoURL",user.getPhotoUrl());

        documentReference.set(dbUser);

        updateDatabase(user);
    }

    @Override
    public User getDbUserInfo(User user) {

        DocumentReference documentReference = fbdatabase.collection("Users").document(user.getId());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    if (value.getData() == null) {
                        saveUserInfo(user);
                        saveModel(user);
                    }
                    user.setFirstName(value.getData().get("firstName") != null ? value.getData().get("firstName").toString() : null);
                    user.setLastName(value.getData().get("lastName") != null ? value.getData().get("lastName").toString() : null);
                    user.setEmail(value.getData().get("email") != null ? value.getData().get("email").toString() : null);
                    user.setLatitude(value.getData().get("latitude") != null ? Double.parseDouble(value.getData().get("latitude").toString()) : 0);
                    user.setLongitude(value.getData().get("longitude") != null ? Double.parseDouble(value.getData().get("longitude").toString()) : 0);
                    user.setDescription(value.getData().get("description") != null ? value.getData().get("description").toString() : null);
                    user.setPhotoUrl(value.getData().get("photoUrl") != null ? value.getData().get("photoUrl").toString() : null);
                    saveModel(user);
                } else {
                    //TODO error treatment
                    String teste = "'1'";
                }

            }
        });
        return null;
    }

    @Override
    public void processLogin() {
        User user = new User();
        user.setId(fbauth.getCurrentUser().getUid());
        user.setFirstName(fbauth.getCurrentUser().getDisplayName());
        user.setEmail(fbauth.getCurrentUser().getEmail());

        user.setFirstName("teste");
        getDbUserInfo(user);
    }

    @Override
    public List<Game> getGamesList() {
        if (gamesList == null) {
            gamesList = SQLite.select().from(Game.class).queryList();
        }
        return gamesList;
    }

    @Override
    public List<GameRoom> getGameRooms() {
        if (gameRooms == null) {
            gameRooms = SQLite.select().from(GameRoom.class).queryList();
        }
        return gameRooms;
    }

    @Override
    public List<Game> getGamesByIds(Collection<Long> gameIds) {
        return SQLite.select()
                .from(Game.class)
                .where((Game_Table.Id.in(gameIds)))
                .queryResults()
                .toListClose();
    }

    @Override
    public Game getGameById(long gameId) {
        return SQLite.select()
                .from(Game.class)
                .where((Game_Table.Id.eq(gameId)))
                .querySingle();
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
    public void updateGameDescription (long gameid, String description) {
        Game game = SQLite.select().from(Game.class).where(Game_Table.Id.eq(gameid)).querySingle();
        game.setDescription(description);
        game.save();
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
    public void saveGameRooms(List<GameRoom> gameRooms) {
        DataRepository.gameRooms = gameRooms;

        insertGameRoomsToDB(gameRooms);
    }

    @Override
    public void updateLocation(Context context) {
        //Checking permission and getting last known location from LocationClient
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
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
        return null;
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

    @Override
    public Location getLocation(){
        return mLastLocation;
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

        //TODO check for errors
        executeSaveTransaction(fastModel);

        return true;

    }

    private boolean insertGameRoomsToDB(List<GameRoom> gamesRoomList){
        Delete.table(GameRoom.class);

        FastStoreModelTransaction fastModel = FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(GameRoom.class))
                .addAll(gamesRoomList)
                .build();

        //TODO check for errors
        executeSaveTransaction(fastModel);

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

    private static <M extends BaseModel> void saveModel(M model) {
        if (model != null) {
            FlowManager.getDatabase(AppDatabase.class).executeTransaction(databaseWrapper -> model.save());
        }
    }

    private static <M extends BaseModel> void saveModelsList(List<M> models) {
        if (models != null) {
            for (M model : models) {
                FlowManager.getDatabase(AppDatabase.class).executeTransaction(databaseWrapper -> model.save());
            }
        }
    }

}
