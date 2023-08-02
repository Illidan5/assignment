package com.serco.managment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serco.managment.model.dto.Response;
import com.serco.managment.model.dto.UserDto;
import com.serco.managment.model.entity.User;
import com.serco.managment.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/users")
	@Operation(summary = "Get users with role", description = "Returns users that have a role")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Ok", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) }) })
	public ResponseEntity<?> getUsersWithRoles() {
		List<User> userList = userService.getUsersWithRole();

		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PostMapping("/users")
	@Operation(summary = "Post new User", description = "Creates a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "400", description = "User already was created", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }) })
	public ResponseEntity<?> createUserWithRoles(@RequestBody User user) {
		User currentUser = userService.getUserById(user.getUser_id());
		if (currentUser != null) {
			return new ResponseEntity<>(
					new Response.ResponseBuilder("UserId " + user.getUser_id() + " already exists").build(),
					HttpStatus.BAD_REQUEST);
		}
		User newUser = userService.createUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@GetMapping("users/{userId}")
	@Operation(summary = "Get user by Id", description = "Returns user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }) })
	public ResponseEntity<?> getUser(@PathVariable Integer userId) {
		User user = userService.getUserById(userId);
		if (user == null) {
			return new ResponseEntity<>(new Response.ResponseBuilder("Could not find user " + userId).build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("users/{userId}")
	@Operation(summary = "Put user by Id", description = "Updates user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }) })
	public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
		User currentUser = userService.getUserById(userId);
		if (currentUser == null) {
			return new ResponseEntity<>(new Response.ResponseBuilder("Could not find user " + userId).build(),
					HttpStatus.NOT_FOUND);
		}
		User modifiedUser = userService.modifyCurrentUser(userDto, userId);
		return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
	}

	@DeleteMapping("users/{userId}")
	@Operation(summary = "Delete user by Id", description = "Deletes user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }) })
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		User user = userService.getUserById(userId);
		if (user == null) {
			return new ResponseEntity<>(new Response.ResponseBuilder("Could not find user " + userId).build(),
					HttpStatus.NOT_FOUND);
		} else {
			userService.deleteUser(user);
		}
		return new ResponseEntity<>(new Response.ResponseBuilder("userId " + userId + " deleted").build(),
				HttpStatus.OK);
	}

	@DeleteMapping("users/{userId}/roles/{roleId}")
	@Operation(summary = "Delete role by roleId", description = "Deletes role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ok", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found or Role not found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }) })
	public ResponseEntity<?> deleteRole(@PathVariable Integer userId, @PathVariable Integer roleId) {

		User user = userService.getUserById(userId);
		if (user == null) {
			return new ResponseEntity<>(new Response.ResponseBuilder("Could not find user " + userId).build(),
					HttpStatus.NOT_FOUND);
		} else if (user.getRoleList().stream().anyMatch(u -> u != null && u.getRole_id() == roleId)) {
			userService.deleteRoleOnUser(user, roleId);
		} else {

			return new ResponseEntity<>(
					new Response.ResponseBuilder("Could not find role " + roleId + " on " + userId).build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new Response.ResponseBuilder("roleId " + roleId + " was deleted on userId " + userId).build(),
				HttpStatus.OK);
	}

}
