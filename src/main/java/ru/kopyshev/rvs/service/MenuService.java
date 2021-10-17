package ru.kopyshev.rvs.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.kopyshev.rvs.exception.NotFoundException;
import ru.kopyshev.rvs.model.MenuItem;
import ru.kopyshev.rvs.repository.CrudMenuRepository;
import ru.kopyshev.rvs.util.CollectionUtil;
import ru.kopyshev.rvs.util.DateTimeUtil;
import ru.kopyshev.rvs.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class MenuService {

    private final CrudMenuRepository repository;

    public MenuService(CrudMenuRepository repository) {
        this.repository = repository;
    }

    public MenuItem create(MenuItem item) {
        ValidationUtil.checkNew(item);
        return repository.save(item);
    }

    public MenuItem get(int id) {
        return repository.get(id).orElseThrow(() -> new NotFoundException("Not found MenuItem with id = " + id));
    }

    public void update(MenuItem item, int id) {
        ValidationUtil.assureIdConsistent(item, id);
        checkExisting(id);
        repository.save(item);
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public List<MenuItem> createAll(List<MenuItem> items) {
        Assert.notNull(items, "Creating menu item must not be null");
        items.forEach(ValidationUtil::checkNew);
        return repository.saveAll(items);
    }

    public List<MenuItem> getAll() {
        return getAll(DateTimeUtil.getMinDate(), DateTimeUtil.getMaxDate());
    }

    public List<MenuItem> getAll(LocalDate start, LocalDate end) {
        LocalDate startDate = Objects.isNull(start) ? DateTimeUtil.getMinDate() : start;
        LocalDate endDate = Objects.isNull(end) ? DateTimeUtil.getMaxDate() : end;
        var menuItems = repository.getAll(startDate, endDate);
        return CollectionUtil.getImmutableListIfNull(menuItems);
    }

    public List<MenuItem> getAll(int restaurantId) {
        var menuItems = repository.getAll(restaurantId);
        return CollectionUtil.getImmutableListIfNull(menuItems);
    }

    public List<MenuItem> getAll(int restaurantId, LocalDate start, LocalDate end) {
        var menuItems = repository.getAll(restaurantId, start, end);
        return CollectionUtil.getImmutableListIfNull(menuItems);
    }

    public List<MenuItem> updateAllByReplacing(List<MenuItem> items) {
        items.forEach(item -> Assert.notNull(item, "Updating items must not be null"));
        repository.deleteAll(items);
        return repository.saveAll(items);
    }

    public void deleteAtDate(int restaurant_id, LocalDate date) {
        Assert.notNull(date, "The date must not be null");
        var affectedRows = repository.delete(restaurant_id, date);
        var message = String.format("Nothing is found for restaurant %d at %s", restaurant_id, date);
        ValidationUtil.checkNotFound(affectedRows != 0, message);
    }

    private void checkExisting(int id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("The updating MenuItem does not exist");
        }
    }
}