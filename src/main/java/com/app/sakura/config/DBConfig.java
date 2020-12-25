package com.app.sakura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDatasource(){
        SQLiteDataSource dataSource = new SQLiteDataSource();
        String p = environment.getProperty("test") + environment.getProperty("connect");
        String url = String.format("jdbc:sqlite:file:stockapp.db?transaction_mode=DEFERRED&shared_cache=false&password=%s&hmac_algorithm=0&key=%s&date_precision=MILLISECONDS&hmac_use=1&kdf_iter=64000&kdf_algorithm=0&open_mode=70&page_size=1024&cipher=sqlcipher&date_class=INTEGER&date_string_format=yyyy-MM-dd HH:mm:ss.SSS&hexkey_mode=SQLCIPHER&plaintext_header_size=0",p,p);
        dataSource.setUrl(url);
        return dataSource;
    }
}
