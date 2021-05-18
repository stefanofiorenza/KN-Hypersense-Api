package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.ConfigurationDataRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.ConfigurationDataService;
import com.knits.coreplatform.service.dto.ConfigurationDataDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.ConfigurationData}.
 */
@RestController
@RequestMapping("/api")
public class ConfigurationDataResource {

    private final Logger log = LoggerFactory.getLogger(ConfigurationDataResource.class);

    private static final String ENTITY_NAME = "configurationData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigurationDataService configurationDataService;

    private final ConfigurationDataRepository configurationDataRepository;

    public ConfigurationDataResource(
        ConfigurationDataService configurationDataService,
        ConfigurationDataRepository configurationDataRepository
    ) {
        this.configurationDataService = configurationDataService;
        this.configurationDataRepository = configurationDataRepository;
    }

    /**
     * {@code POST  /configuration-data} : Create a new configurationData.
     *
     * @param configurationDataDTO the configurationDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configurationDataDTO, or with status {@code 400 (Bad Request)} if the configurationData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/configuration-data")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<ConfigurationDataDTO> createConfigurationData(@RequestBody ConfigurationDataDTO configurationDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConfigurationData : {}", configurationDataDTO);
        if (configurationDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new configurationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigurationDataDTO result = configurationDataService.save(configurationDataDTO);
        return ResponseEntity
            .created(new URI("/api/configuration-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /configuration-data/:id} : Updates an existing configurationData.
     *
     * @param id the id of the configurationDataDTO to save.
     * @param configurationDataDTO the configurationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurationDataDTO,
     * or with status {@code 400 (Bad Request)} if the configurationDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configurationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/configuration-data/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<ConfigurationDataDTO> updateConfigurationData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigurationDataDTO configurationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConfigurationData : {}, {}", id, configurationDataDTO);
        if (configurationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configurationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configurationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConfigurationDataDTO result = configurationDataService.save(configurationDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configurationDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /configuration-data/:id} : Partial updates given fields of an existing configurationData, field will ignore if it is null
     *
     * @param id the id of the configurationDataDTO to save.
     * @param configurationDataDTO the configurationDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configurationDataDTO,
     * or with status {@code 400 (Bad Request)} if the configurationDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the configurationDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the configurationDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/configuration-data/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<ConfigurationDataDTO> partialUpdateConfigurationData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigurationDataDTO configurationDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConfigurationData partially : {}, {}", id, configurationDataDTO);
        if (configurationDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configurationDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configurationDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfigurationDataDTO> result = configurationDataService.partialUpdate(configurationDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configurationDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /configuration-data} : get all the configurationData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configurationData in body.
     */
    @GetMapping("/configuration-data")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<ConfigurationDataDTO> getAllConfigurationData() {
        log.debug("REST request to get all ConfigurationData");
        return configurationDataService.findAll();
    }

    /**
     * {@code GET  /configuration-data/:id} : get the "id" configurationData.
     *
     * @param id the id of the configurationDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configurationDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/configuration-data/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<ConfigurationDataDTO> getConfigurationData(@PathVariable Long id) {
        log.debug("REST request to get ConfigurationData : {}", id);
        Optional<ConfigurationDataDTO> configurationDataDTO = configurationDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configurationDataDTO);
    }

    /**
     * {@code DELETE  /configuration-data/:id} : delete the "id" configurationData.
     *
     * @param id the id of the configurationDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/configuration-data/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteConfigurationData(@PathVariable Long id) {
        log.debug("REST request to delete ConfigurationData : {}", id);
        configurationDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
