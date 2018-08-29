package com.spider.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安居客房源信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjkHouseDO {

	private Long id;

	/**
	 * 详细地址
	 */
	public String address;

	/**
	 * 小区
	 */
	public String plot;

	/**
	 * 面积
	 */
	public float area;

	/**
	 * 总价
	 */
	public float totalPrice;

	/**
	 * 单价 / m²
	 */
	public float unitPrice;

	/**
	 * 图片地址
	 */
	public String imgUrl;

	/**
	 * 居室
	 */
	public String roomHall;

	/**
	 * 详情地址
	 */
	private String detailUrl;

	private Date createTime;

	private Date updateTime;

}
