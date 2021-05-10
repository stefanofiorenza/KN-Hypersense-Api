package com.knits.coreplatform.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.knits.coreplatform.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.knits.coreplatform.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.knits.coreplatform.domain.User.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Authority.class.getName());
            createCache(cm, com.knits.coreplatform.domain.User.class.getName() + ".authorities");
            createCache(cm, com.knits.coreplatform.domain.Organisation.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Organisation.class.getName() + ".factories");
            createCache(cm, com.knits.coreplatform.domain.Organisation.class.getName() + ".userData");
            createCache(cm, com.knits.coreplatform.domain.UserData.class.getName());
            createCache(cm, com.knits.coreplatform.domain.UserData.class.getName() + ".deviceConfigurations");
            createCache(cm, com.knits.coreplatform.domain.DeviceConfiguration.class.getName());
            createCache(cm, com.knits.coreplatform.domain.ConfigurationData.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Application.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Application.class.getName() + ".things");
            createCache(cm, com.knits.coreplatform.domain.ThingCategory.class.getName());
            createCache(cm, com.knits.coreplatform.domain.ThingCategory.class.getName() + ".things");
            createCache(cm, com.knits.coreplatform.domain.Thing.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Thing.class.getName() + ".devices");
            createCache(cm, com.knits.coreplatform.domain.Thing.class.getName() + ".states");
            createCache(cm, com.knits.coreplatform.domain.DeviceGroup.class.getName());
            createCache(cm, com.knits.coreplatform.domain.DeviceGroup.class.getName() + ".devices");
            createCache(cm, com.knits.coreplatform.domain.AlertConfiguration.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Telemetry.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Supplier.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName() + ".rules");
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName() + ".alertMessages");
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName() + ".metaData");
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName() + ".deviceConfigurations");
            createCache(cm, com.knits.coreplatform.domain.Device.class.getName() + ".statuses");
            createCache(cm, com.knits.coreplatform.domain.DeviceModel.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Location.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Rule.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Rule.class.getName() + ".ruleConfigurations");
            createCache(cm, com.knits.coreplatform.domain.RuleConfiguration.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Metadata.class.getName());
            createCache(cm, com.knits.coreplatform.domain.AlertMessage.class.getName());
            createCache(cm, com.knits.coreplatform.domain.State.class.getName());
            createCache(cm, com.knits.coreplatform.domain.Status.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
