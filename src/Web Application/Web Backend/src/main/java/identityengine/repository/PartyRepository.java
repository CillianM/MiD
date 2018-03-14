package identityengine.repository;

import identityengine.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    Party findById(String id);

    Party findByNetworkId(String id);

}
