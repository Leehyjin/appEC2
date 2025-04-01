package org.zerock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.Board;

@Mapper
public interface BoardMapper {
    Long insert(Board board);
}
