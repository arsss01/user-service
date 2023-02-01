package kz.postavshik.userservice.service;

import kz.postavshik.userservice.dto.UserDto;
import kz.postavshik.userservice.entity.User;
import kz.postavshik.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UUID createUser(UserDto dto) {
        User user = mapper.map(dto, User.class);
        user.setUserId(UUID.randomUUID());
        user.setPhone("+77072377772");
        user.setStatus("WAITING_FOR_CONFIRMATION");
        user.setType("CLIENT");

        return userRepository.save(user).getUserId();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserByEmail(email);
    }

    public UserDto getUserByEmail(String email) {
        return mapper.map(userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email)),
                UserDto.class);
    }

    public void throwErrorIfUserExitsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Error: Email is already in use!");
        }
    }
}
