package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.DeviceConfigurationRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.DeviceConfigurationService;
import com.knits.coreplatform.service.dto.DeviceConfigurationDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.DeviceConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class DeviceConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(DeviceConfigurationResource.class);

    private static final String ENTITY_NAME = "deviceConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceConfigurationService deviceConfigurationService;

    private final DeviceConfigurationRepository deviceConfigurationRepository;

    public DeviceConfigurationResource(
        DeviceConfigurationService deviceConfigurationService,
        DeviceConfigurationRepository deviceConfigurationRepository
    ) {
        this.deviceConfigurationService = deviceConfigurationService;
        this.deviceConfigurationRepository = deviceConfigurationRepository;
    }

    /**
     * {@code POST  /device-configurations} : Create a new deviceConfiguration.
     *
     * @param deviceConfigurationDTO the deviceConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceConfigurationDTO, or with status {@code 400 (Bad Request)} if the deviceConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-configurations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<DeviceConfigurationDTO> createDeviceConfiguration(@RequestBody DeviceConfigurationDTO deviceConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save DeviceConfiguration : {}", deviceConfigurationDTO);
        if (deviceConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceConfigurationDTO result = deviceConfigurationService.save(deviceConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/device-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-configurations/:id} : Updates an existing deviceConfiguration.
     *
     * @param id the id of the deviceConfigurationDTO to save.
     * @param deviceConfigurationDTO the deviceConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the deviceConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<DeviceConfigurationDTO> updateDeviceConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceConfigurationDTO deviceConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceConfiguration : {}, {}", id, deviceConfigurationDTO);
        if (deviceConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceConfigurationDTO result = deviceConfigurationService.save(deviceConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-configurations/:id} : Partial updates given fields of an existing deviceConfiguration, field will ignore if it is null
     *
     * @param id the id of the deviceConfigurationDTO to save.
     * @param deviceConfigurationDTO the deviceConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the deviceConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deviceConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-configurations/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<DeviceConfigurationDTO> partialUpdateDeviceConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceConfigurationDTO deviceConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceConfiguration partially : {}, {}", id, deviceConfigurationDTO);
        if (deviceConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceConfigurationDTO> result = deviceConfigurationService.partialUpdate(deviceConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /device-configurations} : get all the deviceConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceConfigurations in body.
     */
    @GetMapping("/device-configurations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<DeviceConfigurationDTO> getAllDeviceConfigurations() {
        log.debug("REST request to get all DeviceConfigurations");
        return deviceConfigurationService.findAll();
    }

    /**
     * {@code GET  /device-configurations/:id} : get the "id" deviceConfiguration.
     *
     * @param id the id of the deviceConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<DeviceConfigurationDTO> getDeviceConfiguration(@PathVariable Long id) {
        log.debug("REST request to get DeviceConfiguration : {}", id);
        Optional<DeviceConfigurationDTO> deviceConfigurationDTO = deviceConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceConfigurationDTO);
    }

    /**
     * {@code DELETE  /device-configurations/:id} : delete the "id" deviceConfiguration.
     *
     * @param id the id of the deviceConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteDeviceConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete DeviceConfiguration : {}", id);
        deviceConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
