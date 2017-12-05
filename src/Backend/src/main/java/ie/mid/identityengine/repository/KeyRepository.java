package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {

    Key findByUserIdAndStatus(String id, String status);

    Key findById(String id);
}
