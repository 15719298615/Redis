package com.ys.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ys.dao.JedisClusterDao;

import redis.clients.jedis.JedisCluster;
@Repository
public class JedisClusterDaoImpl implements JedisClusterDao {

	@Resource
	private JedisCluster jedisClients;
	
	
	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisClients.exists(key);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisClients.get(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisClients.set(key, value);
	}
	
	
	
	
	
}
