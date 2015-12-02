package otakuplus.straybird.othellogameserver.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OthelloUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    private String username;

    @NotNull
    private String emailAddress;

    @JsonIgnore
    @NotNull
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:00", timezone = "GMT+8")
    @CreationTimestamp
    private Date createTime;

    @NotNull
    private boolean isActive = true;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserOnline userOnline;
    @JsonIgnore
    @OneToMany(mappedBy = "playerA")
    private Set<GameRecord> gameRecords = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "OTHELLO_MEMBERSHIP",
            joinColumns = {@JoinColumn(name = "user_group_members")},
            inverseJoinColumns = {@JoinColumn(name = "othello_user_group")}
    )
    private UserGroup userGroup;

    @JsonIgnore
    private String socketIOId;

    public User() {

    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public UserOnline getUserOnline() {
        return this.userOnline;
    }

    public void setUserOnline(UserOnline userOnline) {
        this.userOnline = userOnline;
    }

    /*
        public Set<GameRecord> getGameRecords() {
            return gameRecords;
        }

        public void setGameRecords(Set<GameRecord> gameRecords) {
            this.gameRecords = gameRecords;
        }
    */
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public String getSocketIOId() {
        return socketIOId;
    }

    public void setSocketIOId(String socketIOId) {
        this.socketIOId = socketIOId;
    }
}
