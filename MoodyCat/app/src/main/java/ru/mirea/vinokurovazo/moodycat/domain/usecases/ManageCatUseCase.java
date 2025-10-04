package ru.mirea.vinokurovazo.moodycat.domain.usecases;

import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;
import ru.mirea.vinokurovazo.moodycat.domain.repository.CatRepository;

public class ManageCatUseCase {
    private final CatRepository repository;

    public ManageCatUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Cat Cat) {
        return repository.addCat(Cat);
    }
}