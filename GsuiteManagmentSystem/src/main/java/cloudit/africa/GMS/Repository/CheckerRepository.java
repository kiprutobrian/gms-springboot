package cloudit.africa.GMS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cloudit.africa.GMS.Entity.Checker;
import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.MarkerCheckers;
import cloudit.africa.GMS.Entity.UserApp;
import javassist.bytecode.stackmap.BasicBlock.Catch;

public interface CheckerRepository extends JpaRepository<Checker, String> {

	
	
	@Query(value = "SELECT id, active, checker, company, maker_checkers FROM public.checker where maker_checkers=? and company=? ", nativeQuery = true)
	Optional<Checker> findByMakerCheckersCompany(String makercheckerId, String companyId);

	Optional<List<Checker>> findByCompany(Company company);

	Optional<List<Checker>> findByChecker(UserApp userApp);

//	Optional<List<Checker>> findBymakerCheckerscompany(MarkerCheckers markerCheckers, Company company);

}
