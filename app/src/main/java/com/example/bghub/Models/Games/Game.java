package com.example.bghub.Models.Games;

import com.example.bghub.Models.AppDatabase;
import com.example.bghub.Models.Credentials;
import com.example.bghub.Models.User;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = AppDatabase.class)
public class Game extends BaseModel {


    @Column
    @PrimaryKey
    float id;

    @Column
    String name;

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

    private List<Family> families;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "mechanics")
    public List<Family> getMyFamilies() {
        if (families == null || families.isEmpty()) {
            families = SQLite.select()
                    .from(Family.class)
                    .where(Family_Table.gameId.eq(getId()))
                    .queryList();
        }
        return families;
    }


    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
