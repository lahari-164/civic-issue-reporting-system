package com.civic.security;

import com.civic.repository.UserRepository;
import com.civic.repository.OfficerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OfficerRepository officerRepository;

    public CustomUserDetailsService(UserRepository userRepository, OfficerRepository officerRepository) {
        this.userRepository = userRepository;
        this.officerRepository = officerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getEmail(), u.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()))))
                .orElseGet(() -> officerRepository.findByEmail(email)
                        .map(o -> new org.springframework.security.core.userdetails.User(
                                o.getEmail(), o.getPassword(),
                                List.of(new SimpleGrantedAuthority("ROLE_OFFICER"))))
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}