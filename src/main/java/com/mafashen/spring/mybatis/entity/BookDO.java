package com.mafashen.spring.mybatis.entity;

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
public class BookDO {

	private Long bookId;

	private String bookName;

	private Long price;

	private Date publishDate;

	private String description;

	private String pictureLink;
}
