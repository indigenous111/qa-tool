package in.indigenous.qa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.indigenous.qa.facade.ProjectFacade;
import in.indigenous.qa.facade.TicketFacade;
import in.indigenous.qa.model.Project;
import in.indigenous.qa.model.Ticket;
import in.indigenous.qa.model.TicketStatus;
import in.indigenous.qa.model.TicketType;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	
	@Autowired
	private ProjectFacade projectFacade;
	
	@Autowired
	private TicketFacade ticketFacade;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreate(Model model) {
		List<TicketType> ticketTypes = ticketFacade.listTicketTypes();
		model.addAttribute("ticketTypes", ticketTypes);
		List<Project> projects = projectFacade.listProjects();
		model.addAttribute("projects", projects);
		Ticket ticket = new Ticket();
		model.addAttribute("ticket", ticket);
		return "create-ticket";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(Model model, Ticket ticket) {
		ticketFacade.create(ticket);
		List<Ticket> tickets = ticketFacade.list();
		model.addAttribute("tickets", tickets);
		model.addAttribute("msg", "Artifact created successfully.");
		return "list-ticket";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Ticket> tickets = ticketFacade.list();
		model.addAttribute("tickets", tickets);
		return "list-ticket";
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String list(@PathVariable Long id, Model model) {
		model.addAttribute("ticket", ticketFacade.view(id));
		return "view-ticket";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String viewEdit(@PathVariable Long id, Model model) {
		model.addAttribute("ticket", ticketFacade.view(id));
		List<TicketStatus> ticketStatuses = ticketFacade.listTicketStatus();
		model.addAttribute("ticketStatuses", ticketStatuses);
		return "edit-ticket";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String update(Ticket ticket, Model model) {
		model.addAttribute("ticket", ticketFacade.update(ticket));
		return "view-ticket";
	}
}
