package by.tux.PhotoAppServer.auth.service;

import by.tux.PhotoAppServer.auth.model.UserModel;
import by.tux.PhotoAppServer.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserModel myUserModel = userRepository.findByLogin(userName);
        if (myUserModel == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUserModel.getLogin())
                .password(myUserModel.getPassword())
                .roles(myUserModel.getRole())
                .build();
        return user;
    }
}
