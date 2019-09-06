package com.ys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ys.commons.utils.JsonUtils;
import com.ys.mapper.ImgMapper;
import com.ys.pojo.Img;
import com.ys.pojo.ImgExample;
import com.ys.service.ImgService;

import redis.clients.jedis.JedisCluster;

@Service
public class ImgServiceImpl implements ImgService {
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private JedisCluster jedisClusterDanImpl;
	@Value("${bigpic.key}")
	private String key;
	@Override
	public List<Img> selAll() {
		return imgMapper.selectByExample(new ImgExample());
	}

	@Override
	public int delById(int id) {
		//1.删除mysql中的数据
		int index = imgMapper.deleteByPrimaryKey(id);
		if(index>0){
			//2.判断redis中是否有缓存数据
			if(jedisClusterDanImpl.exists(key)){
				String value = jedisClusterDanImpl.get(key);
				//3.如果有缓存数据，修改缓存数据
				if(value!=null&&!value.equals("")){
					List<Img> list = JsonUtils.jsonToList(value, Img.class);
					Img imgExists = null;
					for (Img img : list) {
						if((int)img.getId()==id){
							imgExists = img;
						}
					}
					if(imgExists!=null){
						list.remove(imgExists);
					}
					jedisClusterDanImpl.set(key, JsonUtils.objectToJson(list));
				}
			}
			
		}
		
		return index;
			
	}
	
	
}
