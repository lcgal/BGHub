package com.example.bghub.Repositories.Data;

import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.Games.Game;
import com.example.bghub.Models.Games.Versions;
import com.example.bghub.Models.Games.Versions_Table;
import com.example.bghub.Models.Session;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.Model;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

import javax.inject.Inject;

public class DataRepository implements DataContract.Repository {

    public AccessToken loginToken;

    public static Session session;

    public static List<Game> gamesList;

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
        return session;
    }

    @Override
    public void saveSession(Session session) {
        this.session = session;
    }

    @Override
    public void changeSessionStatus (int status){
        session.setStatus(status);
    }

    @Override
    public List<Game> getGamesList() {
        return gamesList;
    }

    @Override
    public void saveGamesList(List<Game> gamesList, String version)
    {
        DataRepository.gamesList = gamesList;

        updateDBVersion(version);

        insertGamesToDB(gamesList);

    }

    @Override
    public void setGamesList(){
        gamesList = SQLite.select().from(Game.class).queryList();
    }

    @Override
    public String getGamesListVersion()
    {

        Versions versionObj = SQLite.select().from(Versions.class).where(Versions_Table.Key.eq("GamesListVersion")).querySingle();
        if (versionObj != null){
            return versionObj.getValue();
        } else {
            return"0";
        }
    }

    private void insertGamesToDB(List<Game> gamesList){
        Delete.table(Game.class);
        FastStoreModelTransaction fastModel = FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(Game.class))
                .addAll(gamesList)
                .build();

        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        Transaction transaction = database.beginTransactionAsync(fastModel).build();
        transaction.execute();

    }

    private void updateDBVersion(String version){
        Versions dbVersion = SQLite.select().from(Versions.class).where(Versions_Table.Key.eq("GamesListVersion")).querySingle();
        //TODO Entender melhor como utilizar o DBFLOW.
        if (dbVersion != null){
            SQLite.update(Versions.class)
                    .set(Versions_Table.value.eq(version))
                    .where(Versions_Table.Key.is("GamesListVersion"))
                    .execute(); // non-UI blocking
        } else {
            SQLite.insert(Versions.class)
                    .columns(Versions_Table.Key,Versions_Table.value)
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
