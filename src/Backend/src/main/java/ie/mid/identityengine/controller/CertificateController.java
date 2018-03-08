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

    @GetMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO getCertificate(@PathVariable String id) {
        Certificate certificate = hyperledgerService.getCertificate(id);
        if (certificate == null) throw new ResourceNotFoundException();
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setCreatedBy(certificate.getTrustee());
        certificateDTO.setOwnedBy(certificate.getOwner());
        certificateDTO.setId(certificate.getCertId());
        certificateDTO.setStatus(certificate.getStatus());
        certificateDTO.setCreatedAt(certificate.getDateCreated());
        certificateDTO.setCreatorName(getCreatorName(certificate.getTrustee()).getName());
        certificateDTO.setOwnerName(getOwnerName(certificate.getOwner()).getNickname());
        return certificateDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO deleteCertificate(@PathVariable String id, Authentication authentication) {
        Certificate certificate = hyperledgerService.getCertificate(id);
        if (certificate == null) throw new ResourceNotFoundException();
        Party party = getCreatorName(certificate.getTrustee());
        if (!party.getId().equals(authentication.getName()))
            throw new ResourceForbiddenException(authentication.getName() + " is forbidden from resource " + id);
        hyperledgerService.updateCertificate(id, EntityStatus.DELETED.toString());
        return getCertificate(id);
    }

    private Party getCreatorName(String id) {
        Party party = partyRepository.findByNetworkId(id);
        if (party == null) throw new ResourceNotFoundException("Party " + id + " for certificate not found");
        return party;
    }

    private User getOwnerName(String id) {
        User user = userRepository.findByNetworkId(id);
        if (user == null) throw new ResourceNotFoundException("User " + id + " for certificate not found");
        else return user;
    }
}
