package by.vitikova.spring.mvc.config.service;

import by.vitikova.spring.mvc.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (userClient.findByLogin(username).isPresent()) {
            return userClient.findByLogin(username).get();
        } else {
            throw new UsernameNotFoundException("???");
        }
    }
}