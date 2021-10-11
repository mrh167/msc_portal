package com.msc.fix.lisa.domain.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统字典值表
 * 
 * @author maruihua
 * @email 1003534413@qq.com
 * @date 2021-09-29 10:21:39
 */
@Data
@TableName("sys_dict_data")
public class SysDictData implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 字典类型id
	 */
	private Long typeId;
	/**
	 * 值
	 */
	private String value;
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
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createUser;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 更新人
	 */
	private Long updateUser;
	/**
	 * 逻辑删除1是0否
	 */
	private Integer yn;

}
