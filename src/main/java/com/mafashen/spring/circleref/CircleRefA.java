package com.mafashen.spring.circleref;

import lombok.Getter;
import lombok.Setter;


/**
 * 测试循环依赖
 * @author mafashen
 * created on 2018/11/8.
 */
@Getter
@Setter
public class CircleRefA {

	CircleRefB refB;
}
