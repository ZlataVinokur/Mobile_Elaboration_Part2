package ru.mirea.vinokurovazo.moodycat.domain.usecases;

import java.util.List;
import ru.mirea.vinokurovazo.moodycat.domain.model.Cat;
import ru.mirea.vinokurovazo.moodycat.domain.repository.CatRepository;
public class GetHistoryUseCase {
    private final CatRepository repository;

    public GetHistoryUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public List<Cat> execute() {
        return repository.getCats();
    }
}
