package cloudit.africa.GMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.MarketingTemplate;
import cloudit.africa.GMS.Entity.SignatureTemplate;


public interface MarketingRepository extends JpaRepository<MarketingTemplate, Integer>{

	List<MarketingTemplate> findByCompany(Company myCompany);

	SignatureTemplate findByIsActive(boolean b);
	

}
