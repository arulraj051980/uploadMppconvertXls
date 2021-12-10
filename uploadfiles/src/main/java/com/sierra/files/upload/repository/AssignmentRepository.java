package com.sierra.files.upload.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sierra.files.upload.model.assignment;

@Transactional
public interface AssignmentRepository extends CrudRepository<assignment, String> {

}
