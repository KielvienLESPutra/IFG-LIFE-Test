package services;

import java.util.Date;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constants.Constant;
import entities.Customer;
import entities.TemporaryMessage;
import jakarta.enterprise.context.ApplicationScoped;
import repository.CustomerRepository;
import repository.TemporaryMessageRepository;

@ApplicationScoped
public class KafkaConsumer {
	
	private Logger log;
	private CustomerRepository customerRepository;
	private TemporaryMessageRepository temporaryMessageRepository;
	private ObjectMapper objectMapper;
	
	public KafkaConsumer(Logger log, CustomerRepository customerRepository,
			TemporaryMessageRepository temporaryMessageRepository, ObjectMapper objectMapper) {
		this.log = Logger.getLogger(KafkaConsumer.class);
		this.customerRepository = customerRepository;
		this.temporaryMessageRepository = temporaryMessageRepository;
		this.objectMapper = objectMapper;
	}

	@Incoming("quotes")
	public void consumeMessage(String quote) {
		try {
			log.infof("Received message from kafka with payload : %s", quote);
			Customer messageObj = objectMapper.readValue(quote, Customer.class);
			Customer customer = customerRepository.findById(Long.valueOf(messageObj.getId()));
			
			customer.setStatus(Constant.STATUS_CUSTOMER.ACTIVE.getValue());
			customer.setUpdatedBy(Constant.SYSTEM);
			customer.setUpdatedDate(new Date());
			
			customerRepository.persist(customer);
			
			String query = "SELECT TemporaryMessage FROM TemporaryMessage WHERE id=?";
		    TemporaryMessage temp = (TemporaryMessage) temporaryMessageRepository.find(query, customer.getId());
			temp.setStatus(Constant.STATUS_SEND.FINISH.getValue());
			temp.setUpdatedBy(Constant.SYSTEM);
			temp.setUpdatedDate(new Date());
			
			temporaryMessageRepository.persist(temp);
		} catch (JsonProcessingException e) { 
			e.printStackTrace();
			
			log.errorf("Error when consume message kafka : %s", e.getCause());
		}
	}
}
