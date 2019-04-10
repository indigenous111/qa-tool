package in.indigenous.qa.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.indigenous.qa.model.Project;
import in.indigenous.qa.service.ProjectService;

/**
 * 
 * @author sarkh
 *
 */
@Component
public class ProjectFacade {
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 
	 * @return
	 */
	public List<Project> listProjects() {
		return projectService.listProjects();
	}

}
