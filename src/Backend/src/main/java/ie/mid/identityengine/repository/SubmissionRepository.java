package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Submission findById(String id);

    @Query("SELECT i FROM Submission i WHERE i.partyId=?1 AND i.status = 'PENDING'")
    List<Submission> findByPartyId(String partyId);

    @Query("SELECT i FROM Submission i WHERE i.userId=?1")
    List<Submission> findByUserId(String userId);

    Submission findByCertificateId(String certificateId);
}
