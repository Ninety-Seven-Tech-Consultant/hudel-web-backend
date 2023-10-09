package com.hudel.web.backend.rest.web.service;

import com.hudel.web.backend.model.auth.UserDetailsDto;
import com.hudel.web.backend.model.entity.User;
import com.hudel.web.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    return UserDetailsDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(null)
        .build();
  }
}
