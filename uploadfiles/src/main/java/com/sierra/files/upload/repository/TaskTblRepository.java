package com.sierra.files.upload.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sierra.files.upload.model.TaskTable;

@Transactional
public interface TaskTblRepository extends CrudRepository<TaskTable, String> {
	
	

}
