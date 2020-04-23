package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void createUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public BlogUser getUserByNickname(String nick) throws DAOException {
		var em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		try {
			user = (BlogUser) em.createNamedQuery("BlogUser.getByNick").setParameter("n", nick).getSingleResult();
		} catch (NoResultException ignorable) {
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getUsers() throws DAOException {
		var em = JPAEMProvider.getEntityManager();
		return em.createQuery("SELECT user FROM BlogUser as user").getResultList();
	}

	@Override
	public void createBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);
	}
	
	@Override
	public void updateBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().merge(entry);
	}

	@Override
	public void createComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}

}