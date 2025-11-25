package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.repository.CatRepository;
import ru.mirea.vinokurovazo.domain.model.Cat;

import java.util.List;

public class GetHistoryUseCase {
    private final CatRepository catRepository;

    public GetHistoryUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public List<Cat> execute() {
        return catRepository.getCats();
    }

    public CatRepository getRepository() {
        return catRepository;
    }

}