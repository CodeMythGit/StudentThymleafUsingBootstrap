package com.codemyth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codemyth.entity.Student;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("UPDATE Student s SET s.result = :status WHERE s.id = :id")
	@Modifying
	void updateResultStatus(long id, boolean status);

	List<Student> findByNameContainingIgnoreCase(String keyword);

}
