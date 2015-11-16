package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "OthelloUserInformation")
public class UserInformation {
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    public User user;
    @Id
    private Integer userInformationId;
    //@JsonView(UserInformationView.ClientUserInformation.class)
    private String nickname;

    @Column(columnDefinition = "ENUM('MALE', 'FEMALE', 'SECRET')")
    @NotNull
    private Gender gender;

    //@JsonView(UserInformationView.ClientUserInformation.class)
    private Date birthday;

    //@JsonView(UserInformationView.ClientUserInformation.class)
    private int gameWins = 0;

    //@JsonView(UserInformationView.ClientUserInformation.class)
    private int gameDraws = 0;

    //@JsonView(UserInformationView.ClientUserInformation.class)
    private int gameLosts = 0;

    //@JsonView(UserInformationView.ClientUserInformation.class)
    private int rankPoints = 0;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserInformationId() {
        return userInformationId;
    }

    public void setUserInformationId(Integer userInformationId) {
        this.userInformationId = userInformationId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGameWins() {
        return gameWins;
    }

    public void setGameWins(int gameWins) {
        this.gameWins = gameWins;
    }

    public int getGameDraws() {
        return gameDraws;
    }

    public void setGameDraws(int gameDraws) {
        this.gameDraws = gameDraws;
    }

    public int getGameLosts() {
        return gameLosts;
    }

    public void setGameLosts(int gameLosts) {
        this.gameLosts = gameLosts;
    }

    public int getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(int rankPoints) {
        this.rankPoints = rankPoints;
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }

}
