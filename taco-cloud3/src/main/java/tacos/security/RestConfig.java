package tacos.security;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.client.RestTemplate;

import tacos.Taco;

@Configuration
public class RestConfig {

	@Bean
	  public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>
	    tacoProcessor(EntityLinks links) { // EntityLinks : 경로를 만들어주는 객체

	    return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {

			@Override
			public PagedModel<EntityModel<Taco>> process(PagedModel<EntityModel<Taco>> model) {
				model.add(
			            links.linkFor(Taco.class)
			                 .slash("recent")
			                 .withRel("recents"));
				return model;
			}
	    };
	  }
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Traverson traverson() {
		return new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);
	}
}
