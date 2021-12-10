package com.sierra.files.upload.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sierra.files.upload.model.TaskTable;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
	
	Logger logger = Logger.getLogger(TaskRepositoryImpl.class);
	
	@Autowired
	private NativeSQL nativeSQL;
	
	@Override
	public void saveTask(TaskTable task) {
		// TODO Auto-generated method stub
		Connection con = nativeSQL.createConnection();
		if(con!=null) {
			try {
				long startTime = System.currentTimeMillis();
				PreparedStatement stmt = con.prepareStatement("INSERT INTO DMO.TASK (ACTIVE, TASKMODE, NAME, DURATION, "
						+ "STARTTIME, FINISH, PREDECESSORS, OUTLINELEVEL, NOTES) VALUES (?,?,?,?,?,?,?,?,?)");
				//stmt.setBigDecimal(1, new BigDecimal(task.getID()));
				stmt.setNString(2, task.getActive());
				stmt.setNString(3, task.getTaskMode());
				stmt.setNString(4, task.getName());
				stmt.setNString(5, task.getDuration());
				stmt.setNString(6, task.getStart());
				stmt.setNString(7, task.getFinish());
				stmt.setNString(8, task.getPredecessors());
				stmt.setNString(9, task.getOutlineLevel());
				stmt.setNString(10, task.getNotes());
				stmt.executeUpdate();
				long endTime = System.currentTimeMillis();
				long executeTime = endTime - startTime;
				logger.info("Insert Job: "+task.getID()+":"+executeTime);
			}catch(SQLException sql) {
				logger.error(sql.getMessage());
				logger.error("Insert Failed...");
			}
		}
	}

}
