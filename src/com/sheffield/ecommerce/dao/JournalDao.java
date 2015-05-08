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
	public static List<Volume> getAllVolumesWithEditions() {
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
	public static Journal getJournal() {
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
	public static void updateJournal(Journal journal) throws InvalidModelException {
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
	public static Volume getVolumeById(int id) {
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
	public static void addNewVolume(Volume volume) throws InvalidModelException {
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
	public static void updateVolume(Volume volume) throws InvalidModelException {
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
	public static int numberOfCurrentVolumes() {
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
	public static int numberOfCurrentEditions() {
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
	public static Edition getEditionById(int id) {
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
	public static void addNewEdition(Edition edition) throws InvalidModelException {
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
	public static void updateEdition(Edition edition) throws InvalidModelException {
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
	public static List<Article> getArticlesForEdition(int editionId) {
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
	
	/**
	 * Marks an article as published by assigning it to an edition
	 * @param articleId
	 * @param editionId
	 * @throws InvalidModelException
	 */
	public static void assignArticleToEdition(int articleId, int editionId) throws InvalidModelException {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("update Article set edition_id = :editionId where id = :articleId");
		query.setParameter("editionId", editionId);
		query.setParameter("articleId", articleId);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Returns a list of articles that are suitable for publication.
	 * 1. The author must have reviewed at least three articles (excluding those needed for articles already published)
	 * 2. At least three people must have reviewed the article (6 if revised)
	 * 3. The reviews must contain at least 2 champions
	 * @return
	 */
	public static List<Article> getApprovedArticles() {
		List<Article> unpublishedArticles = ArticleDao.getAllUnpublishedArticles();
		List<Article> approvedArticles = new ArrayList<Article>();
		for (Article article : unpublishedArticles) {
			int publishedCount = UserDao.countUsersPublishedArticles(article.getAuthor().getId()); //Count the number of published articles the author owns
			//Check the author has made enough reviews for this article to be published
			if (ReviewDao.countReviewsForUser(article.getAuthor()) - (3*publishedCount) >= 3) {
				//Check that the article has enough reviews
				if ((article.getFileNameRevision1() == null && article.getReviews().size() == 3) || (article.getFileNameRevision1() != null && article.getReviews().size() == 6)) {
					
					//Get the three most recent (we want to ignore reviews made from earlier revisions)
					List<Review> reviews = ReviewDao.getThreeMostRecentReviews(article.getId());
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