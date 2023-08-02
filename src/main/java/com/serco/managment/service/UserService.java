package com.serco.managment.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serco.managment.model.dto.UserDto;
import com.serco.managment.model.entity.Role;
import com.serco.managment.model.entity.User;
import com.serco.managment.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * Get users with role
	 * 
	 * @return
	 */
	public List<User> getUsersWithRole() {
		List<User> userList = userRepository.findAllHasRole();
		return userList;
	}

	/**
	 * Given user create new user
	 * 
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		User newUser = userRepository.save(user);
		return newUser;
	}

	/**
	 * Get user from id
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	/**
	 * Given userDto and userId update row
	 * 
	 * @param userDto
	 * @param userId
	 * @return
	 */
	public User modifyCurrentUser(UserDto userDto, Integer userId) {
		User currentUser = getUserById(userId);
		if (currentUser == null) {
			return null;
		} else {

			modifyUser(currentUser, userDto);
			User updatedUser = userRepository.save(currentUser);
			return updatedUser;

		}
	}

	/**
	 * Given currentUser and userDto load data
	 * 
	 * @param currentUser
	 * @param userDto
	 */
	public void modifyUser(User currentUser, UserDto userDto) {

		if (userDto.getUser_name() != null) {
			currentUser.setUser_name(userDto.getUser_name());
		}
		if (userDto.getUser_password() != null) {
			currentUser.setUser_password(userDto.getUser_password());
		}
	}

	/**
	 * Given user delete user
	 * 
	 * @param user
	 */

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	/**
	 * Given user and roleId delete the role
	 * 
	 * @param user
	 * @param roleId
	 */
	public void deleteRoleOnUser(User user, Integer roleId) {
		Iterator<Role> iterator = user.getRoleList().iterator();
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (roleId == role.getRole_id()) {
				iterator.remove();
			}
		}

		userRepository.save(user);
	}

}
