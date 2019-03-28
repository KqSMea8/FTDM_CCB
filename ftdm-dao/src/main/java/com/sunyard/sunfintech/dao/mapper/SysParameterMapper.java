package com.sunyard.sunfintech.dao.mapper;

import com.sunyard.sunfintech.dao.entity.SysParameter;
import com.sunyard.sunfintech.dao.entity.SysParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysParameterMapper {
    /**
     *
     * @mbggenerated 2017-06-01
     */
    int countByExample(SysParameterExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByExample(SysParameterExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insert(SysParameter record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int insertSelective(SysParameter record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    List<SysParameter> selectByExample(SysParameterExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    SysParameter selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExampleSelective(@Param("record") SysParameter record, @Param("example") SysParameterExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByExample(@Param("record") SysParameter record, @Param("example") SysParameterExample example);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKeySelective(SysParameter record);

    /**
     *
     * @mbggenerated 2017-06-01
     */
    int updateByPrimaryKey(SysParameter record);
}