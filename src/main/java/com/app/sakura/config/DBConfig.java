package com.app.sakura.config;

import com.sun.xml.internal.ws.client.dispatch.DataSourceDispatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    public DataSource getDatasource(){
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:file:stockapp.db?transaction_mode=DEFERRED&shared_cache=false&password=QSint&hmac_algorithm=0&key=QSint&date_precision=MILLISECONDS&hmac_use=1&kdf_iter=64000&kdf_algorithm=0&open_mode=70&page_size=1024&cipher=sqlcipher&date_class=INTEGER&date_string_format=yyyy-MM-dd HH:mm:ss.SSS&hexkey_mode=SQLCIPHER&plaintext_header_size=0");
        return dataSource;
    }

}
