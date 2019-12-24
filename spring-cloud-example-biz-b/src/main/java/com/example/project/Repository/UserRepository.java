/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Entity.User;

/**
 * @author Jacky
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> getByUsername(String username);
}
