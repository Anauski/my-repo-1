package net.ent.etrs.librairie.model.daos;



import java.io.Serializable;

public interface BaseDao<T, ID extends Serializable> {
    
    <S extends T> S save(S entity);
    
    T findOne(ID id);
    
    Iterable<T> findAll();
    
    void delete(T entity);
    
    void delete(ID id);
    
    boolean exists(ID id);
    
    long count();
}
