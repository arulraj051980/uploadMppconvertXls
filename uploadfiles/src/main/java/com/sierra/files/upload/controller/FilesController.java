package com.sierra.files.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.sierra.files.upload.message.ResponseMessage;
import com.sierra.files.upload.model.FileInfo;
import com.sierra.files.upload.model.ResourceEntity;
import com.sierra.files.upload.model.TaskTable;
import com.sierra.files.upload.model.assignment;
import com.sierra.files.upload.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {
	
  

  @Autowired
  FilesStorageService storageService;

  @PostMapping(value = "/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  
  @PostMapping("/convertexcel") 
  public ResponseEntity<ResponseMessage> converToExcel() {
	String message = "";
	
  	try {
  		message = storageService.convert();
  		message = "Converted to excel file successfully: ";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
  	}catch(Exception e) {
  		message = "Could not convert the file to excel: ";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
  	}  	
  	
  }
  
  @PostMapping("/readExcel")
  public ResponseEntity<ResponseMessage> readExcel() {
	  String message = "";
	  try {
		  message = storageService.readExcel();
		  message = "This excel file read and write the DB...";
		  return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	  }catch(Exception e) {
		  message = "Could not read the excel file:";
		  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	  }
  }
  
  @GetMapping("/getTaskDetails")
  public ResponseEntity<List<TaskTable>> getTaskDetails() {
	  String message = null;
	  List<TaskTable> task = new ArrayList<TaskTable>();
	  try {		  
		  task = storageService.getAllTaskDetails();
		  message = "Get all records from TaskTable...";
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  message = "Issues are happend to reterive the Tasktable...";
		  
	  }
	return ResponseEntity.status(HttpStatus.OK).body(task);
  }
  
  @GetMapping("/getAssignmentDetails")
  public ResponseEntity<List<assignment>> getAllAssignmentDetails() {
	  String message = null;
	  List<assignment> assignment = new ArrayList<assignment>();
	  try {
		  assignment = storageService.getAllAssignments();
		  message = "Get all records from Assignment Table...";
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  message = "Issues are happend to reterive the Assignment Table...";
	  }
	  return ResponseEntity.status(HttpStatus.OK).body(assignment);
  }
  
  @GetMapping("/getResourceEntityDetails")
  public ResponseEntity<List<ResourceEntity>> getAllResourceEntity() {
	  String message = null;
	  List<ResourceEntity> resource = new ArrayList<ResourceEntity>();
	  try {
		  resource = storageService.getAllResourceEntity();
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  message = "Issues are happend to reterive the Resource Entity Table...";
	  }
	  return ResponseEntity.status(HttpStatus.OK).body(resource);
  }
  
  @GetMapping("/getTaskName/{name}")
  public ResponseEntity<List<TaskTable>> getTaskName(@PathVariable String name) throws SQLException {
	  String message = null;
	  List<TaskTable> tskTbl = new ArrayList<TaskTable>();
	  try {
		  tskTbl = storageService.findByName(name);
	  }catch(Exception ex) {
		  ex.printStackTrace();
		  message = "Issues are happend to reterive the TaskTable ....";
	  }
	  return ResponseEntity.status(HttpStatus.OK).body(tskTbl);
  }
  
  
}
