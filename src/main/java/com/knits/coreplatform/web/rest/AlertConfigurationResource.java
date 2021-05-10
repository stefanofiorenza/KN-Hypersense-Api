package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.AlertConfigurationRepository;
import com.knits.coreplatform.service.AlertConfigurationService;
import com.knits.coreplatform.service.dto.AlertConfigurationDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.knits.coreplatform.domain.AlertConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class AlertConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AlertConfigurationResource.class);

    private static final String ENTITY_NAME = "alertConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlertConfigurationService alertConfigurationService;

    private final AlertConfigurationRepository alertConfigurationRepository;

    public AlertConfigurationResource(
        AlertConfigurationService alertConfigurationService,
        AlertConfigurationRepository alertConfigurationRepository
    ) {
        this.alertConfigurationService = alertConfigurationService;
        this.alertConfigurationRepository = alertConfigurationRepository;
    }

    /**
     * {@code POST  /alert-configurations} : Create a new alertConfiguration.
     *
     * @param alertConfigurationDTO the alertConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alertConfigurationDTO, or with status {@code 400 (Bad Request)} if the alertConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alert-configurations")
    public ResponseEntity<AlertConfigurationDTO> createAlertConfiguration(@RequestBody AlertConfigurationDTO alertConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save AlertConfiguration : {}", alertConfigurationDTO);
        if (alertConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new alertConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlertConfigurationDTO result = alertConfigurationService.save(alertConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/alert-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alert-configurations/:id} : Updates an existing alertConfiguration.
     *
     * @param id the id of the alertConfigurationDTO to save.
     * @param alertConfigurationDTO the alertConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the alertConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alertConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alert-configurations/{id}")
    public ResponseEntity<AlertConfigurationDTO> updateAlertConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertConfigurationDTO alertConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AlertConfiguration : {}, {}", id, alertConfigurationDTO);
        if (alertConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlertConfigurationDTO result = alertConfigurationService.save(alertConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alertConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alert-configurations/:id} : Partial updates given fields of an existing alertConfiguration, field will ignore if it is null
     *
     * @param id the id of the alertConfigurationDTO to save.
     * @param alertConfigurationDTO the alertConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alertConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the alertConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alertConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alertConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alert-configurations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AlertConfigurationDTO> partialUpdateAlertConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AlertConfigurationDTO alertConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AlertConfiguration partially : {}, {}", id, alertConfigurationDTO);
        if (alertConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alertConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alertConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlertConfigurationDTO> result = alertConfigurationService.partialUpdate(alertConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alertConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alert-configurations} : get all the alertConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertConfigurations in body.
     */
    @GetMapping("/alert-configurations")
    public List<AlertConfigurationDTO> getAllAlertConfigurations() {
        log.debug("REST request to get all AlertConfigurations");
        return alertConfigurationService.findAll();
    }

    /**
     * {@code GET  /alert-configurations/:id} : get the "id" alertConfiguration.
     *
     * @param id the id of the alertConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alertConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alert-configurations/{id}")
    public ResponseEntity<AlertConfigurationDTO> getAlertConfiguration(@PathVariable Long id) {
        log.debug("REST request to get AlertConfiguration : {}", id);
        Optional<AlertConfigurationDTO> alertConfigurationDTO = alertConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alertConfigurationDTO);
    }

    /**
     * {@code DELETE  /alert-configurations/:id} : delete the "id" alertConfiguration.
     *
     * @param id the id of the alertConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alert-configurations/{id}")
    public ResponseEntity<Void> deleteAlertConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete AlertConfiguration : {}", id);
        alertConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
