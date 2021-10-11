package database;

import javax.persistence.*;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Entity
@Table(name="easy")
public class EasyResult extends Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="nickname")
    private String nickname;

    @Column(name="time")
    private Time time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return nickname + " " + new SimpleDateFormat("HH:mm:ss.SS").format(time);
    }
}
