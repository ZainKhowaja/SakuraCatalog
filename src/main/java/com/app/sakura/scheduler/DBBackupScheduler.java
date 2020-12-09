package com.app.sakura.scheduler;

import com.app.sakura.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DBBackupScheduler {

    @Value("${scheduler.enable-backup:true}")
    private boolean enableDbBackup;
    private static final long DELAY = 3600000L; // 1 HOUR

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(fixedDelay =  DELAY)
    public void dbBackupService(){
        if(!enableDbBackup){
            return;
        }
        FileUtil.takeDailyFileBackup("stockapp.db");
    }
}
