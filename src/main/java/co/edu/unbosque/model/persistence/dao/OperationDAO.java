package co.edu.unbosque.model.persistence.dao;

import java.util.List;

public interface OperationDAO  <D,E>{


    public boolean add(D objeto);
    public boolean remove(String Id);
    public boolean update(D objeto, String id);
    public D get(String id);
    public E find(String id);
    public List<D> getAll();
    void load();
}
