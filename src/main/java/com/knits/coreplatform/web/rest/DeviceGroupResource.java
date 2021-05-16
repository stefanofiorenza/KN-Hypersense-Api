package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.DeviceGroupRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.DeviceGroupService;
import com.knits.coreplatform.service.dto.DeviceGroupDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.DeviceGroup}.
 */
@RestController
@RequestMapping("/api")
public class DeviceGroupResource {

    private final Logger log = LoggerFactory.getLogger(DeviceGroupResource.class);

    private static final String ENTITY_NAME = "deviceGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceGroupService deviceGroupService;

    private final DeviceGroupRepository deviceGroupRepository;

    public DeviceGroupResource(DeviceGroupService deviceGroupService, DeviceGroupRepository deviceGroupRepository) {
        this.deviceGroupService = deviceGroupService;
        this.deviceGroupRepository = deviceGroupRepository;
    }

    /**
     * {@code POST  /device-groups} : Create a new deviceGroup.
     *
     * @param deviceGroupDTO the deviceGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceGroupDTO, or with status {@code 400 (Bad Request)} if the deviceGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-groups")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<DeviceGroupDTO> createDeviceGroup(@RequestBody DeviceGroupDTO deviceGroupDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceGroup : {}", deviceGroupDTO);
        if (deviceGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceGroupDTO result = deviceGroupService.save(deviceGroupDTO);
        return ResponseEntity
            .created(new URI("/api/device-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-groups/:id} : Updates an existing deviceGroup.
     *
     * @param id the id of the deviceGroupDTO to save.
     * @param deviceGroupDTO the deviceGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceGroupDTO,
     * or with status {@code 400 (Bad Request)} if the deviceGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-groups/{id}")
    public ResponseEntity<DeviceGroupDTO> updateDeviceGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceGroupDTO deviceGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DeviceGroup : {}, {}", id, deviceGroupDTO);
        if (deviceGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeviceGroupDTO result = deviceGroupService.save(deviceGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /device-groups/:id} : Partial updates given fields of an existing deviceGroup, field will ignore if it is null
     *
     * @param id the id of the deviceGroupDTO to save.
     * @param deviceGroupDTO the deviceGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceGroupDTO,
     * or with status {@code 400 (Bad Request)} if the deviceGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deviceGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deviceGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/device-groups/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DeviceGroupDTO> partialUpdateDeviceGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeviceGroupDTO deviceGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeviceGroup partially : {}, {}", id, deviceGroupDTO);
        if (deviceGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deviceGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deviceGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeviceGroupDTO> result = deviceGroupService.partialUpdate(deviceGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /device-groups} : get all the deviceGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceGroups in body.
     */
    @GetMapping("/device-groups")
    public List<DeviceGroupDTO> getAllDeviceGroups() {
        log.debug("REST request to get all DeviceGroups");
        return deviceGroupService.findAll();
    }

    /**
     * {@code GET  /device-groups/:id} : get the "id" deviceGroup.
     *
     * @param id the id of the deviceGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-groups/{id}")
    public ResponseEntity<DeviceGroupDTO> getDeviceGroup(@PathVariable Long id) {
        log.debug("REST request to get DeviceGroup : {}", id);
        Optional<DeviceGroupDTO> deviceGroupDTO = deviceGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceGroupDTO);
    }

    /**
     * {@code DELETE  /device-groups/:id} : delete the "id" deviceGroup.
     *
     * @param id the id of the deviceGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-groups/{id}")
    public ResponseEntity<Void> deleteDeviceGroup(@PathVariable Long id) {
        log.debug("REST request to delete DeviceGroup : {}", id);
        deviceGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
