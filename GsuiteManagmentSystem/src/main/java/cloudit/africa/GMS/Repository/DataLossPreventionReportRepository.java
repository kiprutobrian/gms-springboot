package cloudit.africa.GMS.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cloudit.africa.GMS.Entity.Company;
import cloudit.africa.GMS.Entity.DataLossPreventionReport;


public interface DataLossPreventionReportRepository extends JpaRepository<DataLossPreventionReport, Integer> {

	@Query(value="SELECT id, active, created_on, key_value, report_address, updated_by, updated_on, company, data_loss_prevention_report_type, daily, last_report, monthly, weekly\n" + 
			"FROM data_loss_prevention_report where data_loss_prevention_report_type=? and company=?",nativeQuery=true)
	Optional<DataLossPreventionReport> findByDataLossPreventionReportTypeAndCompany(int i, String company);

}
