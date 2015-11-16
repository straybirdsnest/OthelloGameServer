package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OthelloUserGroup")
public class UserGroup {
    @Id
    @GeneratedValue
    private Integer userGroupId;

    // GroupName in database must follow the format of "ROLE_XXX"
    @NotNull
    private String userGroupName;

    @OneToMany(mappedBy = "userGroup")
    private Set<User> userGroupMembers = new HashSet<>();

    public UserGroup() {

    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}
