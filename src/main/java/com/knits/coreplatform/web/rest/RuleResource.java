package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.RuleRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.RuleService;
import com.knits.coreplatform.service.dto.RuleDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.Rule}.
 */
@RestController
@RequestMapping("/api")
public class RuleResource {

    private final Logger log = LoggerFactory.getLogger(RuleResource.class);

    private static final String ENTITY_NAME = "rule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleService ruleService;

    private final RuleRepository ruleRepository;

    public RuleResource(RuleService ruleService, RuleRepository ruleRepository) {
        this.ruleService = ruleService;
        this.ruleRepository = ruleRepository;
    }

    /**
     * {@code POST  /rules} : Create a new rule.
     *
     * @param ruleDTO the ruleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleDTO, or with status {@code 400 (Bad Request)} if the rule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rules")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<RuleDTO> createRule(@RequestBody RuleDTO ruleDTO) throws URISyntaxException {
        log.debug("REST request to save Rule : {}", ruleDTO);
        if (ruleDTO.getId() != null) {
            throw new BadRequestAlertException("A new rule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleDTO result = ruleService.save(ruleDTO);
        return ResponseEntity
            .created(new URI("/api/rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rules/:id} : Updates an existing rule.
     *
     * @param id the id of the ruleDTO to save.
     * @param ruleDTO the ruleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleDTO,
     * or with status {@code 400 (Bad Request)} if the ruleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rules/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<RuleDTO> updateRule(@PathVariable(value = "id", required = false) final Long id, @RequestBody RuleDTO ruleDTO)
        throws URISyntaxException {
        log.debug("REST request to update Rule : {}, {}", id, ruleDTO);
        if (ruleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RuleDTO result = ruleService.save(ruleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rules/:id} : Partial updates given fields of an existing rule, field will ignore if it is null
     *
     * @param id the id of the ruleDTO to save.
     * @param ruleDTO the ruleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleDTO,
     * or with status {@code 400 (Bad Request)} if the ruleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ruleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ruleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rules/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<RuleDTO> partialUpdateRule(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RuleDTO ruleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rule partially : {}, {}", id, ruleDTO);
        if (ruleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RuleDTO> result = ruleService.partialUpdate(ruleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rules} : get all the rules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rules in body.
     */
    @GetMapping("/rules")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<RuleDTO> getAllRules() {
        log.debug("REST request to get all Rules");
        return ruleService.findAll();
    }

    /**
     * {@code GET  /rules/:id} : get the "id" rule.
     *
     * @param id the id of the ruleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rules/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<RuleDTO> getRule(@PathVariable Long id) {
        log.debug("REST request to get Rule : {}", id);
        Optional<RuleDTO> ruleDTO = ruleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruleDTO);
    }

    /**
     * {@code DELETE  /rules/:id} : delete the "id" rule.
     *
     * @param id the id of the ruleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rules/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        log.debug("REST request to delete Rule : {}", id);
        ruleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
