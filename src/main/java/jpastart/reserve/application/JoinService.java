package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;

public class JoinService {
    
    public void join(User user) {
        EntityManager em = EMF.createEntityManager(); // 팩토리에서 EntityManager 생성
        em.getTransaction().begin();
    
        try {
            User found = em.find(User.class, user.getEmail()); // @Id 어노테이션 설정된 필드(primary key) 이용 User 객체를 찾는다.
                                                               // 값이 없으면 null 리턴
            System.out.println(found);
            if(found != null) {
                throw new DuplicateEmailException();
            }
            em.persist(user); // user 객체 저장
            em.getTransaction().commit(); // 처리내용 DB 반영.
        }
        catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        finally {
            em.close();
        }
        
    }
}
