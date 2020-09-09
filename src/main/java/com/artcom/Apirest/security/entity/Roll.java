package com.artcom.Apirest.security.entity;

import com.artcom.Apirest.security.enums.RollName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Roll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RollName rollName;

    public Roll(){}

    public Roll(@NotNull RollName rollName) {
        this.rollName = rollName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RollName getRollName() {
        return rollName;
    }

    public void setRollName(RollName rollName) {
        this.rollName = rollName;
    }
}
