package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.TelemetryRepository;
import com.knits.coreplatform.service.TelemetryService;
import com.knits.coreplatform.service.dto.TelemetryDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.Telemetry}.
 */
@RestController
@RequestMapping("/api")
public class TelemetryResource {

    private final Logger log = LoggerFactory.getLogger(TelemetryResource.class);

    private static final String ENTITY_NAME = "telemetry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelemetryService telemetryService;

    private final TelemetryRepository telemetryRepository;

    public TelemetryResource(TelemetryService telemetryService, TelemetryRepository telemetryRepository) {
        this.telemetryService = telemetryService;
        this.telemetryRepository = telemetryRepository;
    }

    /**
     * {@code POST  /telemetries} : Create a new telemetry.
     *
     * @param telemetryDTO the telemetryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telemetryDTO, or with status {@code 400 (Bad Request)} if the telemetry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telemetries")
    public ResponseEntity<TelemetryDTO> createTelemetry(@RequestBody TelemetryDTO telemetryDTO) throws URISyntaxException {
        log.debug("REST request to save Telemetry : {}", telemetryDTO);
        if (telemetryDTO.getId() != null) {
            throw new BadRequestAlertException("A new telemetry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelemetryDTO result = telemetryService.save(telemetryDTO);
        return ResponseEntity
            .created(new URI("/api/telemetries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telemetries/:id} : Updates an existing telemetry.
     *
     * @param id the id of the telemetryDTO to save.
     * @param telemetryDTO the telemetryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telemetryDTO,
     * or with status {@code 400 (Bad Request)} if the telemetryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telemetryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telemetries/{id}")
    public ResponseEntity<TelemetryDTO> updateTelemetry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelemetryDTO telemetryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Telemetry : {}, {}", id, telemetryDTO);
        if (telemetryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telemetryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telemetryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelemetryDTO result = telemetryService.save(telemetryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telemetryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telemetries/:id} : Partial updates given fields of an existing telemetry, field will ignore if it is null
     *
     * @param id the id of the telemetryDTO to save.
     * @param telemetryDTO the telemetryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telemetryDTO,
     * or with status {@code 400 (Bad Request)} if the telemetryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telemetryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telemetryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telemetries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TelemetryDTO> partialUpdateTelemetry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelemetryDTO telemetryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telemetry partially : {}, {}", id, telemetryDTO);
        if (telemetryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telemetryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telemetryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelemetryDTO> result = telemetryService.partialUpdate(telemetryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, telemetryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telemetries} : get all the telemetries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telemetries in body.
     */
    @GetMapping("/telemetries")
    public List<TelemetryDTO> getAllTelemetries() {
        log.debug("REST request to get all Telemetries");
        return telemetryService.findAll();
    }

    /**
     * {@code GET  /telemetries/:id} : get the "id" telemetry.
     *
     * @param id the id of the telemetryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telemetryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telemetries/{id}")
    public ResponseEntity<TelemetryDTO> getTelemetry(@PathVariable Long id) {
        log.debug("REST request to get Telemetry : {}", id);
        Optional<TelemetryDTO> telemetryDTO = telemetryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telemetryDTO);
    }

    /**
     * {@code DELETE  /telemetries/:id} : delete the "id" telemetry.
     *
     * @param id the id of the telemetryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telemetries/{id}")
    public ResponseEntity<Void> deleteTelemetry(@PathVariable Long id) {
        log.debug("REST request to delete Telemetry : {}", id);
        telemetryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
