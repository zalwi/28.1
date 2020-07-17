package pl.zalwi.service;

import org.springframework.stereotype.Service;
import pl.zalwi.data.Category;
import pl.zalwi.data.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Task> findAll() {
        TypedQuery<Task> selectQuery = entityManager.createQuery("SELECT t FROM Task t", Task.class);
        return selectQuery.getResultList();
    }

    @Transactional
    public void save(Task task) {
        entityManager.persist(task);
    }

    public Optional<Task> findOneById(Long id) {
        Task movie = entityManager.find(Task.class, id);
        return Optional.ofNullable(movie);
    }

    public List<Task> findAllByCategory(Category category) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.category=?1", Task.class);
        query.setParameter(1, category);
        return query.getResultList();
    }

    public List<Task> findAllByStatus(Boolean isFinished) {
        TypedQuery<Task> query = entityManager.createQuery("SELECT t FROM Task t WHERE t.finished=?1", Task.class);
        query.setParameter(1, isFinished);
        return query.getResultList();
    }
}
