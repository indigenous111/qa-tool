package in.indigenous.qa.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import in.indigenous.common.jpa.repository.qa.TicketRepository;
import in.indigenous.common.jpa.repository.qa.TicketStatusRepository;
import in.indigenous.common.jpa.repository.qa.TicketTypeRepository;
import in.indigenous.qa.model.Project;
import in.indigenous.qa.model.Ticket;

/**
 * The artifact service.
 * 
 * @author sarkh
 *
 */
@Service
@Transactional(transactionManager = "qaTransactionManager", readOnly = false)
public class TicketService {

	/** The artifact repository. */
	@Autowired
	private TicketRepository ticketRepository;

	/** The artifact type repository. */
	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	/** The artifact status repository. */
	@Autowired
	private TicketStatusRepository ticketStatusRepository;

	/**
	 * 
	 * @param ticket
	 */
	public void create(Ticket ticket) {
		in.indigenous.common.jpa.entity.qa.Ticket entity = new in.indigenous.common.jpa.entity.qa.Ticket();
		entity.setProjectId(ticket.getProject().getId());
		entity.setType(ticketTypeRepository.getOne(ticket.getType().getId()));
		entity.setStatus(ticketStatusRepository.findByStatus("NEW"));
		entity.setSummary(ticket.getSummary());
		entity.setDescription(ticket.getDescription());
		entity.setCreatedOn(LocalDateTime.now());
		ticketRepository.save(entity);
	}

	/**
	 * 
	 * @return
	 */
	public List<Ticket> list() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return ticketRepository.findAll().stream().map(ticket -> {
			Ticket result = mapper.convertValue(ticket, Ticket.class);
			Project prj = new Project();
			prj.setId(ticket.getProjectId());
			result.setProject(prj);
			return result;
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
		Optional<in.indigenous.common.jpa.entity.qa.Ticket> entity = ticketRepository.findById(id);
		if (entity.isPresent()) {
			in.indigenous.common.jpa.entity.qa.Ticket target = entity.get();
			Ticket ticket = mapper.convertValue(target, Ticket.class);
			Project prj = new Project();
			prj.setId(target.getProjectId());
			ticket.setProject(prj);
			return ticket;
		}
		throw new EntityNotFoundException("No artifact found.");
	}

	/**
	 * 
	 * @param ticket
	 * @return
	 */
	public Ticket update(Ticket ticket) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		Optional<in.indigenous.common.jpa.entity.qa.Ticket> entity = ticketRepository.findById(ticket.getId());
		if (entity.isPresent()) {
			in.indigenous.common.jpa.entity.qa.Ticket target = entity.get();
			target.setProjectId(ticket.getProject().getId());
			target.setDescription(ticket.getDescription());
			target.setStatus(ticketStatusRepository.findById((ticket.getStatus().getId())).get());
			target.setSummary(ticket.getSummary());
			target.setType(ticketTypeRepository.findByType(ticket.getType().getType()));
			target.setModifiedOn(LocalDateTime.now());
			ticketRepository.save(target);
			Ticket updated = mapper.convertValue(target, Ticket.class);
			Project prj = new Project();
			prj.setId(target.getProjectId());
			updated .setProject(prj);
			return updated;
		}
		throw new EntityNotFoundException("No artifact found.");
	}
}
