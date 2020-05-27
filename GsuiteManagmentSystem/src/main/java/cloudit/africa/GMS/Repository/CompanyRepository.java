package cloudit.africa.GMS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String>{

	
	Optional<Company> findByDomain(String substring);

}
