package com.ripanhalder.service;

import com.ripanhalder.dao.RoleDao;
import com.ripanhalder.dao.UserDao;
import com.ripanhalder.entity.Role;
import com.ripanhalder.entity.User;
import com.ripanhalder.user.RegisterationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder pswdEncoderObj;

	@Override
	@Transactional
	public User searchByUName(String userName) {
		// check the database if the user already exists
		return userDao.searchByUName(userName);
	}

	@Override
	@Transactional
	public void save(RegisterationUser registerationUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(registerationUser.getUserName());
		user.setPassword(pswdEncoderObj.encode(registerationUser.getPassword()));
		user.setFirstName(registerationUser.getFirstName());
		user.setLastName(registerationUser.getLastName());
		user.setEmail(registerationUser.getEmail());

		// give user default role of "user"
		user.setRoles(Arrays.asList(roleDao.searchRoleByName("ROLE_USER")));

		 // save user in the database
		userDao.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.searchByUName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
