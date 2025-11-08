package services;

import java.util.Date;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import constants.Constant;
import entities.Customer;
import entities.TemporaryMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import models.MessageQuote;
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

	@Incoming("quotes-in")
	public void consumeMessage(String msg) {
		try {
			log.infof("Received message from kafka with payload : %s", msg);
			MessageQuote messageObj = objectMapper.readValue(msg, MessageQuote.class);
			doUpdateStatusCustomer(messageObj);
		} catch (Exception e) {
			e.printStackTrace();
			log.errorf("Error trigger and will be negative acknowledge message with reason : %s message: %s",
					e.getCause(), e.getMessage());
		}
	}

	@Transactional
	public void doUpdateStatusCustomer(MessageQuote messageObj) throws Exception {
		Customer customer = customerRepository.findById(Long.valueOf(messageObj.getCustomerId()));

		if (customer == null) {
			log.warnf("Customer with id : %s cannot be found on databases", messageObj.getCustomerId());
			throw new Exception("Customer with cannot be found");
		}

		customer.setStatus(Constant.STATUS_CUSTOMER.ACTIVE.getValue());
		customer.setUpdatedBy(Constant.SYSTEM);
		customer.setUpdatedDate(new Date());

		customerRepository.persist(customer);

		TemporaryMessage temp = temporaryMessageRepository.findById(Long.valueOf(messageObj.getTemporaryId()));
		
		if (temp == null) {
			log.warnf("Temporary message with reference id : %s cannot be found on databases", messageObj.getTemporaryId());
			throw new Exception("Temporary message with cannot be found");
		}
		temp.setStatus(Constant.STATUS_SEND.FINISH.getValue());
		temp.setUpdatedBy(Constant.SYSTEM);
		temp.setUpdatedDate(new Date());

		temporaryMessageRepository.persist(temp);
	}
}
