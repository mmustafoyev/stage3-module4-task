package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractDBRepository<T extends BaseEntity<K>,K> implements BaseRepository<T,K> {
    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> entityClass;
    private final Class<K> idClass;
    abstract void update(T prevState, T nextState);

    public AbstractDBRepository() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) type.getActualTypeArguments()[0];
        this.idClass = (Class<K>) type.getActualTypeArguments()[1];
    }


    @Override
    public List<T> readAll() {
        TypedQuery<T> query = entityManager.createQuery(" SELECT e FROM " +
                entityClass.getSimpleName() + " e ", entityClass);
        return query.getResultList();
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return readById(entity.getId()).map(existingEntity -> {
            update(existingEntity, entity);
            T updatedEntity = entityManager.merge(existingEntity);
            //flush is neededfor auditable enitities to get actual @LastModifiesDate field
            entityManager.flush();
            return updatedEntity;
                }
        ).orElse(null);
    }

    @Override
    public boolean deleteById(K id) {
        if(id == null) return false;
        T entityRef = getReference(id);
        entityManager.remove(entityRef);
        return false;
    }

    @Override
    public boolean existsById(K id) {
        EntityType<T> entityType = (EntityType<T>) entityManager.getMetamodel().entity(entityClass);
        String idFieldName = entityType.getId(idClass).getName();
        Query query = entityManager.createQuery(
                "SELECT COUNT(*) FROM " + entityClass.getSimpleName() + " e WHERE " + idFieldName + "= ?1"
        ).setParameter("1", id);
        Long count = (Long) query.getSingleResult();
        return count > 0;
    }

    @Override
    public T getReference(K id) {
        return entityManager.getReference(this.entityClass, id);
    }
}
