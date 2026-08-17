package com.example.capitalmarkets.tradesettlement.user;

import com.example.capitalmarkets.tradesettlement.common.exception.RoleNotFoundException;
import com.example.capitalmarkets.tradesettlement.common.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        Optional<User> u = userRepository.findByUsername(request.username());
        if (u.isPresent()){
            throw new UserAlreadyExistsException("user already exists");
        }
        Set<Role> foundRoles = getValidatedRoles(request.roles());

        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRoles(foundRoles);
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(newUser);

        Set<String> foundRoleNames = foundRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponse(newUser.getId(), newUser.getUsername(),
                newUser.getStatus(),foundRoleNames);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Set<Role> getValidatedRoles(Set<String> roleNames){
        Set<Role> foundRoles = roleRepository.findAllByNameIn(roleNames);

        Set<String> foundRoleNames = foundRoles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        roleNames.removeAll(foundRoleNames);
        if (!roleNames.isEmpty()){
            throw new RoleNotFoundException(roleNames);
        }
        return foundRoles;
    }
}