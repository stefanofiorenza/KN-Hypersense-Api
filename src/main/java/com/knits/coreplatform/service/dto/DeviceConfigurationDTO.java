package com.knits.coreplatform.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.knits.coreplatform.domain.DeviceConfiguration} entity.
 */
@ApiModel(description = "Entity to keep data related to sensor connection.\n@author Vassili Moskaljov\n@version 1.0")
public class DeviceConfigurationDTO implements Serializable {

    private Long id;

    private String name;

    private String uUID;

    @Lob
    private byte[] token;

    private String tokenContentType;
    private ConfigurationDataDTO configurationData;

    private UserDataDTO userData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuUID() {
        return uUID;
    }

    public void setuUID(String uUID) {
        this.uUID = uUID;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getTokenContentType() {
        return tokenContentType;
    }

    public void setTokenContentType(String tokenContentType) {
        this.tokenContentType = tokenContentType;
    }

    public ConfigurationDataDTO getConfigurationData() {
        return configurationData;
    }

    public void setConfigurationData(ConfigurationDataDTO configurationData) {
        this.configurationData = configurationData;
    }

    public UserDataDTO getUserData() {
        return userData;
    }

    public void setUserData(UserDataDTO userData) {
        this.userData = userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceConfigurationDTO)) {
            return false;
        }

        DeviceConfigurationDTO deviceConfigurationDTO = (DeviceConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deviceConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceConfigurationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uUID='" + getuUID() + "'" +
            ", token='" + getToken() + "'" +
            ", configurationData=" + getConfigurationData() +
            ", userData=" + getUserData() +
            "}";
    }
}
