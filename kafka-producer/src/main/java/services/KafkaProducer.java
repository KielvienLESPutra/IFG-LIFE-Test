package services;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaProducer {

	@Inject
	private Logger log;
	
	@Inject
    @Channel("quotes")
    private Emitter<String> emitter;
	
	public boolean sendMessage(String message) {
		try {
			log.infof("Send message with payload : %s", message);
			emitter.send(message);
			return true;
		}catch (Exception e) {
			log.errorf("Error sending message to kafka with payload : %s", message);
		}
		
		return false;
	}
}
