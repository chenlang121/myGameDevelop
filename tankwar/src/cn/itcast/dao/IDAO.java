package cn.itcast.dao;

import java.util.List;
import java.util.Set;

public interface IDAO <K,V>{
    public boolean doCreate(V vo) throws Exception;
    public V findById(K id) throws Exception;
    public List<V> findAll() throws Exception;
    public List<V> findAllSplit(String column,String keyWord,Integer currentPage,Integer lineSize ) throws Exception;
    public boolean doUpdate(V vo) throws Exception;
    public boolean doRemove(Set<K> ids) throws Exception;
    public Integer getAllCount(String column,String keyWord) throws Exception;
}
