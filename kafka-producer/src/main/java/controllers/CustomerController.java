package controllers;

import org.jboss.logging.Logger;

import entities.Customer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.CustomerCreateRequest;
import services.CustomerService;

@Path("/ap/v1")
public class CustomerController {
	
	private Logger log;
	private CustomerService customerService;
	
	public CustomerController(Logger log, CustomerService customerService) {
		this.log = log;
		this.customerService = customerService;
	}

	@POST
	@Path("/createCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewCustomer(CustomerCreateRequest request) {
		log.infof("Incoming request create new customer with payload : %s", request.toString());
		try {
			Customer customer = customerService.doSave(request);
			return Response.ok(customer).build();
		}catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
