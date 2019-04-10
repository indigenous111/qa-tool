package in.indigenous.qa.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import in.indigenous.common.jpa.repository.pm.ProjectRepository;
import in.indigenous.qa.model.Project;

/**
 * The project service.
 * 
 * @author sarkh
 *
 */
@Service
@Transactional(transactionManager = "pmTransactionManager", readOnly = true)
public class ProjectService {

	/** The project repository. */
	@Autowired
	private ProjectRepository projectRepository;

	/**
	 * List projects.
	 * 
	 * @return
	 */
	public List<Project> listProjects() {
		List<in.indigenous.common.jpa.entity.pm.Project> entities = projectRepository.findAll();
		entities.sort(Comparator.comparing(in.indigenous.common.jpa.entity.pm.Project::getCreatedOn));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Project> projects = new ArrayList<>();
		entities.forEach(entity -> {
			Project project = mapper.convertValue(entity, Project.class);
			projects.add(project);
		});
		return projects;
	}

	/**
	 * Get Project.
	 * @param id
	 * @return
	 */
	public Project getProject(Long id) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.convertValue(projectRepository.getOne(id), Project.class);
	}
}
