package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.RateDomainModel;
import util.HibernateUtil;

public class RateDAL {

	public static double getRate(int creditScore) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		RateDomainModel rateGet = null;
		try {
			tx = session.beginTransaction();
			String search = "from RateDomainModel where mincreditscore <=" + creditScore;
			Query query = session.createQuery(search);
			List<?> list = query.list();

			rateGet = (RateDomainModel) list.get(0);

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				RateDomainModel rate = (RateDomainModel) iterator.next();
				if (rate.getInterestRate() <= rateGet.getInterestRate()) {
					rateGet = rate;
				}
			}
			tx.commit();
		}

		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rateGet.getInterestRate();
	}

}