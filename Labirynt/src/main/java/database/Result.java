package database;

import java.sql.Time;

public abstract class Result {

    public abstract void setNickname(String nickname);

    public abstract void setTime(Time time);

    public abstract String getNickname();

    public abstract Time getTime();

}
