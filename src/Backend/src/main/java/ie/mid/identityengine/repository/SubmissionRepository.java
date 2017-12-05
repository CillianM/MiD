package ie.mid.identityengine.repository;

import ie.mid.identityengine.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    Submission findById(String id);

    List<Submission> findByPartyId(String partyId);

    List<Submission> findByUserId(String userId);
}
