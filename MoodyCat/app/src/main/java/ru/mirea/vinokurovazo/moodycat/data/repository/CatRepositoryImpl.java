package ru.mirea.vinokurovazo.moodycat.data.repository;

import java.util.List;

import ru.mirea.vinokurovazo.moodycat.data.test.TestDataSource;
import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;
import ru.mirea.vinokurovazo.moodycat.domain.repository.CatRepository;

public class CatRepositoryImpl implements CatRepository {
    @Override
    public List<Cat> getCats() {
        return TestDataSource.Cats;
    }

    @Override
    public Cat getCatById(int id) {
        for (Cat p : TestDataSource.Cats) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public boolean addCat(Cat Cat) {
        return TestDataSource.Cats.add(Cat);
    }
}
