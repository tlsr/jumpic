package ua.com.cyberneophyte.jumpic.service;

public interface ICRUDService<E> {
    void delete(E e);
    void save(E e);
}
