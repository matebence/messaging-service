package com.blesk.messagingservice.DAO;

import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAO<T> {

    T save(T t);

    Boolean update(Class<T> c, String column, String id, Update update);

    Boolean delete(Class<T> c, String column, String id);

    T get(Class<T> c, String column, String id);

    List<T> getAll(Class<T> c, int pageNumber, int pageSize);

    List<T> getJoinValuesByColumn(Class<T> c, List<String> ids, String columName);

    Map<String, Object> searchBy(Class c, HashMap<String, HashMap<String, String>> criterias);
}