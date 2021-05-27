package com.knits.coreplatform.service;

import com.knits.coreplatform.service.dto.DeviceDTO;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link com.knits.coreplatform.domain.Device}.
 */
public interface DeviceService {
    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDTO save(DeviceDTO deviceDTO);

    /**
     * Partially updates a device.
     *
     * @param deviceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeviceDTO> partialUpdate(DeviceDTO deviceDTO);

    /**
     * Get all the devices.
     *
     * @return the list of entities.
     */
    List<DeviceDTO> findAll();

    /**
     * Get the "id" device.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOne(Long id);

    /**
     * Delete the "id" device.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Method to save data from Excel into database
     *
     * @param file uploaded excel file with device information.
     * */
    void save(MultipartFile file);

    /**
     * Method that gets info from database and generates Excel file to be donwloaded on client side.
     * */
    public ByteArrayInputStream load();
}
