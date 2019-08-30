package com.mapper;

import com.pojo.Secorder;
import com.pojo.SecorderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecorderMapper {
    int countByExample(SecorderExample example);

    int deleteByExample(SecorderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Secorder record);

    int insertSelective(Secorder record);

    List<Secorder> selectByExample(SecorderExample example);

    Secorder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Secorder record, @Param("example") SecorderExample example);

    int updateByExample(@Param("record") Secorder record, @Param("example") SecorderExample example);

    int updateByPrimaryKeySelective(Secorder record);

    int updateByPrimaryKey(Secorder record);
}