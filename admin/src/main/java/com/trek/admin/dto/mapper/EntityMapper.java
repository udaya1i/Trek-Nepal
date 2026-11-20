package com.trek.admin.dto.mapper;


import com.trek.core.dto.command.Command;
import com.trek.core.entity.BaseEntity;

/**
 *
 * @param <C> Command
 * @param <E> Entity
 */
public interface EntityMapper<C extends Command, E extends BaseEntity> {

    E toEntity(C command);


}
