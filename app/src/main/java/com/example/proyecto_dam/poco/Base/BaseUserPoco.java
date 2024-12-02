package com.example.proyecto_dam.poco.Base;

public abstract class BaseUserPoco extends BasePoco  {
    private long userId;

    public BaseUserPoco(long id, long userId)
    {
        super(id);
        this.userId = userId;
    }

    public long getUserId() {return this.userId;}
    public void setUserId(long id) {this.userId = id;}

}
