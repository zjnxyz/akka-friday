package com.xtuone.base.util.akka.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xtuone.client.util.AkkaOps;

import akka.actor.ActorSystem;

@Configuration
public class AppConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	public static ActorSystem systemPush =null;
	/**
	   * Actor system singleton for this application.
	   */
	  @Bean(destroyMethod = "shutdown")
	  public ActorSystem actorSystem() {
	
		  if(systemPush == null){
			  systemPush = AkkaOps.createActorSystem("superPush");
		  }
//	    ActorSystem system = ActorSystem.create("super");
	    // initialize the application context in the Akka Spring Extension
	    SpringExtension.SpringExtProvider.get(systemPush).initialize(applicationContext);
	    return systemPush;
	  }
	  
//	  /*
//	     * Factory bean that creates the com.mongodb.Mongo instance
//	     */
//	     public @Bean MongoFactoryBean mongo() {
//	          MongoFactoryBean mongo = new MongoFactoryBean();
//	          mongo.setHost("localhost");
//	          return mongo;
//	     }
}
