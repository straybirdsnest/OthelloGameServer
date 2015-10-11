package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "OthelloUserGroup")
public class UserGroup {
    @Id
    @GeneratedValue
    private Long userGroupId;

    @NotNull
    private String userGroupName;

    @OneToMany
    private List<User> userGroupMembers;

    public UserGroup(){

    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}
