package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.CertificateDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.exception.ResourceForbiddenException;
import ie.mid.identityengine.exception.ResourceNotFoundException;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.Party;
import ie.mid.identityengine.model.User;
import ie.mid.identityengine.repository.PartyRepository;
import ie.mid.identityengine.repository.UserRepository;
import ie.mid.identityengine.service.HyperledgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    HyperledgerService hyperledgerService;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(CertificateController.class);

    @GetMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO getCertificate(@PathVariable String id) {
        logger.debug("'GET' Request to getCertificate() for id: " + id);
        Certificate certificate = hyperledgerService.getCertificate(id);
        if (certificate == null) {
            logger.error(id + " not found on hyperledger returning '404'");
            throw new ResourceNotFoundException(id + "not found on hyperledger");
        }
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setCreatedBy(certificate.getTrustee());
        certificateDTO.setOwnedBy(certificate.getOwner());
        certificateDTO.setId(certificate.getCertId());
        certificateDTO.setStatus(certificate.getStatus());
        certificateDTO.setSubmissionHash(certificate.getSubmissionHash());
        certificateDTO.setCreatedAt(certificate.getDateCreated());
        certificateDTO.setCreatorName(getCreatorName(certificate.getTrustee()).getName());
        certificateDTO.setOwnerName(getOwnerName(certificate.getOwner()).getNickname());
        logger.debug("Certificate found, Returning...");
        return certificateDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO deleteCertificate(@PathVariable String id, Authentication authentication) {
        logger.debug("'DELETE' request to deleteCertificate() for id: " + id);
        Certificate certificate = hyperledgerService.getCertificate(id);
        if (certificate == null) {
            logger.error(id + " not found on hyperledger returning '404'");
            throw new ResourceNotFoundException(id + " not found on hyperledger");
        }
        Party party = getCreatorName(certificate.getTrustee());
        if (!party.getId().equals(authentication.getName())) {
            logger.error("Authentication failure: " + authentication.getName() + " != " + party.getId());
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        }
        hyperledgerService.updateCertificate(id, EntityStatus.DELETED.toString());
        return getCertificate(id);
    }

    private Party getCreatorName(String id) {
        logger.debug("Getting party with id " + id);
        Party party = partyRepository.findByNetworkId(id);
        if (party == null) {
            logger.error("Party not found for id " + id);
            throw new ResourceNotFoundException("Party " + id + " for certificate not found");
        }
        return party;
    }

    private User getOwnerName(String id) {
        logger.debug("Getting user with id " + id);
        User user = userRepository.findByNetworkId(id);
        if (user == null) {
            logger.error("User not found for id " + id);
            throw new ResourceNotFoundException("User " + id + " for certificate not found");
        }
        else return user;
    }
}
