package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findById(String id);

    List<Request> findByRecipient(String id);

    List<Request> findBySender(String id);

}
