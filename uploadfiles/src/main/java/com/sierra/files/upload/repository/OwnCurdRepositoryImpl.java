package com.sierra.files.upload.repository;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sierra.files.upload.model.TaskTable;

@Repository
@Transactional
public class OwnCurdRepositoryImpl implements OwnCurdRepository<TaskTable, String> {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TaskTable> findByName(String name) throws SQLException {
		String query1 = "select * from Task where name like ?;";
		Query query = entityManager.createNativeQuery(query1, TaskTable.class);
		query.setParameter(1, name);		
		return query.getResultList();
	}

}
