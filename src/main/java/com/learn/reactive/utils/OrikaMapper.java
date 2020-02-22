package com.learn.reactive.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaMapper<S,T> {
    private Class<T> target;
    private S source;

    public OrikaMapper(S source,Class<T> target){
        this.target = target;
        this.source = source;
    }

    public T map(){
        MapperFactory mapper = new DefaultMapperFactory.Builder().build();
        mapper.classMap(this.source.getClass(),target).byDefault().register();
        MapperFacade facade = mapper.getMapperFacade();
        return facade.map(this.source,target);
    }
}
