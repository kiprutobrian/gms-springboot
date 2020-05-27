package cloudit.africa.GMS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.WorkFlow;

public interface WorkFlowRepository extends JpaRepository<WorkFlow, Integer>{

	
	
	Optional<List<WorkFlow>> findByCompany(Company company);

	List<WorkFlow> findBycompany(String company);


}
