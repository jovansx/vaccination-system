package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.websocket.server.PathParam;
import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("/api/documents")
public class InteresovanjeController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/{jmbg}")
    public String getInteresovanje(@PathVariable String jmbg) {
        return documentService.getInteresovanje(jmbg);
    }

    @PostMapping
    public String createInteresovanje(@RequestBody String interesovanjeXml) throws JAXBException, SAXException {
        return documentService.createInteresovanje(interesovanjeXml);
    }
}
