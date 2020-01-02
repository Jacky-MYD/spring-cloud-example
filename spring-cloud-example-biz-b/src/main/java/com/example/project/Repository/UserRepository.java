/**
 * @authour Jacky
 * @data Dec 23, 2019
 */
package com.example.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.project.entity.User;

/**
 * @author Jacky
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	List<User> getByUsername(String username);
//	Page<User> findAll(Pageable pageable);
}
