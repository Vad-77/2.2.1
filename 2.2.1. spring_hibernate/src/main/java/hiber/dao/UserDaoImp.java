package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser (String model,int series) {
      User user = sessionFactory.getCurrentSession()
              .createQuery("FROM User user WHERE user.car.model = :modelParam AND user.car.series = :seriesParam", User.class)
//              .createQuery("FROM User user INNER JOIN user.car WHERE user.car.model = :modelParam AND user.car.series = :seriesParam", User.class)
              .setParameter("modelParam", model)
              .setParameter("seriesParam", series)
              .uniqueResult();
      return user;
   }
}
