package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.example.demo.models.Person;
import com.example.demo.models.Section;

@Service
public class ExampleItems extends BasicEntityManager {
	public List<Person> retriveManagersByDepartment(String departmentName) {
		List<Person> persons = new ArrayList<Person>();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(null);
			Root<Section> rootSection = criteriaQuery.from(Section.class);
			
			Join<Section,Person> joinPerson = rootSection.join("manager", JoinType.LEFT);
			joinPerson.on(criteriaBuilder.equal(rootSection.get("manager").get("employeeId"),joinPerson.get("employeeId")));

			Predicate p1 = criteriaBuilder.equal(rootSection.get("department").get("name"), departmentName);
			criteriaQuery.where(p1);
			criteriaQuery.select(joinPerson);
			TypedQuery<Person> typedQuery = entityManager.createQuery(criteriaQuery);
			persons = typedQuery.getResultList();
			return persons;

		} catch (Exception ex) {
			ex.getCause();
			ex.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public int sumOrderQuantityByDepartment(String departmentName) {
		int count = 0;
		try {
			Query totalQuantityQuery = entityManager.createNativeQuery(
					  "select DISTINCT "
					  + " traing_order.quantity,"
					  + "  section_own_orders.own_sections_department_name,"
					  + "  section_own_orders.own_orders_id"
					  + "  from section_own_orders"
					  + "  Right join traing_order on section_own_orders.own_orders_id = traing_order.id"
					  + "  where own_orders_id in"
					  + "   (SELECT own_orders_id FROM section_own_orders"
					  + "     where section_own_orders.own_sections_department_name= :departmentName)");
			totalQuantityQuery.setParameter("departmentName", departmentName);
			List<Object[]> author =  (List<Object[]>)totalQuantityQuery.getResultList();
			for(Object[] obj :author) {
				count += Integer.valueOf(obj[0].toString());
			}
			return count;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
