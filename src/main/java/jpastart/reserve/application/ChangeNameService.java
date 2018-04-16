package jpastart.reserve.application;

import jpastart.jpa.EMF;
import jpastart.reserve.model.User;

import javax.persistence.EntityManager;

public class ChangeNameService {
    
    public void changeName(String email, String newName) {
        EntityManager em = EMF.createEntityManager(); // EntityManagerFactory로부터 EntityManager 생성
        try {
            em.getTransaction().begin(); // 트랜잭션 시작.
            User user = em.find(User.class, email); // 입력한 primary키로 조회.
            if(user == null) throw new UserNotFoundException();
            user.changeName(newName); // user 객체의 내용 변경.
            em.getTransaction().commit(); // 영속 컨텍스트에 있는 객체중에서, 변경된 객체의 내용 변경.
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
