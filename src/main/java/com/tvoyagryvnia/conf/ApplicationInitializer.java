package com.tvoyagryvnia.conf;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        rootContext.register(WebAppConfig.class);
        rootContext.register(HibernateConfig.class);
        rootContext.register(SecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        rootContext.setServletContext(servletContext);

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));

        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);

        servletContext.addFilter("hibernateFilter", new OpenSessionInViewFilter())
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}
