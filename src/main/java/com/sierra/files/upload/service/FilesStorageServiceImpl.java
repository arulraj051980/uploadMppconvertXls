package com.sierra.files.upload.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sierra.files.upload.utils.Utils;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

 @Autowired
 public Utils utils;
	
  //private final String root = "E:\\MppFiles\\";
  private final String uploadExcel = "E://uploadFiles//";

//  @Override
//  public void init() {
//    try {
//      Files.createDirectory(root);      
//    } catch (IOException e) {
//      throw new RuntimeException("Could not initialize folder for upload!");
//    }
//  }

  @Override
  public void save(MultipartFile file) {
    try {
    	File file1 = new File(uploadExcel);
		String fileFolder=uploadExcel+"/";
		if(!file1.exists()) {			
			file1.mkdirs();			
		}
					
		FileCopyUtils.copy(file.getBytes(), new File(fileFolder + file.getOriginalFilename()));
    	
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      File file = new File(uploadExcel);
      
      String[] flist = file.list();
      int flag = 0;
      if(flist == null) {
    	  System.out.println("Empty Directory");
      } else {
    	  for(int i=0; i<flist.length; i++) {
    		  String fileName = flist[i];
    		  if(fileName.equalsIgnoreCase(fileName)) {
    			  System.out.println(fileName + "Found");
    			  flag = 1;
    		  }
    	  }
      }
      if(flag==0) {
    	  System.out.println("File is not found");
      }
      
     
      Resource resource = new UrlResource(file.getAbsolutePath());

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
    //FileSystemUtils.deleteRecursively(uploadExcel.);
  }


@Override
public String convert() {
	
	File file = utils.getLatestFilefromDir(uploadExcel);
	
	try {
		utils.savemsstoxls(uploadExcel, file);
	} catch (IOException e) {		
		e.printStackTrace();
	}
	
	return "Success";
}

@Override
public void init() {
	// TODO Auto-generated method stub
	
}

}
