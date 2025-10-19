package ru.mirea.vinokurovazo.domain.usecases;

import ru.mirea.vinokurovazo.domain.repository.CatRepository;
import ru.mirea.vinokurovazo.domain.model.Cat;

public class ManageCatUseCase {
    private final CatRepository catRepository;

    public ManageCatUseCase(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public void execute(Cat cat) {
        catRepository.addCat(cat);
    }
}