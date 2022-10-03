package tistory.petoo.config.database;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JndiResource {

    @Value("${jndi.name}")
    private String jndiName;

    @Value("${driver.class.name}")
    private String driverClassName;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.user.name}")
    private String dbUserName;

    @Value("${db.user.password}")
    private String dbUserPassword;

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                context.getNamingResources().addResource(getResource(
                        jndiName,
                        driverClassName,
                        dbUrl,
                        dbUserName,
                        dbUserPassword)
                );

                context.setXmlBlockExternal(false);
            }
        };
    }

    public ContextResource getResource(String name, String driverClassName, String url, String username, String password) {
        ContextResource resource = new ContextResource();
        resource.setName(name);
        resource.setType("javax.sql.DataSource");
        resource.setAuth("Container");
        resource.setProperty("factory", "org.apache.commons.dbcp2.BasicDataSourceFactory");

        resource.setProperty("driverClassName", driverClassName);
        resource.setProperty("url", url);
        resource.setProperty("username", username);
        resource.setProperty("password", password);

        return resource;
    }

}