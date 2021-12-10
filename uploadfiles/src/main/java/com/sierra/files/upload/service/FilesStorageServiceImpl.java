package com.sierra.files.upload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.tasks.private_.id.ce;
import com.sierra.files.upload.model.ResourceEntity;
import com.sierra.files.upload.model.TaskTable;
import com.sierra.files.upload.model.assignment;
import com.sierra.files.upload.repository.AssignmentRepository;
import com.sierra.files.upload.repository.OwnCurdRepository;
import com.sierra.files.upload.repository.ResourceRepository;
import com.sierra.files.upload.repository.TaskRepository;
import com.sierra.files.upload.repository.TaskTblRepository;
import com.sierra.files.upload.utils.Utils;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

 @Autowired
 public Utils utils;
 
 @Autowired
 private TaskTblRepository repository;
 
 @Autowired
 private AssignmentRepository assignmentRep;
 
 @Autowired
 private ResourceRepository resRepository;
 
 @Autowired
 private OwnCurdRepository ownRepository;
 
 public XSSFSheet mySheet = null;
	
  //private final String uploadExcel = "uploadFiles/";
  private Path root = Paths.get("uploadFiles");
  
  @Override
  public void init() {
    try {
      Files.createDirectory(root);      
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file) {
	try {
		root = Paths.get("uploadFiles");
		if(!Files.exists(root)) {
			init();
		}    
    	Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));    	
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }    
  }

  @Override
  public Resource load(String filename) {
	  try {
	      Path file = Paths.get("uploadFiles").resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
  }

  @Override
  public void deleteAll() {
	  FileSystemUtils.deleteRecursively(Paths.get("uploadFiles").toFile());
  }


@Override
public String convert() {
	System.out.println("Path is "+this.root.getFileSystem().toString());	
	String path = Paths.get("uploadFiles").toAbsolutePath().toString();
	File file = utils.getLatestFilefromDir(path+"\\");
	
	try {
		utils.savemsstoxls(path+"\\", file);		
	} catch (IOException e) {		
		e.printStackTrace();
	}
	
	return "Success";
}

@Override
public String readExcel() { 
try {
		//String status = ""
		String path = Paths.get("uploadFiles").toAbsolutePath().toString();
		System.out.println("Path is "+path);
		
		File file = utils.getLatestFilefromDir(path+"\\");
		
		System.out.println("Stage One "+file.getName());
		System.out.println("File path is "+path+"\\"+file.getName());
		String pathFile = path+"\\"+file.getName();
		
		FileInputStream fis = new FileInputStream(new File(pathFile));
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		
		int TotSheet = myWorkBook.getNumberOfSheets();
			
		mySheet = myWorkBook.getSheetAt(0);
		
		int rows = mySheet.getPhysicalNumberOfRows();
				
		for(int i=1; i<rows; i++) {
			Row row = mySheet.getRow(i);
			
			TaskTable task = new TaskTable();
			
				task.setID(row.getCell(0).getStringCellValue());
				task.setActive(row.getCell(1).getStringCellValue());
				task.setTaskMode(row.getCell(2).getStringCellValue());
				task.setName(row.getCell(3).getStringCellValue());
				task.setDuration(row.getCell(4).getStringCellValue());
				task.setStart(row.getCell(5).getStringCellValue());
				task.setFinish(row.getCell(6).getStringCellValue());												
				task.setPredecessors(row.getCell(7).getStringCellValue());
				task.setOutlineLevel(row.getCell(8).getStringCellValue());
				task.setNotes(row.getCell(9).getStringCellValue());
				//taskRepository.saveTask(task);
				repository.save(task);			
		}
		
		if(TotSheet > 0) {
			mySheet = myWorkBook.getSheetAt(1);
		}		
		
		int rowsS3 = mySheet.getPhysicalNumberOfRows();
		
		for(int k=1; k<rowsS3; k++) {
			Row row = mySheet.getRow(k);
			ResourceEntity resource = new ResourceEntity();
			//resource.setRid(row.getCell(0).getStringCellValue());
			resource.setIndicators(row.getCell(1).getStringCellValue());
			resource.setResource_name(row.getCell(2).getStringCellValue());
			resource.setType(row.getCell(3).getStringCellValue());
			resource.setMaterial_label(row.getCell(4).getStringCellValue());
			resource.setInitials(row.getCell(5).getStringCellValue());
			resource.setGroups(row.getCell(6).getStringCellValue());
			resource.setMaxunits(row.getCell(7).getStringCellValue());
			resource.setStdrate(row.getCell(8).getStringCellValue());
			resource.setOvtrate(row.getCell(9).getStringCellValue());
			resource.setCost(row.getCell(10).getStringCellValue());
			resource.setAccrue(row.getCell(11).getStringCellValue());
			resource.setBasecalendar(row.getCell(12).getStringCellValue());
			resource.setCode(row.getCell(13).getStringCellValue());
			resRepository.save(resource);
		}
		
		if(TotSheet > 1) {
			mySheet = myWorkBook.getSheetAt(2);
		}
		
		int rowsS2 = mySheet.getPhysicalNumberOfRows();
		System.out.println("Sheet Two Rows "+rowsS2);
		
		for(int j=1; j<rowsS2; j++) {
			Row row = mySheet.getRow(j);
			
			assignment assign = new assignment();
			
			assign.setTaskname(row.getCell(1).getStringCellValue());
			assign.setResourcename(row.getCell(2).getStringCellValue());
			assign.setWork(row.getCell(3).getStringCellValue());
			assign.setDuration(row.getCell(4).getStringCellValue());
			
			assignmentRep.save(assign);
		}

		
		return "Success";
	
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return null;
}
}

@Override
public Stream<Path> loadAll() {
  try {
    return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
  } catch (IOException e) {
    throw new RuntimeException("Could not load the files!");
  }
}

@Override
public List<TaskTable> getAllTaskDetails() {
	List<TaskTable> listTask = (List<TaskTable>) repository.findAll();
	return listTask;
}

@Override
public List<assignment> getAllAssignments() {
	List<assignment> listAssignment = (List<assignment>) assignmentRep.findAll();
	return listAssignment;
}

@Override
public List<ResourceEntity> getAllResourceEntity() {
	List<ResourceEntity> listResource = (List<ResourceEntity>) resRepository.findAll();
	return listResource;
}

@Override
public Optional<TaskTable> findTaskTableById(String id) {
	Optional<TaskTable> tTbl = repository.findById(id);
	return tTbl;
}

@Override
public Optional<assignment> findAssignmentById(String id) {
	Optional<assignment> aTbl = assignmentRep.findById(id);
	return aTbl;
}

@Override
public Optional<ResourceEntity> findResourceEntityById(String id) {
	Optional<ResourceEntity> rTbl = resRepository.findById(id);
	return rTbl;
}

@Override
public List<TaskTable> findByName(String name) throws SQLException {
	List<TaskTable> taskTbl = ownRepository.findByName(name);
	return taskTbl;
}


}
