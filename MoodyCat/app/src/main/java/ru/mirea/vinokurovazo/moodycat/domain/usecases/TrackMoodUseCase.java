package ru.mirea.vinokurovazo.moodycat.domain.usecases;

import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;
import ru.mirea.vinokurovazo.moodycat.domain.repository.CatRepository;

public class TrackMoodUseCase {
    private final CatRepository repository;

    public TrackMoodUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public Cat execute(int id) {
        return repository.getCatById(id);
    }
}