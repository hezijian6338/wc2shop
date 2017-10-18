package com.bootdo.shop.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author zscat
 * @email 951449465@qq.com
 * @date 2017-10-15 15:07:36
 */
public class TGoodSorderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private Long goodsid;
	//
	private Long orderid;
	//
	private String img;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}
	/**
	 * 获取：
	 */
	public Long getGoodsid() {
		return goodsid;
	}
	/**
	 * 设置：
	 */
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	/**
	 * 获取：
	 */
	public Long getOrderid() {
		return orderid;
	}
	/**
	 * 设置：
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：
	 */
	public String getImg() {
		return img;
	}
}
