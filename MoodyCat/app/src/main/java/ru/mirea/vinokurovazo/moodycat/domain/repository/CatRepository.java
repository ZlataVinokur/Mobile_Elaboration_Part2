package ru.mirea.vinokurovazo.moodycat.domain.repository;

import java.util.List;

import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;

public interface CatRepository {
    List<Cat> getCats();

    Cat getCatById(int id);

    boolean addCat(Cat Cat);
}