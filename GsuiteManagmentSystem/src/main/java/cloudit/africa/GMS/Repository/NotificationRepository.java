package cloudit.africa.GMS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cloudit.africa.GMS.Entity.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Long>{


	@Query(value="SELECT id, created_by, created_date, from_user, message, message_html_body, title, update_by, update_date, user_app FROM public.notification where user_app=?",nativeQuery=true)
	Optional<List<Notification>> findByUserApp(String userId);

	
	@Query(value="SELECT id, created_by, created_date, from_user, message, message_html_body, title, update_by, update_date, user_app FROM public.notification where user_app=?",nativeQuery=true)
	List<Notification> findByUserApps(String userId);

}
