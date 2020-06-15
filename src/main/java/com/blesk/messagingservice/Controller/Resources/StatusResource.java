package com.blesk.messagingservice.Controller.Resources;

import com.blesk.messagingservice.Exception.MessageServiceException;
import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Service.Status.StatusServiceImpl;
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
public class StatusResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private StatusServiceImpl statusService;

    @Autowired
    public StatusResource(StatusServiceImpl statusService){
        this.statusService = statusService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/status")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Status> createStatus(@Valid @RequestBody Status statuses, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Status status = this.statusService.createStatus(statuses);
        if (status == null) throw new MessageServiceException(Messages.CREATE_STATUS, HttpStatus.BAD_REQUEST);

        EntityModel<Status> entityModel = EntityModel.of(status);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveStatus(status.getStatusId(), httpServletRequest, httpServletResponse)).withRel("status"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/status/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteStatus(@PathVariable String statusId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Status status = this.statusService.getStatus(statusId);
        if (status == null) throw new MessageServiceException(Messages.GET_STATUS, HttpStatus.NOT_FOUND);
        if (!this.statusService.deleteStatus(status)) throw new MessageServiceException(Messages.DELETE_STATUS, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PutMapping("/status/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateStatus(@Valid @RequestBody Status statuses, @PathVariable String statusId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Status status = this.statusService.getStatus(statusId);
        if (status == null) throw new MessageServiceException(Messages.GET_STATUS, HttpStatus.BAD_REQUEST);

        if (!this.statusService.updateStatus(status, statuses)) throw new MessageServiceException(Messages.UPDATE_STATUS, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/status/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Status> retrieveStatus(@PathVariable String statusId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Status status = this.statusService.getStatus(statusId);
        if (status == null) throw new MessageServiceException(Messages.GET_STATUS, HttpStatus.BAD_REQUEST);

        EntityModel<Status> entityModel = EntityModel.of(status);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveStatus(statusId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllStatuses(StatusResource.DEFAULT_NUMBER, StatusResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-statuses"));
        return entityModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/status/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<Status> retrieveAllStatuses(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Status> status = this.statusService.getAllStatuses(pageNumber, pageSize);
        if (status == null || status.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_STATUSES, HttpStatus.BAD_REQUEST);

        CollectionModel<Status> collectionModel = CollectionModel.of(status);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllStatuses(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllStatuses(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/status/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Status> searchForStatus(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new MessageServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> status = this.statusService.searchForStatus(search);
        if (status == null || status.isEmpty()) throw new MessageServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<Status> collectionModel = CollectionModel.of((List<Status>) status.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForStatus(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) status.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForStatus(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) status.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForStatus(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/status/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Status> joinStatus(@PathVariable String columName, @RequestBody List<String> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Status> statuses = this.statusService.getStatusForJoin(ids, columName);
        if (statuses == null || statuses.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_STATUSES, HttpStatus.BAD_REQUEST);
        return CollectionModel.of(statuses);
    }
}