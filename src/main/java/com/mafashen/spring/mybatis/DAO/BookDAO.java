package com.mafashen.spring.mybatis.DAO;

import com.mafashen.spring.mybatis.entity.BookDO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookDAO {

	List<BookDO> queryAll();

	void updatePrice();
}
