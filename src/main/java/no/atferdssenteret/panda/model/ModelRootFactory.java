package no.atferdssenteret.panda.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import no.atferdssenteret.panda.util.JPATransactor;

//public class ModelRootFactory<T extends Model> {
////	private static Map<Class<T>, Root<T>> modelMap = new HashMap<Class<T>, Root<T>>();
//	@SuppressWarnings("rawtypes")
//	private static Map<Class, Root> modelMap = new HashMap<Class, Root>();
//	
//	private Root<T> createModelRoot(Class<T> modelClass) {
//		CriteriaBuilder criteriaBuilder = JPATransactor.getInstance().entityManager().getCriteriaBuilder();
//		CriteriaQuery<? extends Model> criteriaQuery = criteriaBuilder.createQuery(modelClass);
//		return criteriaQuery.from(modelClass);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public <T> Root<T> root(Class<T> modelClass) {
//		if (!modelMap.containsKey(modelClass)) {
//			modelMap.put(modelClass, createModelRoot(modelClass));
//		}
//		return modelMap.get(modelClass);
//	}
//}

public class ModelRootFactory {
	@SuppressWarnings("rawtypes")
	private static Map<Class, Root> modelMap = new HashMap<Class, Root>();
	
	private <T extends Model> Root<T> createModelRoot(Class<T> modelClass) {
//		CriteriaQuery<? extends Model> criteriaQuery = JPATransactor.getInstance().criteriaBuilder().createQuery(modelClass);
		CriteriaQuery<? extends Model> criteriaQuery = JPATransactor.getInstance().entityManager().getCriteriaBuilder().createQuery(modelClass);
		return criteriaQuery.from(modelClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Model> Root<T> root(Class<T> modelClass) {
		if (!modelMap.containsKey(modelClass)) {
			modelMap.put(modelClass, createModelRoot(modelClass));
		}
		return modelMap.get(modelClass);
	}
}