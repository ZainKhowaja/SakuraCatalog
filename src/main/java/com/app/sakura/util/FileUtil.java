package com.app.sakura.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            if(!Paths.get(backupLocation).toFile().exists()){
                Files.createDirectory(Paths.get(backupLocation));
                Files.setAttribute(Paths.get(backupLocation), "dos:hidden", true);
            }

            //if already file is not backuped
            if(!target.toFile().exists()) {
                Path createdFile = Files.copy(source, target);
                Files.setAttribute(createdFile, "dos:hidden", true);
                LOGGER.info("Backup created at: "+newFileName);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
