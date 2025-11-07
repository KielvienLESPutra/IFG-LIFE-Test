package controllers;

import java.util.Set;

import org.jboss.logging.Logger;

import entities.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.CustomerCreateRequest;
import services.CustomerService;

@Path("/api/v1")
public class CustomerController {
	
	private Logger log;
	private CustomerService customerService;
	private Validator validator;
	
	public CustomerController(Logger log, CustomerService customerService, Validator validator) {
		this.log = log;
		this.customerService = customerService;
		this.validator = validator;
	}

	@POST
	@Path("/createCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewCustomer(CustomerCreateRequest request) {
		log.infof("Incoming request create new customer with payload : %s", request.toString());
		
		try {
			Set<ConstraintViolation<CustomerCreateRequest>> violations = validator.validate(request);
			if (!violations.isEmpty()) {
				log.warnf("Validation catch violations with reason : %s", violations);
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			Customer customer = customerService.doSave(request);
			return Response.ok(customer).build();
		}catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
