package com.blesk.messagingservice.Controller;

import com.blesk.messagingservice.Exception.MessageServiceException;
import com.blesk.messagingservice.Model.Communications;
import com.blesk.messagingservice.Service.Communications.CommunicationsServiceImpl;
import com.blesk.messagingservice.Value.Keys;
import com.blesk.messagingservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CommunicationsResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private CommunicationsServiceImpl communicationsService;

    @Autowired
    public CommunicationsResource(CommunicationsServiceImpl communicationsService){
        this.communicationsService = communicationsService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/communications")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Communications> createCommunications(@Valid @RequestBody Communications communications, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Communications communication = this.communicationsService.createCommunication(communications);
        if (communication == null) throw new MessageServiceException(Messages.CREATE_COMMUNICATION, HttpStatus.BAD_REQUEST);

        EntityModel<Communications> entityModel = EntityModel.of(communication);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveCommunications(communication.getCommunicationId(), httpServletRequest, httpServletResponse)).withRel("communication"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @DeleteMapping("/communications/{communicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteCommunications(@PathVariable String communicationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Communications communication = this.communicationsService.getCommunication(communicationId);
        if (communication == null) throw new MessageServiceException(Messages.GET_COMMUNICATION, HttpStatus.NOT_FOUND);
        if (!this.communicationsService.deleteCommunication(communication)) throw new MessageServiceException(Messages.DELETE_COMMUNICATION, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER')")
    @PutMapping("/communications/{communicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateCommunications(@Valid @RequestBody Communications communications, @PathVariable String communicationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Communications communication = this.communicationsService.getCommunication(communicationId);
        if (communication == null) throw new MessageServiceException(Messages.GET_COMMUNICATION, HttpStatus.BAD_REQUEST);

        if (!this.communicationsService.updateCommunication(communication, communications)) throw new MessageServiceException(Messages.UPDATE_COMMUNICATION, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/communications/{communicationId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Communications> retrieveCommunications(@PathVariable String communicationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Communications communication = this.communicationsService.getCommunication(communicationId);
        if (communication == null) throw new MessageServiceException(Messages.GET_COMMUNICATION, HttpStatus.BAD_REQUEST);

        EntityModel<Communications> entityModel = EntityModel.of(communication);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveCommunications(communicationId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllCommunications(CommunicationsResource.DEFAULT_NUMBER, CommunicationsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-communications"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/communications/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<Communications> retrieveAllCommunications(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Communications> communications = this.communicationsService.getAllCommunications(pageNumber, pageSize);
        if (communications == null || communications.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_COMMUNICATIONS, HttpStatus.BAD_REQUEST);

        CollectionModel<Communications> collectionModel = CollectionModel.of(communications);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllCommunications(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllCommunications(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/communications/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Communications> searchForCommunications(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new MessageServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> communication = this.communicationsService.searchForCommunication(search);
        if (communication == null || communication.isEmpty()) throw new MessageServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<Communications> collectionModel = CollectionModel.of((List<Communications>) communication.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForCommunications(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) communication.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForCommunications(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) communication.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForCommunications(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/communications/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Communications> joinCommunications(@PathVariable String columName, @RequestBody List<String> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Communications> communications = this.communicationsService.getCommunicationsForJoin(ids, columName);
        if (communications == null || communications.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_COMMUNICATIONS, HttpStatus.BAD_REQUEST);
        return CollectionModel.of(communications);
    }
}