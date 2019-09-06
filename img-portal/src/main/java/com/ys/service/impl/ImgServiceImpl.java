package com.ys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ys.commons.utils.JsonUtils;
import com.ys.dao.JedisClusterDao;
import com.ys.mapper.ImgMapper;
import com.ys.pojo.Img;
import com.ys.pojo.ImgExample;
import com.ys.service.ImgService;
@Service
public class ImgServiceImpl implements ImgService {
	@Resource
	private ImgMapper imgMapper;
	@Resource
	private JedisClusterDao jedisClusterDaoImpl;
	@Value("${bigpic.key}")
	private String key;
	
	@Override
	public List<Img> selAll() {
		//判断redis中是否存在指定key
		if(jedisClusterDaoImpl.exists("bigimg")){
			//如果存在取出，取出后判断是否为null或""
			String value = jedisClusterDaoImpl.get("bigimg");
			if(value!=null&&!value.equals("")){
				return JsonUtils.jsonToList(value, Img.class);
			}
			
		}
		
		
			//如果不存在则从mysql中取出
			
			//查询体
			ImgExample example = new ImgExample();
			//查询全部
			List<Img> list = imgMapper.selectByExample(example);
			
			//把数据缓存到Redis中
			jedisClusterDaoImpl.set("bigimg", JsonUtils.objectToJson(list));
			return list;
		
	}
	
	
}
