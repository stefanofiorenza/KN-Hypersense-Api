package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.ThingCategoryRepository;
import com.knits.coreplatform.service.ThingCategoryService;
import com.knits.coreplatform.service.dto.ThingCategoryDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.ThingCategory}.
 */
@RestController
@RequestMapping("/api")
public class ThingCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ThingCategoryResource.class);

    private static final String ENTITY_NAME = "thingCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThingCategoryService thingCategoryService;

    private final ThingCategoryRepository thingCategoryRepository;

    public ThingCategoryResource(ThingCategoryService thingCategoryService, ThingCategoryRepository thingCategoryRepository) {
        this.thingCategoryService = thingCategoryService;
        this.thingCategoryRepository = thingCategoryRepository;
    }

    /**
     * {@code POST  /thing-categories} : Create a new thingCategory.
     *
     * @param thingCategoryDTO the thingCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thingCategoryDTO, or with status {@code 400 (Bad Request)} if the thingCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thing-categories")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.COMPANY_ADMIN + "\")")
    public ResponseEntity<ThingCategoryDTO> createThingCategory(@RequestBody ThingCategoryDTO thingCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ThingCategory : {}", thingCategoryDTO);
        if (thingCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new thingCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThingCategoryDTO result = thingCategoryService.save(thingCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/thing-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thing-categories/:id} : Updates an existing thingCategory.
     *
     * @param id the id of the thingCategoryDTO to save.
     * @param thingCategoryDTO the thingCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the thingCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thingCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thing-categories/{id}")
    public ResponseEntity<ThingCategoryDTO> updateThingCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingCategoryDTO thingCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThingCategory : {}, {}", id, thingCategoryDTO);
        if (thingCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThingCategoryDTO result = thingCategoryService.save(thingCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thing-categories/:id} : Partial updates given fields of an existing thingCategory, field will ignore if it is null
     *
     * @param id the id of the thingCategoryDTO to save.
     * @param thingCategoryDTO the thingCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thingCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the thingCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thingCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thingCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thing-categories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ThingCategoryDTO> partialUpdateThingCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThingCategoryDTO thingCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThingCategory partially : {}, {}", id, thingCategoryDTO);
        if (thingCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thingCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thingCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThingCategoryDTO> result = thingCategoryService.partialUpdate(thingCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thingCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /thing-categories} : get all the thingCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thingCategories in body.
     */
    @GetMapping("/thing-categories")
    public List<ThingCategoryDTO> getAllThingCategories() {
        log.debug("REST request to get all ThingCategories");
        return thingCategoryService.findAll();
    }

    /**
     * {@code GET  /thing-categories/:id} : get the "id" thingCategory.
     *
     * @param id the id of the thingCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thingCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thing-categories/{id}")
    public ResponseEntity<ThingCategoryDTO> getThingCategory(@PathVariable Long id) {
        log.debug("REST request to get ThingCategory : {}", id);
        Optional<ThingCategoryDTO> thingCategoryDTO = thingCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thingCategoryDTO);
    }

    /**
     * {@code DELETE  /thing-categories/:id} : delete the "id" thingCategory.
     *
     * @param id the id of the thingCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thing-categories/{id}")
    public ResponseEntity<Void> deleteThingCategory(@PathVariable Long id) {
        log.debug("REST request to delete ThingCategory : {}", id);
        thingCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
