package config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Created by ssc on 2017/7/11.
 */
@Configuration
@PropertySource(value = {"classpath:/jdbc.properties"})
public class MyBatisConfig {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driver;

    private DataSource dataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriver(driver);
        dataSource.setUrl(url);
        return dataSource;
    }

    private Environment environment() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource());
        return environment;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment());
        configuration.addMappers("mapper");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }
}
