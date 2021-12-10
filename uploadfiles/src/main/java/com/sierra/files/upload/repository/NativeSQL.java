package com.sierra.files.upload.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NativeSQL {

	Logger logger = LoggerFactory.getLogger(NativeSQL.class);
	
	private Connection connection = null;
	
	@Autowired
	private Database db;
	
	public Connection createConnection() {
		long startTime = System.currentTimeMillis();
		//logger.info("Java Version: "+com.sap.db.jdbc.Driver.getJavaVersion());
		//logger.info("Minimum Supported Java & SAP Version "+com.sap.db.jdbc.Driver.getVersionInfo());
		
		try {
			connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
			if(connection != null) {
				logger.info("Connection HANA Successfully");				
			}
			long endTime = System.currentTimeMillis();
			long executeTime = endTime-startTime;
			logger.info("Hana Connection Time "+executeTime);
			return connection;
		}catch(SQLException ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}
	
	public void closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
				connection = null;
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
