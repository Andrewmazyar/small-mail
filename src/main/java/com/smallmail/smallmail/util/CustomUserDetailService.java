package com.smallmail.smallmail.util;

import com.smallmail.smallmail.Service.UserService;
import com.smallmail.smallmail.model.entity.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        UserBuilder userBuilder;
        if (user != null) {
            userBuilder = withUsername(email);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getRoles()
                    .stream()
                    .map(role -> role.getRoleName().name())
                    .toArray(String[]::new));
        } else {
            throw new UsernameNotFoundException("No such User");
        }
        return userBuilder.build();
    }
}
