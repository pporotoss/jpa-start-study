package jpastart.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    
    private static EntityManagerFactory emf;
    
    public static void init() {
        // psersistence.xml 파일에 persistence-unit 태그에 넣어준 name으로 팩토리 생성
        emf = Persistence.createEntityManagerFactory("jpastart");
    }
    
    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void close() {
        emf.close();
    }
    
}
