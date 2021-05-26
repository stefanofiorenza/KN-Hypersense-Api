package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.ThingRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.ThingService;
import com.knits.coreplatform.service.dto.ThingDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.Thing}.
 */
@RestController
@RequestMapping("/api")
public class ThingResource {

    private final Logger log = LoggerFactory.getLogger(ThingResource.class);

    private static final String ENTITY_NAME = "thing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThingService thingService;

    private final ThingRepository thingRepository;

    public ThingResource(ThingService thingService, ThingRepository thingRepository) {
        this.thingService = thingService;
        this.thingRepository = thingRepository;
    }

    /**
     * {@code POST  /things} : Create a new thing.
     *
     * @param thingDTO the thingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thingDTO, or with status {@code 400 (Bad Request)} if the thing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/things")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<ThingDTO> createThing(@RequestBody ThingDTO thingDTO) throws URISyntaxException {
        log.debug("REST request to save Thing : {}", thingDTO);
        if (thingDTO.getId() != null) {
            throw new BadRequestAlertException("A new thing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThingDTO result = thingService.save(thingDTO);
        return ResponseEntity
            .created(new URI("/api/things/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /things/:id} : Updates an existing thing.
     *
     * @param id the id of the thingDTO to save.
     * @param thingDTO the thingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingDTO,
     * or with status {@code 400 (Bad Request)} if the thingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/things/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<ThingDTO> updateThing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingDTO thingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Thing : {}, {}", id, thingDTO);
        if (thingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThingDTO result = thingService.save(thingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /things/:id} : Partial updates given fields of an existing thing, field will ignore if it is null
     *
     * @param id the id of the thingDTO to save.
     * @param thingDTO the thingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingDTO,
     * or with status {@code 400 (Bad Request)} if the thingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/things/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<ThingDTO> partialUpdateThing(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingDTO thingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thing partially : {}, {}", id, thingDTO);
        if (thingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThingDTO> result = thingService.partialUpdate(thingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /things} : get all the things.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of things in body.
     */
    @GetMapping("/things")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<ThingDTO> getAllThings(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Things");
        return thingService.findAll();
    }

    /**
     * {@code GET  /things/:id} : get the "id" thing.
     *
     * @param id the id of the thingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/things/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<ThingDTO> getThing(@PathVariable Long id) {
        log.debug("REST request to get Thing : {}", id);
        Optional<ThingDTO> thingDTO = thingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thingDTO);
    }

    /**
     * {@code DELETE  /things/:id} : delete the "id" thing.
     *
     * @param id the id of the thingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/things/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteThing(@PathVariable Long id) {
        log.debug("REST request to delete Thing : {}", id);
        thingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
