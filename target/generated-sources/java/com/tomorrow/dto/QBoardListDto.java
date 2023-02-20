package com.tomorrow.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.tomorrow.dto.QBoardListDto is a Querydsl Projection type for BoardListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBoardListDto extends ConstructorExpression<BoardListDto> {

    private static final long serialVersionUID = -963774458L;

    public QBoardListDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> boardTitle, com.querydsl.core.types.Expression<String> createdBy, com.querydsl.core.types.Expression<Integer> viewCount) {
        super(BoardListDto.class, new Class<?>[]{long.class, String.class, String.class, int.class}, id, boardTitle, createdBy, viewCount);
    }

}

