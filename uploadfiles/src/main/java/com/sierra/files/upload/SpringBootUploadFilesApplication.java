package com.sierra.files.upload;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.sierra.files.upload.model.TaskTable;

import com.sierra.files.upload.service.FilesStorageService;

@SpringBootApplication
public class SpringBootUploadFilesApplication implements CommandLineRunner {
  @Resource
  FilesStorageService storageService;
  
  @Autowired
  FilesStorageService fileStorageService;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootUploadFilesApplication.class, args);
  }
  
  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	  return args -> {
		  System.out.println(fileStorageService.findAssignmentById("1L"));
	  };
  }

  @Override
  public void run(String... arg) throws Exception {
    storageService.deleteAll();
    storageService.init();
  }
}
