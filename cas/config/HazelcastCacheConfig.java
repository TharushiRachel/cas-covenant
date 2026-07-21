package com.itechro.cas.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.itechro.cas.commons.constants.CachingConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

/**
 * @author: chamara
 */
@Configuration
@PropertySource(name = "apps.properties", value = "classpath:apps.properties")
@EnableCaching
public class HazelcastCacheConfig {

    @Value("${apps.cache.hazelcast.tcpip.enabled}")
    private String tcpIpConfigEnabled;

    @Value("${apps.cache.hazelcast.nodes}")
    private String nodesInfo;

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName(CachingConstants.CACHE_INSTANCE_NAME);

        if (Boolean.valueOf(tcpIpConfigEnabled)) {
            JoinConfig joinConfig = config.getNetworkConfig().getJoin();
            joinConfig.getMulticastConfig().setEnabled(false);
            String[] nodes = StringUtils.split(nodesInfo, ',');
            joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Arrays.asList(nodes));
        }

        MapConfig usersCache = this.createCacheConfig(60 * 15);
        config.getMapConfigs().put(CachingConstants.USERS_CACHE_KEY, usersCache);

        MapConfig systemParameterCache = this.createCacheConfig(60 * 15);
        config.getMapConfigs().put(CachingConstants.SYSTEM_PARAMS_KEY, systemParameterCache);

        return config;
    }

    private MapConfig createCacheConfig(Integer ttl) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setTimeToLiveSeconds(ttl);
        mapConfig.setEvictionPolicy(EvictionPolicy.LFU);

        return mapConfig;
    }
}
