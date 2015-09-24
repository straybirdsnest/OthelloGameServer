package otakuplus.straybird.othellogameserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import otakuplus.straybird.othellogameserver.models.SecurityUser;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserRepository;

@Service
public class OthelloUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("UserName "+username+" not found.");
        }
        return new SecurityUser(user);
    }
}
