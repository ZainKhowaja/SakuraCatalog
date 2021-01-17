package com.app.sakura.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    public static final String IMAGE_FOLDER_PATH = "/image/";

    public static void takeDailyFileBackup(String fileLocation) {
        try {
            String newFileName = DateUtil.getCurrentDateForDbBackup() + ".db";
            String backupLocation = "backup\\";
            Path source = Paths.get(fileLocation);
            Path target = Paths.get(backupLocation + newFileName);

            if (!Paths.get(backupLocation).toFile().exists()) {
                Files.createDirectory(Paths.get(backupLocation));
                Files.setAttribute(Paths.get(backupLocation), "dos:hidden", true);
            }

            //create backup if no exists
            if(!target.toFile().exists()) {
                Path createdFile = Files.copy(source, target);
                Files.setAttribute(createdFile, "dos:hidden", true);
                LOGGER.info("Backup created at: " + newFileName);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String copyFile(String source) {
        File sourceFile = new File(source);
        File destFile = new File(System.getProperty("user.dir") + IMAGE_FOLDER_PATH + sourceFile.getName());
        if(!destFile.exists()){
            new File(System.getProperty("user.dir") + IMAGE_FOLDER_PATH).mkdirs();
        }
        try {
            FileCopyUtils.copy(sourceFile, destFile);
            System.out.println(destFile.getCanonicalPath().replaceAll("\\\\","/"));
            return destFile.getCanonicalPath().replaceAll("\\\\","/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean doesDbExists() {
        return new File("stockapp.db").exists();
    }

    public static void main(String[] args) {
        FileUtil.copyFile("E:\\Program Files (x86)\\Sakura Filter\\img\\A-2524.jpg");
    }
}
