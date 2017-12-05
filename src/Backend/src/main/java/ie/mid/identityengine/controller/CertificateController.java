package ie.mid.identityengine.controller;

import ie.mid.identityengine.dto.CertificateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @GetMapping(value = "/{id}")
    @ResponseBody
    public CertificateDTO getCertificate(@PathVariable String id) {
        //TODO implement link into hyperledger service and get the certificate tied to this id
        return new CertificateDTO();
    }
}
