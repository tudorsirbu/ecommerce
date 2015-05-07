package com.sheffield.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sheffield.ecommerce.exceptions.InvalidModelException;
import com.sheffield.ecommerce.models.Article;
import com.sheffield.ecommerce.models.Journal;
import com.sheffield.ecommerce.models.Review;
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
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Volume");
		@SuppressWarnings("unchecked")
		List<Volume> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}

	/**
	 * Gets the single journal entry from the database
	 * @returns The journal object
	 */
	public Journal getJournal() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Journal");
		Journal journal = (Journal) query.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return journal;
	}
	
	/**
	 * Updates the journal in the database with the journal passed
	 * @param journal
	 * @throws InvalidModelException
	 */
	public void updateJournal(Journal journal) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		journal.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Journal set title = :title, academic_aims = :academicAims where id = 1");
		query.setParameter("title", journal.getTitle());
		query.setParameter("academicAims", journal.getAcademicAims());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Fetch a specific volume from the database by its id
	 * @param id
	 * @return The requested volume
	 */
	public Volume getVolumeById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Volume v where v.volumeId = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Volume volume = (Volume) query.uniqueResult();
		session.close();
		return volume;
	}

	/**
	 * Add a new volume to the database
	 * @param volume
	 * @throws InvalidModelException
	 */
	public void addNewVolume(Volume volume) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		volume.setJournal(getJournal());
		volume.setVolumeNumber(numberOfCurrentVolumes() + 1);
		volume.validateModel();
		session.beginTransaction();
		session.save(volume);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Update a volume in the database
	 * @param volume
	 * @throws InvalidModelException
	 */
	public void updateVolume(Volume volume) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		volume.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Volume set publication_date = :date where volume_id = :id");
		query.setParameter("id", volume.getVolumeId());
		query.setParameter("date", volume.getPublicationDate());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Count number number of volumes currently in the database
	 * @return number of volumes
	 */
	public int numberOfCurrentVolumes() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Volume");
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}
	
	/**
	 * Count number number of editions currently in the database
	 * @return number of volumes
	 */
	public int numberOfCurrentEditions() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("select count(*) from Edition");
		query.setMaxResults(1);
		int count = ((Long)query.uniqueResult()).intValue();
		session.close();
		return count;
	}

	/**
	 * Fetch a specific edition from the database by its id
	 * @param id
	 * @return The requested edition
	 */
	public Edition getEditionById(int id) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Edition e where e.editionId = :id");
		query.setMaxResults(1);
		query.setParameter("id", id);
		Edition edition = (Edition) query.uniqueResult();
		session.close();
		return edition;
	}

	/**
	 * Add a new edition to the database
	 * @param edition
	 * @throws InvalidModelException
	 */
	public void addNewEdition(Edition edition) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		edition.setEditionNumber(numberOfCurrentEditions() + 1);
		edition.validateModel();
		session.beginTransaction();
		session.save(edition);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Update a edition in the database
	 * @param edition
	 * @throws InvalidModelException
	 */
	public void updateEdition(Edition edition) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		edition.validateModel();
		session.beginTransaction();
		Query query = session.createQuery("update Edition set publication_date = :date where edition_id = :id");
		query.setParameter("id", edition.getEditionId());
		query.setParameter("date", edition.getPublicationDate());
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Gets all the articles for the given edition
	 * @param editionId
	 * @return A list of articles
	 */
	public List<Article> getArticlesForEdition(int editionId) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article where edition_id = :id");
		query.setParameter("id", editionId);
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}
	
	public void assignArticleToEdition(int articleId, int editionId) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("update Article set edition_id = :editionId where id = :articleId");
		query.setParameter("editionId", editionId);
		query.setParameter("articleId", articleId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	public List<Article> getApprovedArticles() {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Article where edition_id = null");
		@SuppressWarnings("unchecked")
		List<Article> results = query.list();
		session.getTransaction().commit();
		session.close();
		List<Article> approvedArticles = new ArrayList<Article>();
		for (Article article : results) {
			UserDao userDao = new UserDao();
			int publishedCount = userDao.countUsersPublishedArticles(article.getAuthor().getId()); //Count the number of published articles the author owns
			//Check the author has made enough reviews for this article to be published
			if (article.getAuthor().getReviews().size() - (3*publishedCount) > 0) {
				//Check that the article has enough reviews
				if ((article.getFileNameRevision1() == null && article.getReviews().size() == 3) || (article.getFileNameRevision1() != null && article.getReviews().size() == 6)) {
					ReviewDao reviewDao = new ReviewDao();
					//Get the three most recent (we want to ignore reviews made from earlier revisions)
					List<Review> reviews = reviewDao.getThreeMostRecentReviews(article.getId());
					int champions = 0;
					for (Review review : reviews) {
						if (review.getOverallJudgement().equals("champion")) {
							champions++;
						}
					}
					if (champions > 1) {
						approvedArticles.add(article);
					}
				}
			}
		}
		return approvedArticles;
	}
}