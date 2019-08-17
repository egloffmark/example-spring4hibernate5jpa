package org.megloff.example.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.megloff.example.spring.model.Car;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CarDAO {
	
		@PersistenceContext
	   private EntityManager em;

	   public void save(Car car) {
	      em.persist(car);
	   }
	   
	   public Car getCarsById(Long id) {
		      Car car = em.find(Car.class,id);
		      return car;
	   }
	   
	   public List<Car> getCarByModel(String model) {
		   CriteriaBuilder cb = em.getCriteriaBuilder();
		   CriteriaQuery<Car> cq = cb.createQuery(Car.class);
		   Root<Car> root = cq.from(Car.class);
		   cq.where(cb.equal(root.get("Model"), model));
		   return em.createQuery(cq).getResultList();
	   }
	   
	   public List<Car> getCarByManufacturer(String manufacturer) {
		   CriteriaBuilder cb = em.getCriteriaBuilder();
		   CriteriaQuery<Car> cq = cb.createQuery(Car.class);
		   Root<Car> root = cq.from(Car.class);
		   cq.where(cb.equal(root.get("Manufacturer"), manufacturer));
		   return em.createQuery(cq).getResultList();
	   }
	   
	   public long getCount() {
		   CriteriaBuilder qb = em.getCriteriaBuilder();
		   CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		   cq.select(qb.count(cq.from(Car.class)));
		   return em.createQuery(cq).getSingleResult();
	   }

	   public List<Car> getAllCars() {
	      CriteriaQuery<Car> criteriaQuery = em.getCriteriaBuilder().createQuery(Car.class);
	      @SuppressWarnings("unused")
	      Root<Car> root = criteriaQuery.from(Car.class);
	      return em.createQuery(criteriaQuery).getResultList();
	   }
	   
	   public void delete(Car car) {
		   car = em.contains(car) ? car : em.merge(car);
		   em.remove(car);
	   }

}
