package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

    Key findByUserIdAndStatus(String id, String status);

    Key findById(String id);
}
