package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findById(String id);

}
