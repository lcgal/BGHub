package com.example.bghub.data.models.Games;

import com.example.bghub.data.models.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;
import java.util.Locale;

@Table(database = AppDatabase.class)
public class Game extends BaseModel {


    @Column
    @PrimaryKey
    long id;

    @Column
    String name;

    @Column
    String minPlayers;

    @Column
    String maxPlayers;

    @Column
    String thumbnail;

    @Column
    String image;

    @Column
    String description;

    List<Mechanic> mechanics;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "mechanics")
    public List<Mechanic> getMyMechanics() {
        if (mechanics == null || mechanics.isEmpty()) {
            mechanics = SQLite.select()
                    .from(Mechanic.class)
                    .where(Mechanic_Table.gameId.eq(getId()))
                    .queryList();
        }
        return mechanics;
    }

    List<Family> families;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "families")
    public List<Family> getMyFamilies() {
        if (families == null || families.isEmpty()) {
            families = SQLite.select()
                    .from(Family.class)
                    .where(Family_Table.gameId.eq(getId()))
                    .queryList();
        }
        return families;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String toLowerCase(Locale locale) {return this.name.toLowerCase(locale);}

    public String getImage() {return image;}

    public void setImage(String image) {
        this.image = image;}

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;}
}
