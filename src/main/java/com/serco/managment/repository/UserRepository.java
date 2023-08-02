package com.serco.managment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serco.managment.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "Select * from users u " + "inner join user_role r on u.user_id = r.user_id", nativeQuery = true)
	public List<User> findAllHasRole();
}
