package config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ssc on 2017/7/11.
 */
@Configuration
@EnableTransactionManagement
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
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment());
//        configuration.addMappers("core.dao");
        //在控制台输出sql
        configuration.setLogImpl(StdOutImpl.class);
        //configuration.setMapUnderscoreToCamelCase(true);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList = new ArrayList<>();
        resourceList.addAll(Arrays.asList(resolver.getResources("classpath:mapper/*.xml")));
        sqlSessionFactoryBean.setMapperLocations(resourceList.toArray(new Resource[resourceList.size()]));
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }

//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws SQLException {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setTypeAliasesPackage("com.nd.sdp.esp.developer.service.mapper");
//
//        // 分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("dialect", "mysql");
//        // 该参数默认为false
//        // 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
//        // 和startPage中的pageNum效果一样-->
//        properties.setProperty("offsetAsPageNum", "true");
//        // 该参数默认为false
//        // 设置为true时，使用RowBounds分页会进行count查询
//        properties.setProperty("rowBoundsWithCount", "true");
//        // 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
//        // （相当于没有执行分页查询，但是返回结果仍然是Page类型）-->
//        properties.setProperty("pageSizeZero", "true");
//        // 3.3.0版本可用 - 分页参数合理化，默认false禁用
//        // 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
//        // 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
//        properties.setProperty("reasonable", "true");
//        // 3.5.0版本可用 - 为了支持startPage(Object params)方法
//        // 增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值
//        // 可以配置pageNum,pageSize,count,pageSizeZero,reasonable,orderBy,不配置映射的用默认值
//        // 不理解该含义的前提下，不要随便复制该配置
//        properties.setProperty("params", "count=countSql;pageNum=pageHelperStart;pageSize=pageHelperRows;");
//        // 支持通过Mapper接口参数来传递分页参数
//        properties.setProperty("supportMethodsArguments", "true");
//        // always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page
//        properties.setProperty("returnPageInfo", "none");
//        pageHelper.setProperties(properties);
//
//
//
//        // 添加插件
//        bean.setPlugins(new Interceptor[] { pageHelper });
//
//        //查看sql 日志
//        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
//
//        // 添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            List<Resource> listR = new ArrayList<Resource>();
//            listR.addAll(Arrays.asList(resolver.getResources("classpath:mapper/*.xml")));
//            listR.addAll(Arrays.asList(resolver.getResources("classpath:mapper/statistic/*.xml")));
//            listR.addAll(Arrays.asList(resolver.getResources("classpath:mapper/samplelib/*.xml")));
//            bean.setMapperLocations((Resource[])listR.toArray(new Resource[listR.size()]));
//
//            //bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            LOG.error("cannot load mapper xml", e);
//            throw new RuntimeException(e);
//        }
//    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
