package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.repository.CatRepository;
import ru.mirea.vinokurovazo.domain.model.Cat;

public class TrackMoodUseCase {
    private final CatRepository catRepository;

    public TrackMoodUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat execute(int catId) {
        return catRepository.getCatById(catId);
    }
}