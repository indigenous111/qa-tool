package in.indigenous.qa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.indigenous.common.jpa.repository.qa.TicketStatusRepository;
import in.indigenous.qa.model.TicketStatus;

@Service
@Transactional(transactionManager="qaTransactionManager", readOnly=false)
public class TicketStatusService {

	@Autowired
	private TicketStatusRepository ticketStatusRepository;
	
	/**
	 * 
	 * @return
	 */
	public List<TicketStatus> listTicketStatus() {
		ObjectMapper mapper = new ObjectMapper();
		return ticketStatusRepository.findAll().stream().map(status -> {
			return mapper.convertValue(status, TicketStatus.class);
		}).collect(Collectors.toList());
	}
}
