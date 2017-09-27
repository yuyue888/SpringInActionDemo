package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAsync
@PropertySource(value = {"classpath:/redis.properties"})
public class RedisConfig {
    @Value("${redis.host}")
    private String server;

    @Value("${redis.port}")
    private String port;

    private RedisServer[] redisServer;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(300);
        config.setMaxTotal(60000);
        config.setTestOnBorrow(true);
        return config;
    }

    @Bean
    public JedisPool jedisPool() {
        String[] servers = server.split(",");
        String[] ports = port.split(",");
        JedisPool config = new JedisPool(this.jedisPoolConfig(), servers[0], Integer.parseInt(ports[0]));
        return config;
    }

    @Bean
    public ShardedJedisPool shardedJedisPool() {
        ShardedJedisPool pool;
        String[] servers = server.split(",");
        String[] ports = port.split(",");
        int len = servers.length <= ports.length ? servers.length : ports.length;
        List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(len);
        for (int start = 0; start < len; start++) {
            JedisShardInfo tmp = new JedisShardInfo(servers[start], Integer.parseInt(ports[start]));
            jdsInfoList.add(tmp);
        }
        pool = new ShardedJedisPool(this.jedisPoolConfig(), jdsInfoList);
        return pool;
    }

    protected class RedisServer {
        private String server;
        private int port;

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
