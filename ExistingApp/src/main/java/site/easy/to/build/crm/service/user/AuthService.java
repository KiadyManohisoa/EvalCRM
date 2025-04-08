package site.easy.to.build.crm.service.user;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.models.AuthRequest;
import site.easy.to.build.crm.repository.UserRepository;

@Service
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User checkLogin(AuthRequest authRequest) throws Exception, UsernameNotFoundException {
        User theUser = null;
        List<User> users = userRepository.findByUsername(authRequest.getUsername());
        if(users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        else if (!users.isEmpty()) {
            for(User user : users) {
                if(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                    theUser = user;
                    break;
                }
            }
        }
        if(theUser == null) {
            throw new Exception("Username or password invalid");
        }
        return theUser;
    }


}
