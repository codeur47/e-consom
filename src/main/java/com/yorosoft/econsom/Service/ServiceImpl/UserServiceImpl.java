package com.yorosoft.econsom.Service.ServiceImpl;

import com.yorosoft.econsom.Dao.RoleDao;
import com.yorosoft.econsom.Dao.UserDao;
import com.yorosoft.econsom.Service.UserService;
import com.yorosoft.econsom.models.User;
import com.yorosoft.econsom.models.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());

        if (localUser != null)
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.setCreatedBy(user.getUsername());
            user.setCreatedDate(new Date());
            user.getUserRoles().addAll(userRoles);
            localUser = userDao.save(user);
        }

        return localUser;
    }

    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username))
            return true;
        else
            return false;

    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username))
            return true;

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email))
            return true;

        return false;
    }


    public User saveUser (User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> findUserList() {
        return userDao.findAll();
    }

    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userDao.save(user);
    }

    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);
        System.out.println(user.isEnabled());
        userDao.save(user);
        System.out.println(username + " is disabled.");
    }
}
