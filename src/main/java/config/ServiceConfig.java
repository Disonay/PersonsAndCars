package config;


import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = {"controller", "data.dao.hibernate"})
@EnableWebMvc
@Configuration
public class ServiceConfig {
    final String USERNAME_PROPERTY = "USERNAME";
    final String PASSWORD_PROPERTY = "PASSWORD";
    final String URL_PROPERTY = "URL";

    @Bean
    @Scope("singleton")
    SessionFactory sessionFactory() {
        try {
            org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration().configure();

            cfg.setProperty("hibernate.connection.url", System.getProperty(URL_PROPERTY));
            cfg.setProperty("hibernate.connection.username", System.getProperty(USERNAME_PROPERTY));
            cfg.setProperty("hibernate.connection.password", System.getProperty(PASSWORD_PROPERTY));

            return cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
