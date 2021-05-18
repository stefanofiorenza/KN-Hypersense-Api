package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.OrganisationRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.OrganisationService;
import com.knits.coreplatform.service.dto.OrganisationDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.Organisation}.
 */
@RestController
@RequestMapping("/api")
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);

    private static final String ENTITY_NAME = "organisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisationService organisationService;

    private final OrganisationRepository organisationRepository;

    public OrganisationResource(OrganisationService organisationService, OrganisationRepository organisationRepository) {
        this.organisationService = organisationService;
        this.organisationRepository = organisationRepository;
    }

    /**
     * {@code POST  /organisations} : Create a new organisation.
     *
     * @param organisationDTO the organisationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisationDTO, or with status {@code 400 (Bad Request)} if the organisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<OrganisationDTO> createOrganisation(@RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisationDTO);
        if (organisationDTO.getId() != null) {
            throw new BadRequestAlertException("A new organisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity
            .created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisations/:id} : Updates an existing organisation.
     *
     * @param id the id of the organisationDTO to save.
     * @param organisationDTO the organisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationDTO,
     * or with status {@code 400 (Bad Request)} if the organisationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<OrganisationDTO> updateOrganisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganisationDTO organisationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}, {}", id, organisationDTO);
        if (organisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organisations/:id} : Partial updates given fields of an existing organisation, field will ignore if it is null
     *
     * @param id the id of the organisationDTO to save.
     * @param organisationDTO the organisationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisationDTO,
     * or with status {@code 400 (Bad Request)} if the organisationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organisationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organisationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organisations/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<OrganisationDTO> partialUpdateOrganisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganisationDTO organisationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organisation partially : {}, {}", id, organisationDTO);
        if (organisationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organisationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganisationDTO> result = organisationService.partialUpdate(organisationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organisations} : get all the organisations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisations in body.
     */
    @GetMapping("/organisations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<OrganisationDTO> getAllOrganisations() {
        log.debug("REST request to get all Organisations");
        return organisationService.findAll();
    }

    /**
     * {@code GET  /organisations/:id} : get the "id" organisation.
     *
     * @param id the id of the organisationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<OrganisationDTO> getOrganisation(@PathVariable Long id) {
        log.debug("REST request to get Organisation : {}", id);
        Optional<OrganisationDTO> organisationDTO = organisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisationDTO);
    }

    /**
     * {@code DELETE  /organisations/:id} : delete the "id" organisation.
     *
     * @param id the id of the organisationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
