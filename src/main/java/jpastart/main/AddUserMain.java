package jpastart.main;

import jpastart.reserve.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class AddUserMain {
    
    public static void main(String[] args) {
        // EntityManager 생성용 팩토리 생성. 영속 단위별로 EntityManagerFactory를 생성한다.
        // 어플리케이션 별로 한번만 생성하면 되므로, 어플리케이션 초기화 과정에서 생성하면 된다.
        // persistence.xml에 지정한 persistence-unit의 name을 파라미터로 입력받는다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpastart");
        
        
        // 영속 컨텍스트와 엔티티를 관리하는 EntityManager 생성. DB연동을 처리하는 기능을 한다.
        EntityManager entityManager = emf.createEntityManager();
        // 트랜잭션 얻어오기.
        EntityTransaction transaction = entityManager.getTransaction();
        
        try{
            transaction.begin(); // 트랜잭션 시작. 트랜잭션을 시작해야만 데이터 추가 및 변경이 가능하다.
            User user = new User("user@user.com", "user", new Date());
            entityManager.persist(user); // 영속 컨텍스트에 객체 추가. 트랜잭션 커밋 시에 DB에 저장.
            transaction.commit(); // 트랜잭션 커밋
        }
        catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        finally {
            entityManager.close(); // 커넥션 반환
        }
        
        emf.close(); // 어플리케이션 종료시 팩토리를 종료한다.
    }
    
}
