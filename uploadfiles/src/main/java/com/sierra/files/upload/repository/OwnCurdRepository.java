package com.sierra.files.upload.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.sierra.files.upload.model.TaskTable;

@Transactional
public interface OwnCurdRepository<T, S> {
	
	public List<TaskTable> findByName(String name) throws SQLException;

}
