package com.app.sakura.util;

import com.app.sakura.config.AppConfig;
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
        LOGGER.info("Image folder: {}", AppConfig.IMAGE_FOLDER_PATH);
        File sourceFile = new File(source);
        File destFile = new File(AppConfig.IMAGE_FOLDER_PATH + sourceFile.getName());
        if(!destFile.exists()){
            new File(AppConfig.IMAGE_FOLDER_PATH).mkdirs();
        }
        try {
            FileCopyUtils.copy(sourceFile, destFile);
            LOGGER.info(destFile.getCanonicalPath().replaceAll("\\\\","/"));
            return destFile.getCanonicalPath().replaceAll("\\\\","/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean doesDbExists() {
        return new File("stockapp.db").exists();
    }

}
