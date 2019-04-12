package in.indigenous.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import in.indigenous.common.jpa.config.PMConfig;
import in.indigenous.common.jpa.config.QAConfig;

@Configuration
@EnableAutoConfiguration()
@EntityScan(basePackages = { "in.indigenous.common.jpa.entity" })
@Import(value = { in.indigenous.common.jpa.Application.class, PMConfig.class, QAConfig.class })
@ComponentScan(basePackages = { "in.indigenous.common.jpa", "in.indigenous.qa" })
@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
	}

	@Bean
	public Map<String, List<String>> getCompMap() {
		Map<String, List<String>> compMap = new HashMap<>();
		List<String> pages = new ArrayList<>();
		pages.add("home");
		compMap.put("pages", pages);
		return compMap;
	}

	@Bean
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSizePerFile(1000000);
		return resolver;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
			@Qualifier("dataSource") DataSource dataSource) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan("in.indigenous.common.jpa.entity");
		factoryBean.setJpaProperties(additionalProperties());
		return factoryBean;
	}

	@Bean
	@Primary
	public DataSource dataSource() throws Exception {
		Properties props = new Properties();
		props.load(new InputStreamReader(new FileInputStream(new File("./src/main/resources/liquibase.properties"))));
		return BasicDataSourceFactory.createDataSource(props);
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB53Dialect");
		return properties;
	}

}
