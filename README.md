# youtube-backend
 youtube clone project
# youtube-backend

[Spring boot setting](#Spring-boot-setting)
[Encoding 처리](#Spring-boot-한글-encoding-처리-하는-방법)
[JPA](#JPA-(Java Persistence API))

# Spring boot setting

## Spring boot 한글 encoding 처리 하는 방법

- src/main/resources/application.properties 안에 server.servlet.encoding.force=true 추가해 주면 됨

## JPA (Java Persistence API)

- 자바 언어로 DB에 명령을 내리는 도구
- 데이터를 객체 지향적으로 관리

### Entity(엔티티) (== model.vo)

- 자바 객체를 DB가 이해할 수 있도록 만드는 역할
- 이를 기반으로 테이블 생성이 되기도 함
- @Entity : 어노테이션이 붙은 클래스를 기반으로 DB테이블 생성가능. (테이블 존재하지 않으면..)
- @Id : 엔티티의 대표 값(Primary key)
- @GeneratedValue : 대표값을 자동으로 생성 (시퀀스와 관련)
- @Column : 엔티티의 대표 값 이외의 값들

### Repository(레포지토리)

- 엔티티가 DB속 테이블에 저장 및 관리할 수 있게 도와주는 인터페이스
- 대표적인 인터페이스 : JpaRepository
- CRUD 만 사용하는 인터페이스 : CrudRepository