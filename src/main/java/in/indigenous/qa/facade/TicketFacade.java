package in.indigenous.qa.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import in.indigenous.qa.model.Ticket;
import in.indigenous.qa.model.TicketStatus;
import in.indigenous.qa.model.TicketType;
import in.indigenous.qa.service.ProjectService;
import in.indigenous.qa.service.TicketService;
import in.indigenous.qa.service.TicketStatusService;
import in.indigenous.qa.service.TicketTypeService;

/**
 * 
 * @author sarkh
 *
 */
@Component
public class TicketFacade {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TicketTypeService ticketTypeService;
	
	@Autowired
	private TicketStatusService ticketStatusService;
	
	/**
	 * 
	 * @param artifact
	 */
	public void create(Ticket artifact) {
		ticketService.create(artifact);
	}

	/**
	 * 
	 * @return
	 */
	public List<Ticket> list() {
		List<Ticket> tickets = ticketService.list();
		return tickets.stream().map(ticket -> {
			ticket.setProject(projectService.getProject(ticket.getProject().getId()));
			return ticket;
		}).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Ticket view(Long id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		Ticket ticket = ticketService.view(id);
		ticket.setProject(projectService.getProject(ticket.getProject().getId()));
		return ticket;
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	public Ticket update(Ticket target) {
		Ticket ticket = ticketService.update(target);
		ticket.setProject(projectService.getProject(ticket.getProject().getId()));
		return ticket;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TicketType> listTicketTypes() {
		return ticketTypeService.listTicketTypes();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TicketStatus> listTicketStatus() {
		return ticketStatusService.listTicketStatus();
	}

}
