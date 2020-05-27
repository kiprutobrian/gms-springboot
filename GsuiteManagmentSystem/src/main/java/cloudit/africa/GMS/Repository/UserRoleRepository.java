package cloudit.africa.GMS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cloudit.africa.GMS.Entity.Role;
import cloudit.africa.GMS.Entity.UserApp;
import cloudit.africa.GMS.Entity.UserRole;


public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

	Optional<List<UserRole>> findByUserApp(UserApp userApp);

	@Query(value="select ur.id,ur.role,ur.user_app from user_role ur,user_app ua where ur.user_app =ua.id and ur.role=? and ua.company=?",nativeQuery = true)
	Optional<List<UserRole>> findByRole(Integer role,String company);
	
	
	Optional<UserRole> findByUserAppAndRole(UserApp userApp,Role role);
	

}
