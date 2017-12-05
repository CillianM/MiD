package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.IdentityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentityTypeRepository extends JpaRepository<IdentityType, Long> {

    @Query("SELECT i FROM IdentityType i WHERE i.id=?1 AND i.versionNumber = (SELECT max(i2.versionNumber) FROM IdentityType i2 WHERE i2.id = i.id)")
    IdentityType findById(String id);

    @Query("SELECT i from IdentityType i WHERE i.versionNumber = (SELECT max(i.versionNumber) FROM IdentityType i2 WHERE i2.id = i.id)")
    List<IdentityType> findLatest();

    @Query("SELECT i from IdentityType i WHERE i.partyId=?1 AND i.versionNumber = (SELECT max(i.versionNumber) FROM IdentityType i2 WHERE i2.id = i.id)")
    List<IdentityType> findLatestByPartyId(String partyId);
}
