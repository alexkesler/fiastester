package org.kesler.fiastester.dao.impl;

import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.apache.log4j.Logger;

import org.hibernate.criterion.Restrictions;
import org.kesler.fiastester.dao.FIASRecordDAO;
import org.kesler.fiastester.dao.DAOState;
import org.kesler.fiastester.dao.DAOListener;

import org.kesler.fiastester.logic.FIASRecord;
import org.kesler.fiastester.util.HibernateUtil;

public class FIASRecordDAOImpl implements FIASRecordDAO {
	protected final Logger log;

	private List<DAOListener> listeners = new ArrayList<DAOListener>();


	public FIASRecordDAOImpl() {

		log = Logger.getLogger("FIASRecordDAO");
	}

	@Override
	public void addDAOListener(DAOListener listener) {
		listeners.add(listener);
	}

	public Long addRecord(FIASRecord record) {
		Long id = null;

		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			log.debug("Begin to write item");
			notifyListeners(DAOState.WRITING);
			tx = session.beginTransaction();
			session.save(record);
			session.flush();
			session.clear();
			tx.commit();
			log.info("Adding item complete");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			if (tx != null) tx.rollback();
			log.error("Error writing item", he);
			he.printStackTrace();
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		id = record.getId();

		return id;
	}

    public void addRecords(List<FIASRecord> records) {

        notifyListeners(DAOState.CONNECTING);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {

            log.debug("Begin to write item");
            notifyListeners(DAOState.WRITING);
            tx = session.beginTransaction();
            for (int i=0; i < records.size(); i++) {
                session.save(records.get(i));
                if (i % 20 == 0) {
                    session.flush();
                    session.clear();
                }

            }
            tx.commit();
            log.info("Adding item complete");
            notifyListeners(DAOState.READY);

        } catch (HibernateException he) {

            if (tx != null) tx.rollback();
            log.error("Error writing item", he);
            notifyListeners(DAOState.ERROR);

        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }


	public List<FIASRecord> getAllRecords() {
		List<FIASRecord> list = new ArrayList<FIASRecord>();
		notifyListeners(DAOState.CONNECTING);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			log.debug("Reading records");
			notifyListeners(DAOState.READING);
			list = session.createCriteria(FIASRecord.class).list();
			log.info("Read " + list.size() + " records");
			notifyListeners(DAOState.READY);
		} catch (HibernateException he) {
			log.error("Error reading items", he);
			notifyListeners(DAOState.ERROR);
		} finally {
			if (session!=null && session.isOpen()) {
				session.close();				
			}
		}

		return list;
	}


    public List<FIASRecord> getChildRecords(String parentGUID) {
        List<FIASRecord> list = new ArrayList<FIASRecord>();
        notifyListeners(DAOState.CONNECTING);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            log.debug("Reading records");
            notifyListeners(DAOState.READING);
            list = session.createCriteria(FIASRecord.class)
                    .add(Restrictions.eq("parentGUID",parentGUID))
                    .list();
            log.info("Read " + list.size() + " records");
            notifyListeners(DAOState.READY);
        } catch (HibernateException he) {
            log.error("Error reading items", he);
            notifyListeners(DAOState.ERROR);
        } finally {
            if (session!=null && session.isOpen()) {
                session.close();
            }
        }

        return list;
    }


	protected void notifyListeners(DAOState state) {
		for (DAOListener listener: listeners) {
			listener.daoStateChanged(state);
		}
	}

}