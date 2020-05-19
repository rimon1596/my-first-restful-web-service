package com.appsdeveloperblog.app.ws.io.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.HibernateUtils;
import com.appsdeveloperblog.app.ws.io.entity.*;

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
	public void closeConnection() {
		if (session != null) {
			session.close();
		}

	}

}
