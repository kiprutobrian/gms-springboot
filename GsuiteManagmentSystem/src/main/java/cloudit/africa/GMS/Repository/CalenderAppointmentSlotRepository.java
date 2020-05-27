package cloudit.africa.GMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.CalenderAppointmentSlot;
import cloudit.africa.GMS.Entity.Company;


public interface CalenderAppointmentSlotRepository extends JpaRepository<CalenderAppointmentSlot, String> {

	List<CalenderAppointmentSlot> findByCompany(Company company);

}
