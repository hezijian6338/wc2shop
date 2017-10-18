package com.bootdo.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.TCartDao;
import com.bootdo.shop.domain.TCartDO;
import com.bootdo.shop.service.TCartService;



@Service
public class TCartServiceImpl implements TCartService {
	@Autowired
	private TCartDao tCartDao;
	
	@Override
	public TCartDO get(Long id){
		return tCartDao.get(id);
	}
	
	@Override
	public List<TCartDO> list(Map<String, Object> map){
		return tCartDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return tCartDao.count(map);
	}
	
	@Override
	public int save(TCartDO tCart){
		return tCartDao.save(tCart);
	}
	
	@Override
	public int update(TCartDO tCart){
		return tCartDao.update(tCart);
	}
	
	@Override
	public int remove(Long id){
		return tCartDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return tCartDao.batchRemove(ids);
	}

	@Override
	public TCartDO selectOne(Map<String, Object> params) {
		List<TCartDO> list = tCartDao.list(params);
		if (list!=null && list.size()>0){
			return  list.get(0);
		}
		return null;
	}

	@Override
	public Object selectOwnCart(Long id) {
		return null;
	}

}
