package cloudit.africa.GMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.OrganizationMembers;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMembers, String> {

	OrganizationMembers findByEmailAdress(String emailToRemove);

}
