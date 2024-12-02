package com.example.proyecto_dam.poco.Base;

public abstract class BasePoco {
    private long Id;

    public BasePoco(long id) {
        this.Id = id;
    }

    public boolean HasBeenPersistence() {
        return this.Id != 0;
    }

    public long getId() {return this.Id;}
    public void setId(long id) {this.Id = id;}

}
