package cloudit.africa.GMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.UserApp;



public interface UserAppRepositiry extends JpaRepository<UserApp, String>{

	
	UserApp findByEmail(String userId);
	List<UserApp> findByCompany(Company myCompanyUsers);

//	List<UserApp> findByIsSuperAdmin(boolean superadmin);
	UserApp findByIsSuperAdminAndCompany(boolean superadmin,Company company);

	
//	@Modifying
//	@Query("update user_app ear set ear.status = :status where ear.id = :id")

	
	
	

}
