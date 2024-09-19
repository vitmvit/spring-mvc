package by.vitikova.spring.mvc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * В данном классе настраиваются основные компоненты, необходимые для работы с базой данных,
 * включая EntityManager, TransactionManager и загрузку параметров из YAML-файла.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("by.vitikova.spring.mvc")
@PropertySource("classpath:application.yml")
public class DataSourceConfig {

    private static final String ENTITY_FOLDER = "by.vitikova.spring.mvc";
    private static final String APPLICATION_YML = "application.yml";
    private static final String HIBERNATE_TRANSACTION_JTA_PLATFORM = "hibernate.transaction.jta.platform";
    private static final String HIBERNATE_OGM_DATASTORE_PROVIDER = "hibernate.ogm.datastore.provider";
    private static final String HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String CHANGELOG_PATH = "classpath:changelog-master.xml";

    @Value("${" + HIBERNATE_TRANSACTION_JTA_PLATFORM + "}")
    private String transactionJtaPlatform;

    @Value("${" + HIBERNATE_OGM_DATASTORE_PROVIDER + "}")
    private String ogmDatasourceProvider;

    @Value("${" + HIBERNATE_USE_SQL_COMMENTS + "}")
    private String useSqlComments;

    @Value("${" + HIBERNATE_SHOW_SQL + "}")
    private String showSql;

    @Value("${" + HIBERNATE_FORMAT_SQL + "}")
    private String formatSql;

    @Value("${database.driver}")
    private String driver;

    @Value("${database.url}")
    private String url;

    @Value("${database.user}")
    private String user;

    @Value("${database.password}")
    private String password;

    /**
     * Определяет обработчик, который позволяет разрешать параметры из YAML-файла.
     *
     * @return экземпляр {@link BeanFactoryPostProcessor}, используемый для обработки параметров.
     */
    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configure = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(APPLICATION_YML));
        Properties yamlObject = Objects.requireNonNull(yaml.getObject(), "Config file not found");
        configure.setProperties(yamlObject);
        return configure;
    }

    /**
     * Создает экземпляр {@link EntityManager} для взаимодействия с базой данных.
     *
     * @param entityManagerFactory фабрика для создания экземпляров {@link EntityManager}.
     * @return созданный {@link EntityManager}.
     */
    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager;
        }
    }

    /**
     * Создает и настраивает {@link PlatformTransactionManager} для управления транзакциями в приложении.
     *
     * @param entityManagerFactory фабрика для создания экземпляров {@link EntityManager}.
     * @return настроенный {@link PlatformTransactionManager}.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Создает экземпляр {@link LocalContainerEntityManagerFactoryBean} для настройки
     * контекста JPA с использованием Hibernate.
     *
     * @return настроенный {@link LocalContainerEntityManagerFactoryBean},
     * который используется для управления {@link EntityManager}.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(ENTITY_FOLDER);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        Properties properties = getHibernateProperties();
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Создает и настраивает источник данных ({@link DataSource}) с использованием HikariCP.
     *
     * @return настроенный экземпляр {@link DataSource}, который используется для подключения к базе данных.
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

    /**
     * Создает экземпляр {@link SpringLiquibase} для управления миграциями базы данных
     * с использованием Liquibase.
     *
     * @param dataSource источник данных, используемый для подключения к базе данных.
     * @return настроенный экземпляр {@link SpringLiquibase}.
     */
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHANGELOG_PATH);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    /**
     * Получает свойства конфигурации Hibernate, используемые для настройки JPA.
     *
     * @return объект {@link Properties}, содержащий настройки Hibernate.
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_TRANSACTION_JTA_PLATFORM, transactionJtaPlatform);
        properties.setProperty(HIBERNATE_OGM_DATASTORE_PROVIDER, ogmDatasourceProvider);
        properties.setProperty(HIBERNATE_USE_SQL_COMMENTS, useSqlComments);
        properties.setProperty(HIBERNATE_SHOW_SQL, showSql);
        properties.setProperty(HIBERNATE_FORMAT_SQL, formatSql);
        return properties;
    }
}