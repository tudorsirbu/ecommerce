package com.sheffield.ecommerce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.SessionFactoryUtil;
import com.sheffield.ecommerce.models.Volume;
import com.sheffield.ecommerce.models.Edition;

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
		Query query = session.createQuery("from Volume");
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

	/**
	 * Fetch a specific volume from the database by its id
	 * @param id
	 * @return The requested volume
	 */
	public Volume getVolumeById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Volume v where v.volumeId = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Volume volume = (Volume) query.uniqueResult();
		return volume;
	}

	/**
	 * Add a new volume to the database
	 * @param volume
	 * @throws InvalidModelException
	 */
	public void addNewVolume(Volume volume) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		volume.setJournal(getJournal());
		volume.setVolumeNumber(numberOfCurrentVolumes() + 1);
		volume.validateModel();
		session.beginTransaction();
		session.save(volume);
		session.getTransaction().commit();
	}

	/**
	 * Update a volume in the database
	 * @param volume
	 * @throws InvalidModelException
	 */
	public void updateVolume(Volume volume) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		volume.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Volume set publication_date = :date where volume_id = :id");
		query.setParameter("id", volume.getVolumeId());
		query.setParameter("date", volume.getPublicationDate());
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	/**
	 * Count number number of volumes currently in the database
	 * @return number of volumes
	 */
	public int numberOfCurrentVolumes() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Volume");
		query.setMaxResults(1);
		return ((Long)query.uniqueResult()).intValue();
	}
	
	/**
	 * Count number number of editions currently in the database
	 * @return number of volumes
	 */
	public int numberOfCurrentEditions() {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Edition");
		query.setMaxResults(1);
		return ((Long)query.uniqueResult()).intValue();
	}

	/**
	 * Fetch a specific edition from the database by its id
	 * @param id
	 * @return The requested edition
	 */
	public Edition getEditionById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Edition e where e.editionId = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Edition edition = (Edition) query.uniqueResult();
		return edition;
	}

	/**
	 * Add a new edition to the database
	 * @param edition
	 * @throws InvalidModelException
	 */
	public void addNewEdition(Edition edition) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		edition.setEditionNumber(numberOfCurrentEditions() + 1);
		edition.validateModel();
		session.beginTransaction();
		session.save(edition);
		session.getTransaction().commit();
	}

	/**
	 * Update a edition in the database
	 * @param edition
	 * @throws InvalidModelException
	 */
	public void updateEdition(Edition edition) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession();
		edition.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Edition set publication_date = :date where edition_id = :id");
		query.setParameter("id", edition.getEditionId());
		query.setParameter("date", edition.getPublicationDate());
		query.executeUpdate();
		session.getTransaction().commit();
	}

}