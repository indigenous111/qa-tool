package in.indigenous.qa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.indigenous.common.jpa.repository.qa.TicketTypeRepository;
import in.indigenous.qa.model.TicketType;

@Service
@Transactional(transactionManager="qaTransactionManager", readOnly=false)
public class TicketTypeService {

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	public List<TicketType> listTicketTypes() {
		ObjectMapper mapper = new ObjectMapper();
		return ticketTypeRepository.findAll().stream().map(type -> {
			return mapper.convertValue(type, TicketType.class);
		}).collect(Collectors.toList());
	}
}
