package ng.com.dpros.customermanager.config;

import java.time.Duration;

import ng.com.dpros.customermanager.domain.Services;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ng.com.dpros.customermanager.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ng.com.dpros.customermanager.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ng.com.dpros.customermanager.domain.User.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Authority.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.User.class.getName() + ".authorities");
            createCache(cm, ng.com.dpros.customermanager.domain.Profile.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.ServiceCategory.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Address.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Review.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Payment.class.getName());
            createCache(cm, Services.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Hardware.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Training.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Software.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Profile.class.getName() + ".services");
            createCache(cm, ng.com.dpros.customermanager.domain.Hardware.class.getName() + ".reviews");
            createCache(cm, ng.com.dpros.customermanager.domain.Hardware.class.getName() + ".payments");
            createCache(cm, ng.com.dpros.customermanager.domain.Software.class.getName() + ".payments");
            createCache(cm, ng.com.dpros.customermanager.domain.Software.class.getName() + ".reviews");
            createCache(cm, ng.com.dpros.customermanager.domain.Training.class.getName() + ".reviews");
            createCache(cm, ng.com.dpros.customermanager.domain.Training.class.getName() + ".payments");
            createCache(cm, ng.com.dpros.customermanager.domain.Services.class.getName());
            createCache(cm, ng.com.dpros.customermanager.domain.Profile.class.getName() + ".hardware");
            createCache(cm, ng.com.dpros.customermanager.domain.Profile.class.getName() + ".software");
            createCache(cm, ng.com.dpros.customermanager.domain.Profile.class.getName() + ".trainings");
            createCache(cm, ng.com.dpros.customermanager.domain.Trial.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
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
