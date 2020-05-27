package cloudit.africa.GMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<cloudit.africa.GMS.Entity.Package, Integer>{

	cloudit.africa.GMS.Entity.Package findByName(String packageName);

}
