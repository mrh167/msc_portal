package com.msc.fix.lisa.domain.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msc.fix.lisa.domain.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统字典类型表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-09-29 10:21:39
 */
@Data
@TableName("sys_dict_type")
@EqualsAndHashCode(callSuper = false)
public class SysDictType extends BasePo {
	/**
	 * 主键
	 */
	@JSONField(serializeUsing= ToStringSerializer.class)
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态（字典 0正常 1停用 2删除）
	 */
	private Integer status;
}
