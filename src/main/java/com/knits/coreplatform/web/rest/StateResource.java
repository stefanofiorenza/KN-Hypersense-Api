package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.StateRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.StateService;
import com.knits.coreplatform.service.dto.StateDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.State}.
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    private final Logger log = LoggerFactory.getLogger(StateResource.class);

    private static final String ENTITY_NAME = "state";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateService stateService;

    private final StateRepository stateRepository;

    public StateResource(StateService stateService, StateRepository stateRepository) {
        this.stateService = stateService;
        this.stateRepository = stateRepository;
    }

    /**
     * {@code POST  /states} : Create a new state.
     *
     * @param stateDTO the stateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateDTO, or with status {@code 400 (Bad Request)} if the state has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/states")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<StateDTO> createState(@RequestBody StateDTO stateDTO) throws URISyntaxException {
        log.debug("REST request to save State : {}", stateDTO);
        if (stateDTO.getId() != null) {
            throw new BadRequestAlertException("A new state cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateDTO result = stateService.save(stateDTO);
        return ResponseEntity
            .created(new URI("/api/states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /states/:id} : Updates an existing state.
     *
     * @param id the id of the stateDTO to save.
     * @param stateDTO the stateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateDTO,
     * or with status {@code 400 (Bad Request)} if the stateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/states/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<StateDTO> updateState(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StateDTO stateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update State : {}, {}", id, stateDTO);
        if (stateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StateDTO result = stateService.save(stateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /states/:id} : Partial updates given fields of an existing state, field will ignore if it is null
     *
     * @param id the id of the stateDTO to save.
     * @param stateDTO the stateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateDTO,
     * or with status {@code 400 (Bad Request)} if the stateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/states/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<StateDTO> partialUpdateState(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StateDTO stateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update State partially : {}, {}", id, stateDTO);
        if (stateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StateDTO> result = stateService.partialUpdate(stateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /states} : get all the states.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of states in body.
     */
    @GetMapping("/states")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<StateDTO> getAllStates() {
        log.debug("REST request to get all States");
        return stateService.findAll();
    }

    /**
     * {@code GET  /states/:id} : get the "id" state.
     *
     * @param id the id of the stateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/states/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<StateDTO> getState(@PathVariable Long id) {
        log.debug("REST request to get State : {}", id);
        Optional<StateDTO> stateDTO = stateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateDTO);
    }

    /**
     * {@code DELETE  /states/:id} : delete the "id" state.
     *
     * @param id the id of the stateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/states/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        log.debug("REST request to delete State : {}", id);
        stateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
