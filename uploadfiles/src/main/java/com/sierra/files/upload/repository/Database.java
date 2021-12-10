package com.sierra.files.upload.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix="database")
public class Database {
	
	Database() {}
		
	String url;
	String username;
	String password;

}
