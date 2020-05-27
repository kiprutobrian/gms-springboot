package cloudit.africa.GMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cloudit.africa.GMS.Entity.GmsFile;
import cloudit.africa.GMS.Entity.GmsUrls;


public interface GmsFileRepository extends JpaRepository<GmsFile, Integer> {

}
