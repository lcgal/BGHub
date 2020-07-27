package com.example.bghub.Models.Games;

import com.example.bghub.Models.AppDatabase;
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
    long Id;

    @Column
    String Name;

    @Column
    String MinPlayers;

    @Column
    String MaxPlayers;

    @Column
    String Thumbnail;

    @Column
    String Image;

    @Column
    String Description;

    List<Mechanic> Mechanics;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "Mechanics")
    public List<Mechanic> getMyMechanics() {
        if (Mechanics == null || Mechanics.isEmpty()) {
            Mechanics = SQLite.select()
                    .from(Mechanic.class)
                    .where(Mechanic_Table.GameId.eq(getId()))
                    .queryList();
        }
        return Mechanics;
    }

    List<Family> Families;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "Families")
    public List<Family> getMyFamilies() {
        if (Families == null || Families.isEmpty()) {
            Families = SQLite.select()
                    .from(Family.class)
                    .where(Family_Table.GameId.eq(getId()))
                    .queryList();
        }
        return Families;
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getMinPlayers() {
        return MinPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        this.MinPlayers = minPlayers;
    }

    public String getMaxPlayers() {
        return MaxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.MaxPlayers = maxPlayers;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.Thumbnail = thumbnail;
    }

    public String toLowerCase(Locale locale) {return this.Name.toLowerCase(locale);}

    public String getImage() {return Image;}

    public void setImage(String image) {Image = image;}

    public String getDescription() {return Description;}

    public void setDescription(String description) {Description = description;}
}
