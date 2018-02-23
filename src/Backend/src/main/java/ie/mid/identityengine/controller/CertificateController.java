package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.CertificateDTO;
import ie.mid.identityengine.enums.EntityStatus;
import ie.mid.identityengine.model.Certificate;
import ie.mid.identityengine.model.CertificateUpdate;
import ie.mid.identityengine.service.HyperledgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    HyperledgerService hyperledgerService;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO getCertificate(@PathVariable String id) {
        Certificate certificate = hyperledgerService.getCertificate(id);
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setCreatedBy(certificate.getTrustee());
        certificateDTO.setOwnedBy(certificate.getOwner());
        certificateDTO.setId(certificate.getCertId());
        certificateDTO.setStatus(certificate.getStatus());
        certificateDTO.setCreatedAt(certificate.getDateCreated());
        return certificateDTO;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO deleteCertificate(@PathVariable String id) {
        hyperledgerService.updateCertificate(id, EntityStatus.DELETED.toString());
        return getCertificate(id);
    }
}
