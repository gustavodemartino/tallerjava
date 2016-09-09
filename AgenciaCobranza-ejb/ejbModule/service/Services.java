package service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class Services {
	Thread t;
	
	@PostConstruct
	void atStartup(){
		t = new Thread(new TerminalServer());
		t.start();
	}

	@PreDestroy
	void atClose(){
		t.interrupt();
	}
}
