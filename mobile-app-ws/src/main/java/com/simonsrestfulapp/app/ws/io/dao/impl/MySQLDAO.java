package com.simonsrestfulapp.app.ws.io.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.simonsrestfulapp.app.ws.io.dao.DAO;
import com.simonsrestfulapp.app.ws.io.entity.UserEntity;
import com.simonsrestfulapp.app.ws.shared.dto.UserDTO;
import com.simonsrestfulapp.app.ws.utils.HibernateUtils;

public class MySQLDAO implements DAO {

	Session session;

	@Override
	public void openConnection() {

		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		session = sessionFactory.openSession();

	}

	@Override
	public UserDTO getUserByUserName(String userName) {

		UserDTO userDto = null;

		CriteriaBuilder cb = session.getCriteriaBuilder();

		// Create criteria against a particular persistent class
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

		// Query roots always reference entity
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("email"), userName));

		// fetch single result
		Query<UserEntity> query = session.createQuery(criteria);
		List<UserEntity> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0) {

			UserEntity userEntity = resultList.get(0);
			userDto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDto);

		}

		return userDto;
	}
	
	@Override
	public UserDTO getUser(String id) {
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
		
		Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
		criteria.select(profileRoot);
		criteria.where(cb.equal(profileRoot.get("userId"),id));
        
        UserEntity userEntity = session.createQuery(criteria).getSingleResult();
        
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
		
		return userDTO;
	}


	public UserDTO saveUser(UserDTO user) {

		UserDTO returnValue = null;

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		session.beginTransaction();
		session.save(userEntity);
		session.getTransaction().commit();

		returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public void closeConnection() {
		if (session != null) {
			session.close();
		}

	}

	@Override
	public void updateUserProfile(UserDTO userProfile) {

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userProfile, userEntity);
		
		session.beginTransaction();
		session.update(userEntity);
		session.getTransaction().commit();
		
	}

	@Override
	public List<UserDTO> getUsers(int start, int limit) {

		List<UserDTO> users = new ArrayList<UserDTO>();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
		
		//Query root always references entities
		Root<UserEntity> userRoot = criteria.from(UserEntity.class);
		criteria.select(userRoot);
		
		//fetch results from start to the limit
		List<UserEntity> searchResults = session.createQuery(criteria)
				.setFirstResult(start)
				.setMaxResults(limit)
				.getResultList();
		
		for (UserEntity result : searchResults) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(result, userDto);
			users.add(userDto);	
		}
		
		return users;
	}
}
