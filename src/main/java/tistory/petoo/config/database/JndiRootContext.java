package tistory.petoo.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"tistory.petoo.api.**"}, sqlSessionFactoryRef = "jndiSqlSessionFactory")
@EnableTransactionManagement
public class JndiRootContext {

    @Value("${jndi.name}")
    private String jndiName;

    @Bean
    public DataSource jndiDataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        return jndiDataSourceLookup.getDataSource("java:comp/env/" + jndiName);
    }

    @Bean
    public SqlSessionFactoryBean jndiSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(jndiDataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));

        Properties properties = new Properties();
        // properties.setProperty("mapUnderscoreToCamelCase", "true");
        sessionFactory.setConfigurationProperties(properties);

        return sessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        // sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);
        sqlSessionFactory.getConfiguration().setReturnInstanceForEmptyRow(true);
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(null);

        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager jndiTransactionManager() {
        DataSourceTransactionManager jndiTransactionManager = new DataSourceTransactionManager();
        jndiTransactionManager.setDataSource(jndiDataSource());

        return jndiTransactionManager;
    }

}