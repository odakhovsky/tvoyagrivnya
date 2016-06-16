package com.tvoyagryvnia.conf;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:hibernate-mysql.properties"})
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[]{"com.tvoyagryvnia"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }


    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    @Bean
    public DataSource restDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(env.getProperty("jdbc.url"),
                env.getProperty("jdbc.user"), env.getProperty("jdbc.pass"));
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("current_session_context_class", "thread");
                setProperty("hibernate.max_fetch_depth","1");
                setProperty("hibernate.id.new_generator_mappings", "false");
                //setProperty("hibernate.implicit_naming_strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl");
                //setProperty("hibernate.physical_naming_strategy", "com.tvoyagryvnia.model.LegacyPhysicalNamingStrategy");


            }
        };
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations(env.getProperty("flyway.migration.path"));
        flyway.setDataSource(restDataSource());
        return flyway;
    }
}