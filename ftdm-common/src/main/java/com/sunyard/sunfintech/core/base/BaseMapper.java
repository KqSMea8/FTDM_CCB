/**
 * 
 */
package com.sunyard.sunfintech.core.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * DAO基础处理类
 * @author heroy
 * @version 20170401
 */
public interface BaseMapper<T extends BaseEntity> extends com.baomidou.mybatisplus.mapper.BaseMapper<T> {

	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);

}
