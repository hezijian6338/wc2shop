package com.bootdo.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.shop.dao.AddressDao;
import com.bootdo.shop.domain.AddressDO;
import com.bootdo.shop.service.AddressService;



@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressDao addressDao;
	
	@Override
	public AddressDO get(Long id){
		return addressDao.get(id);
	}
	
	@Override
	public List<AddressDO> list(Map<String, Object> map){
		return addressDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return addressDao.count(map);
	}
	
	@Override
	public int save(AddressDO address){
		return addressDao.save(address);
	}
	
	@Override
	public int update(AddressDO address){
		return addressDao.update(address);
	}
	
	@Override
	public int remove(Long id){
		return addressDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return addressDao.batchRemove(ids);
	}
	
}
