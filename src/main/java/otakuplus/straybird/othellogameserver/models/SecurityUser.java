package otakuplus.straybird.othellogameserver.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends User implements UserDetails{

    public SecurityUser(User user){
        if(user != null){
            this.setUserId(user.getUserId());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setEmailAddress(user.getEmailAddress());
            this.setCreateTime(user.getCreateTime());
            this.setIsActive(user.getIsActive());
            this.setUserInformation(user.getUserInformation());
            this.setUserOnline(user.getUserOnline());
            this.setUserGroup(user.getUserGroup());
            this.setSocketIOId(user.getSocketIOId());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(getUserGroup().getUserGroupName()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.getIsActive();
    }
}
