package com.knits.coreplatform.web.rest;

import com.knits.coreplatform.repository.RuleConfigurationRepository;
import com.knits.coreplatform.security.AuthoritiesConstants;
import com.knits.coreplatform.service.RuleConfigurationService;
import com.knits.coreplatform.service.dto.RuleConfigurationDTO;
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
 * REST controller for managing {@link com.knits.coreplatform.domain.RuleConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class RuleConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(RuleConfigurationResource.class);

    private static final String ENTITY_NAME = "ruleConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RuleConfigurationService ruleConfigurationService;

    private final RuleConfigurationRepository ruleConfigurationRepository;

    public RuleConfigurationResource(
        RuleConfigurationService ruleConfigurationService,
        RuleConfigurationRepository ruleConfigurationRepository
    ) {
        this.ruleConfigurationService = ruleConfigurationService;
        this.ruleConfigurationRepository = ruleConfigurationRepository;
    }

    /**
     * {@code POST  /rule-configurations} : Create a new ruleConfiguration.
     *
     * @param ruleConfigurationDTO the ruleConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleConfigurationDTO, or with status {@code 400 (Bad Request)} if the ruleConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rule-configurations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_CREATE + "\")")
    public ResponseEntity<RuleConfigurationDTO> createRuleConfiguration(@RequestBody RuleConfigurationDTO ruleConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save RuleConfiguration : {}", ruleConfigurationDTO);
        if (ruleConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new ruleConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleConfigurationDTO result = ruleConfigurationService.save(ruleConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/rule-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rule-configurations/:id} : Updates an existing ruleConfiguration.
     *
     * @param id the id of the ruleConfigurationDTO to save.
     * @param ruleConfigurationDTO the ruleConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the ruleConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ruleConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rule-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<RuleConfigurationDTO> updateRuleConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RuleConfigurationDTO ruleConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RuleConfiguration : {}, {}", id, ruleConfigurationDTO);
        if (ruleConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruleConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruleConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RuleConfigurationDTO result = ruleConfigurationService.save(ruleConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rule-configurations/:id} : Partial updates given fields of an existing ruleConfiguration, field will ignore if it is null
     *
     * @param id the id of the ruleConfigurationDTO to save.
     * @param ruleConfigurationDTO the ruleConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the ruleConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ruleConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ruleConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rule-configurations/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_UPDATE + "\")")
    public ResponseEntity<RuleConfigurationDTO> partialUpdateRuleConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RuleConfigurationDTO ruleConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RuleConfiguration partially : {}, {}", id, ruleConfigurationDTO);
        if (ruleConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ruleConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ruleConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RuleConfigurationDTO> result = ruleConfigurationService.partialUpdate(ruleConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ruleConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rule-configurations} : get all the ruleConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruleConfigurations in body.
     */
    @GetMapping("/rule-configurations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public List<RuleConfigurationDTO> getAllRuleConfigurations() {
        log.debug("REST request to get all RuleConfigurations");
        return ruleConfigurationService.findAll();
    }

    /**
     * {@code GET  /rule-configurations/:id} : get the "id" ruleConfiguration.
     *
     * @param id the id of the ruleConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rule-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_READ + "\")")
    public ResponseEntity<RuleConfigurationDTO> getRuleConfiguration(@PathVariable Long id) {
        log.debug("REST request to get RuleConfiguration : {}", id);
        Optional<RuleConfigurationDTO> ruleConfigurationDTO = ruleConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ruleConfigurationDTO);
    }

    /**
     * {@code DELETE  /rule-configurations/:id} : delete the "id" ruleConfiguration.
     *
     * @param id the id of the ruleConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rule-configurations/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.PERMISSION_DELETE + "\")")
    public ResponseEntity<Void> deleteRuleConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete RuleConfiguration : {}", id);
        ruleConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
