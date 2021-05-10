package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.TelemetryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.Telemetry}.
 */
public interface TelemetryService {
    /**
     * Save a telemetry.
     *
     * @param telemetryDTO the entity to save.
     * @return the persisted entity.
     */
    TelemetryDTO save(TelemetryDTO telemetryDTO);

    /**
     * Partially updates a telemetry.
     *
     * @param telemetryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelemetryDTO> partialUpdate(TelemetryDTO telemetryDTO);

    /**
     * Get all the telemetries.
     *
     * @return the list of entities.
     */
    List<TelemetryDTO> findAll();

    /**
     * Get the "id" telemetry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelemetryDTO> findOne(Long id);

    /**
     * Delete the "id" telemetry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
