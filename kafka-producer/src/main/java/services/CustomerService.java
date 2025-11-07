package services;

import java.util.Date;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import constants.Constant;
import entities.Customer;
import entities.TemporaryMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import models.CustomerCreateRequest;
import repository.CustomerRepository;
import repository.TemporaryMessageRepository;

@ApplicationScoped
public class CustomerService {

	private Logger log;
	private CustomerRepository customerRepository;
	private TemporaryMessageRepository temporaryMessageRepository;
	private KafkaProducer kafkaProducer; 
	private ObjectMapper objectMapper;

	public CustomerService(Logger log, CustomerRepository customerRepository,
			TemporaryMessageRepository temporaryMessageRepository, KafkaProducer kafkaProducer,
			ObjectMapper objectMapper) {
		this.log = log;
		this.customerRepository = customerRepository;
		this.temporaryMessageRepository = temporaryMessageRepository;
		this.kafkaProducer = kafkaProducer;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public Customer doSave(CustomerCreateRequest request){
		log.infof("Starting process save customer with request : %s", request.toString());
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(null);
		customer.setAddress(null);
		customer.setDob(null);
		customer.setGender(null);
		customer.setStatus(Constant.STATUS_CUSTOMER.SUBMISSION.getValue());
		customer.setCreatedBy(Constant.SYSTEM);
		customer.setCreatedDate(new Date());

		customerRepository.persist(customer);
		
		String messageToKafka = objectMapper.writeValueAsString(customer);
		TemporaryMessage message = new TemporaryMessage();
		message.setPayload(messageToKafka);
		message.setStatus(Constant.STATUS_SEND.ONPROGRESS.getValue());
		message.setCreatedBy(Constant.SYSTEM);
		message.setCreatedDate(new Date());
		temporaryMessageRepository.persist(message);
		
		boolean isSuccess = kafkaProducer.sendMessage(messageToKafka);
		
		if(isSuccess) {
			message.setStatus(Constant.STATUS_SEND.SUCCESSFUL_SEND.getValue());
		}else {
			message.setStatus(Constant.STATUS_SEND.FAILED.getValue());
		}

		message.setUpdatedBy(Constant.SYSTEM);
		message.setUpdatedDate(new Date());
		temporaryMessageRepository.persist(message);
		
		log.infof("Finish process save customer with request : %s", request.toString());
		return customer;
	}
}
