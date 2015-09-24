package otakuplus.straybird.othellogameserver.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "OthelloUser")
public class User {
	
	//@JsonView(UserView.ClientUser.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	//@JsonView(UserView.ClientUser.class)
	@NotNull
	private String username;
	
	//@JsonView(UserView.ClientUser.class)
	@NotNull
	private String emailAddress;

    @JsonIgnore
	@NotNull
	private String password;

    //@JsonView(UserView.ClientUser.class)
	@CreationTimestamp
	@NotNull
    private Date createTime;
	
	//@JsonView(UserView.ClientUser.class)
	@NotNull
	private boolean isActive = false;
	
	@OneToOne(cascade= CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private UserInformation userInformation;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserOnline userOnline;

    @JsonIgnore
    @OneToMany
    private List<GameRecord> gameRecords;

	public User() {

	}

	public User(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
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

    public UserOnline getUserOnline(){
        return this.userOnline;
    }

    public void setUserOnline(UserOnline userOnline){
        this.userOnline = userOnline;
    }
}
