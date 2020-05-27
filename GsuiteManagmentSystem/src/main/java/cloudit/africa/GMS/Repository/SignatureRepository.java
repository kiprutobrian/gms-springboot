package cloudit.africa.GMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.SignatureTemplate;


public interface SignatureRepository extends JpaRepository<SignatureTemplate, Integer>{

	List<SignatureTemplate> findByCompany(Company myCompany);

	SignatureTemplate findByIsActive(boolean b);
	

}
