package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Department;
import com.example.demo.models.Order;
import com.example.demo.models.Order.OrderStatus;
import com.example.demo.models.Person;
import com.example.demo.models.Section;
import com.example.demo.models.SectionPK;
import com.example.demo.models.WareHouse;

@Service
public class UpdateDemoData extends BasicEntityManager {

	@Transactional(rollbackOn = { Exception.class })
	public int save() {
		try {
			Person person01 = new Person(1, "eton");
			Person person02 = new Person(2, "joe");
			Person person03 = new Person(3, "john");
			Person person04 = new Person(4, "bill");
			Person person05 = new Person(5, "alice");

			// ---department
			Department department01 = new Department("RD");
			Department department02 = new Department("IT");

			// ---section
			SectionPK sectionPK01 = new SectionPK("RD", "section01");
			SectionPK sectionPK02 = new SectionPK("IT", "section02");
			SectionPK sectionPK03 = new SectionPK("IT", "section03");
			Section section01 = new Section(sectionPK01, person01);
			Section section02 = new Section(sectionPK02, person02);
			Section section03 = new Section(sectionPK03, person05);

			List<Section> sections = new ArrayList<Section>();
			sections.add(section01);
			sections.add(section02);
			sections.add(section03);

			department01.setSections(sections);
			department01.setManager(person01);
			department02.setManager(person04);

			entityManager.persist(person01);
			entityManager.persist(person02);
			entityManager.persist(person03);
			entityManager.persist(person04);
			entityManager.persist(person05);

			entityManager.persist(department01);
			entityManager.persist(department02);
			entityManager.persist(section01);
			entityManager.persist(section02);
			entityManager.persist(section03);

			Order order01 = new Order("order01", 520, OrderStatus.FG);
			Order order03 = new Order("order03", 520, OrderStatus.WIP);
			Order order05 = new Order("order05", 520, OrderStatus.DEDICATED);
			Order order02 = new Order("order02", 520, OrderStatus.FG);
			Order order04 = new Order("order04", 520, OrderStatus.WIP);
			Order order06 = new Order("order06", 520, OrderStatus.DEDICATED);

			order02.setOwnSections(sections);
			List<Order> orders01 = new ArrayList<Order>();
			orders01.add(order01);
			orders01.add(order03);
			orders01.add(order05);
			orders01.add(order05);

			List<Order> orders02 = new ArrayList<Order>();
			orders02.add(order02);
			orders02.add(order04);
			orders02.add(order06);

			WareHouse wareHouse01 = new WareHouse("WareHouses01");
			WareHouse wareHouse02 = new WareHouse("WareHouses02");
			WareHouse wareHouse03 = new WareHouse("WareHouses03");
			WareHouse wareHouse04 = new WareHouse("WareHouses04");
			WareHouse wareHouse05 = new WareHouse("WareHouses05");
			WareHouse wareHouse06 = new WareHouse("WareHouses06");
			wareHouse06.setOrders(orders01);

			entityManager.persist(wareHouse01);
			entityManager.persist(wareHouse02);
			entityManager.persist(wareHouse03);
			entityManager.persist(wareHouse04);
			entityManager.persist(wareHouse05);
			entityManager.persist(wareHouse06);

			entityManager.persist(order01);
			entityManager.persist(order02);
			entityManager.persist(order03);
			entityManager.persist(order04);
			entityManager.persist(order05);
			entityManager.persist(order06);

			section01.setOwnOrders(orders01);
			section02.setOwnOrders(orders02);
			entityManager.merge(section01);
			entityManager.merge(section02);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Transactional(rollbackOn = { Exception.class })
	public int deleteAll() {
		try {
			TypedQuery<Person> personQuery =  entityManager.createQuery("SELECT person FROM Person person",Person.class);
			List<Person>  persons = personQuery.getResultList();
			
			TypedQuery<Department> departmentQuery =  entityManager.createQuery("SELECT department FROM Department department",Department.class);
			List<Department> departments = departmentQuery.getResultList();
			
			TypedQuery<Section> sectionQuery =  entityManager.createQuery("SELECT section FROM Section section",Section.class);
			List<Section>  sections = sectionQuery.getResultList();
			
			TypedQuery<Order> orderQuery =  entityManager.createQuery("SELECT order FROM Order order",Order.class);
			List<Order>  orders = orderQuery.getResultList();
			
			TypedQuery<WareHouse> wareHouseQuery =  entityManager.createQuery("SELECT wareHouse FROM WareHouse wareHouse",WareHouse.class);
			List<WareHouse>  wareHouses = wareHouseQuery.getResultList();
			
			for(WareHouse wareHouse:wareHouses) {
				entityManager.remove(wareHouse);
			}
			for(Order order:orders) {
				entityManager.remove(order);
			}
			for(Section section:sections) {
				entityManager.remove(section);
			}
			for(Department department:departments) {
				entityManager.remove(department);
			}
			for(Person person:persons) {
				entityManager.remove(person);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}