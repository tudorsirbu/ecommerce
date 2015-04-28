package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.Volume;

/**
 * This class abstracts data access methods relating to the Journal into a single file
 */
public class JournalDao {

	/**
	 * Returns all the volumes and their respective editions in the database
	 * @return Returns a list of volume objects
	 */
	public List<Volume> getAllVolumesWithEditions() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Volume vol join fetch vol.editions edi");
		@SuppressWarnings("unchecked")
		List<Volume> results = query.list();
		session.getTransaction().commit();
		return results;
	}

	/**
	 * Gets the single journal entry from the database
	 * @returns The journal object
	 */
	public Journal getJournal() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Journal");
		Journal journal = (Journal) query.uniqueResult();
		session.getTransaction().commit();
		return journal;
	}
	
	/**
	 * Updates the journal in the database with the journal passed
	 * @param journal
	 * @throws InvalidModelException
	 */
	public void updateJournal(Journal journal) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		journal.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Journal set title = :title, academic_aims = :academicAims where id = 1");
		query.setParameter("title", journal.getTitle());
		query.setParameter("academicAims", journal.getAcademicAims());
		query.executeUpdate();
		session.getTransaction().commit();
	}

}