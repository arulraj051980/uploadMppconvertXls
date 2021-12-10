package com.sierra.files.upload.utils;


import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.aspose.tasks.Project;
import com.aspose.tasks.SaveFileFormat;
import org.apache.commons.io.FilenameUtils;

@Component
public class Utils {

    public static String getDataDir(Class c) {
        File dir = new File(System.getProperty("user.dir"));
        dir = new File(dir, "src");
        dir = new File(dir, "main");
        dir = new File(dir, "resources");

        for (String s : c.getName().split("\\.")) {
            dir = new File(dir, s);
            if (dir.isDirectory() == false)
                dir.mkdir();
        }

        System.out.println("Using data directory: " + dir.toString());
        return dir.toString() + File.separator;
    }
    
  /*  public File getTheNewestFile(String filePath, String ext) {
        File theNewestFile = null;
        File dir = new File(filePath);
        FileFilter fileFilter = new WildcardFileFilter("*." + ext);
        File[] files = dir.listFiles(fileFilter);

        if (files.length > 0) {
            /** The newest file comes first **
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            theNewestFile = files[0];
        }

        return theNewestFile;
    }*/
    
    public File getLatestFilefromDir(String dirPath) {
    	
        File dir = new File(dirPath);
        //System.out.println("Stage One path is "+dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
           if (lastModifiedFile.lastModified() < files[i].lastModified()) {
               lastModifiedFile = files[i];
           }
        }
        return lastModifiedFile;
    }
    
    public String savemsstoxls(String path, File file) throws IOException {
    	//System.out.println("Path is 1="+path+file.getName());
    	Project project = new Project(path + file.getName());
    	String fileNameWithoutExt = FilenameUtils.getBaseName(file.getName());
    	project.save(path + fileNameWithoutExt.concat(".xlsx"), SaveFileFormat.XLSX);
    	
    	return "Success";
    }
}
