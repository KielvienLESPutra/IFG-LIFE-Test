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
import models.MessageQuote;
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
	public Customer doSave(CustomerCreateRequest request) throws Exception {
		log.infof("Starting process save customer with request : %s", request.toString());

		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setAddress(request.getAddress());
		customer.setDob(request.getDob());
		customer.setGender(request.getGender());
		customer.setStatus(Constant.STATUS_CUSTOMER.SUBMISSION.getValue());
		customer.setCreatedBy(Constant.SYSTEM);
		customer.setCreatedDate(new Date());

		customerRepository.persist(customer);

		String messageToKafka = objectMapper.writeValueAsString(customer);
		TemporaryMessage temp = new TemporaryMessage();
		temp.setRefId(customer.getId());
		temp.setPayload(messageToKafka);
		temp.setStatus(Constant.STATUS_SEND.ONPROGRESS.getValue());
		temp.setCreatedBy(Constant.SYSTEM);
		temp.setCreatedDate(new Date());
		temporaryMessageRepository.persist(temp);

		MessageQuote messageQuote = new MessageQuote(customer.getId(), temp.getId());
		String message = objectMapper.writeValueAsString(messageQuote);
		boolean isSuccess = kafkaProducer.sendMessage(message);

		if (isSuccess) {
			temp.setStatus(Constant.STATUS_SEND.SUCCESSFUL_SEND.getValue());
		} else {
			temp.setStatus(Constant.STATUS_SEND.FAILED.getValue());
		}

		temp.setUpdatedBy(Constant.SYSTEM);
		temp.setUpdatedDate(new Date());
		temporaryMessageRepository.persist(temp);

		log.infof("Finish process save customer with request : %s", request.toString());
		return customer;
	}
}
