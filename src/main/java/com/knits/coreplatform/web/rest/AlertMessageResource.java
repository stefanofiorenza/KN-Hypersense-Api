package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.AlertMessageRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.AlertMessageService;
import com.knits.coreplatform.service.dto.AlertMessageDTO;
import com.knits.coreplatform.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.knits.coreplatform.domain.AlertMessage}.
 */
@RestController
@RequestMapping("/api")
public class AlertMessageResource {

    private final Logger log = LoggerFactory.getLogger(AlertMessageResource.class);

    private static final String ENTITY_NAME = "alertMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertMessageService alertMessageService;

    private final AlertMessageRepository alertMessageRepository;

    public AlertMessageResource(AlertMessageService alertMessageService, AlertMessageRepository alertMessageRepository) {
        this.alertMessageService = alertMessageService;
        this.alertMessageRepository = alertMessageRepository;
    }

    /**
     * {@code POST  /alert-messages} : Create a new alertMessage.
     *
     * @param alertMessageDTO the alertMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertMessageDTO, or with status {@code 400 (Bad Request)} if the alertMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alert-messages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<AlertMessageDTO> createAlertMessage(@RequestBody AlertMessageDTO alertMessageDTO) throws URISyntaxException {
        log.debug("REST request to save AlertMessage : {}", alertMessageDTO);
        if (alertMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertMessageDTO result = alertMessageService.save(alertMessageDTO);
        return ResponseEntity
            .created(new URI("/api/alert-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alert-messages/:id} : Updates an existing alertMessage.
     *
     * @param id the id of the alertMessageDTO to save.
     * @param alertMessageDTO the alertMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertMessageDTO,
     * or with status {@code 400 (Bad Request)} if the alertMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alert-messages/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<AlertMessageDTO> updateAlertMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertMessageDTO alertMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AlertMessage : {}, {}", id, alertMessageDTO);
        if (alertMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlertMessageDTO result = alertMessageService.save(alertMessageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alertMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alert-messages/:id} : Partial updates given fields of an existing alertMessage, field will ignore if it is null
     *
     * @param id the id of the alertMessageDTO to save.
     * @param alertMessageDTO the alertMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertMessageDTO,
     * or with status {@code 400 (Bad Request)} if the alertMessageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alertMessageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alertMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alert-messages/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<AlertMessageDTO> partialUpdateAlertMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertMessageDTO alertMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlertMessage partially : {}, {}", id, alertMessageDTO);
        if (alertMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlertMessageDTO> result = alertMessageService.partialUpdate(alertMessageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alertMessageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alert-messages} : get all the alertMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertMessages in body.
     */
    @GetMapping("/alert-messages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<AlertMessageDTO> getAllAlertMessages() {
        log.debug("REST request to get all AlertMessages");
        return alertMessageService.findAll();
    }

    /**
     * {@code GET  /alert-messages/:id} : get the "id" alertMessage.
     *
     * @param id the id of the alertMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alert-messages/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<AlertMessageDTO> getAlertMessage(@PathVariable Long id) {
        log.debug("REST request to get AlertMessage : {}", id);
        Optional<AlertMessageDTO> alertMessageDTO = alertMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertMessageDTO);
    }

    /**
     * {@code DELETE  /alert-messages/:id} : delete the "id" alertMessage.
     *
     * @param id the id of the alertMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alert-messages/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteAlertMessage(@PathVariable Long id) {
        log.debug("REST request to delete AlertMessage : {}", id);
        alertMessageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
