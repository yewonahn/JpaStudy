package helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class jpaMain {
    public static void main(String[] args) {
        // persistence.xml 에서 persistence-unit name="hello" 로 지정한 이름
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 애플리케이션 로딩 시점에 하나만 만들어야 함


        // db 에 저장하는 등의 트랜잭션 단위의 작업 (ex. 고객이 장바구니에 담음) 을 할 때 마다
        // 즉, db 커넥션 얻어서 쿼리를 날리고 종료되는 하나의 일관적인 단위를 할 때 마다 entityManager 만들어줘야 함
        EntityManager em = emf.createEntityManager();
        // 실제 동작하는 코드 작성

        // JPA 에서 데이터를 변경하는 모든 작업은 트랜잭션 안에서 작업해야 함
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try {
            /*
            Member member = new Member();
            member.setId(1L);
            member.setName("hello");
            em.persist(member);
             */

            // 조회
            /*
            Member findMember = em.find(Member.class, 1L);
            System.out.println(findMember.getId());
            System.out.println(findMember.getName());
             */

            // 삭제는 em.remove(); 이용

            // 수정
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("change");
            // 자바 객체에서 값만 바꿨는데 db 에 반영
            // -> JPA 를 통해서 entity 를 가져오면, JPA 에 의해서 관리되므로
            // 트랜잭션 커밋 되는 시점에 변경사항 있는지 다 체크함
            // 변경 사항 있는 경우 트랜잭션 직전에 update 쿼리 날리고 커밋

            tx.commit();
        } catch (Exception e) {
            // 문제 생기면 rollback
            tx.rollback();
        } finally {
            // 작업 끝나면 em 닫아주기
            em.close();
        }

        // 전체 애플리케이션이 완전히 끝나면 닫아줘야 함
        emf.close();
    }
}
