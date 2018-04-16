package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class GetUserListService {
    
    public List<User> getAllUsers() {
        EntityManager em = EMF.createEntityManager();
        try {
            em.getTransaction().begin();
            // 타입 파라미터가 User인 TypeQuery 생성
            TypedQuery<User> query = em.createQuery(
                    "select u from User u order by u.name" // User 객체의 name 속성을 이용 정렬.
                    , User.class);
            List<User> result = query.getResultList(); // JPQL을 실행한 결과를 List로 반환
            em.getTransaction().commit();
            return result;
        }
        catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
        finally {
            em.close();
        }
    }
}
