package bit.project.server;

import bit.project.server.util.seed.Seeder;
import bit.project.server.util.trigger.TriggerInjector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application {


	public static void main(String[] args) throws Exception {

		ApplicationContext context = SpringApplication.run(Application.class, args);

		Seeder seeder = context.getBean(Seeder.class);
		seeder.seed();

		TriggerInjector triggerInjector = context.getBean(TriggerInjector.class);
		triggerInjector.inject();
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**");
//			}
//		};
//	}

}
