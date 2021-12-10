package com.sierra.files.upload.service;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.sierra.files.upload.model.TaskTable;
import com.sierra.files.upload.model.assignment;
import com.sierra.files.upload.model.ResourceEntity;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);
  
  public Stream<Path> loadAll();

  public void deleteAll();

  public String readExcel();
  
  public String convert();
  
  public List<TaskTable> getAllTaskDetails();
  
  public List<assignment> getAllAssignments();
  
  public List<ResourceEntity> getAllResourceEntity();
  
  public Optional<TaskTable> findTaskTableById(String id);
  
  public Optional<assignment> findAssignmentById(String id);
  
  public Optional<ResourceEntity> findResourceEntityById(String id);
  
  public List<TaskTable> findByName(String name) throws SQLException;
  
}
