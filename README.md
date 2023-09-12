# 지능기전공학과 사물함 예약 시스템
### [ime-locker.shop](http://ime-locker.shop)

---

## 목차

1. [개요](#개요)
2. [기술 스택](#기술-스택)
3. [API 문서](#api-문서)
4. [버전](#버전)

## 개요

학생부회장으로 일하며 학과행사중 사물함 배부를 진행하는데 있어 다음과 같은 불편함이 존재

1. 기존의 정해진 시간이 되면 카톡방에 원하는 사물함의 번호를 입력하고, 해당 번호를 학생회 인원이 직접 엑셀에 실시간으로 옮겨적는 방식은 한계(공정성, 인력한계)가 존재
2. 누구든 입장할수 있는 오픈채팅방으로는 지능기전소속의 재학생 신원확인이 어려움

로그인과 예약형식으로 진행함으로써 사물함의 공정한 배부와 나아가 마이페이지 구성을 통한 매 행사마다 학우들의 정보가 저장된 엑셀을 직접 확인하던 불편함 해소 기대

## 기술 스택

- Java11, Spring Boot,Spring Data Jpa
- MySQL, Redis
- AWS EC2, Docker, Github Action

## API 문서

[http://ime-locker.shop:8082/swagger-ui/index.html](http://ime-locker.shop:8082/swagger-ui/index.html)

## 버전

<details>
<summary> 1.0.0 ver</summary>
<div markdown="1">

### 개발 기간
23.06.27 ~ 23.08.14

### 기능 및 수정내용 

- 로그인 (세종대학교 학사정보시스템)
- 사물함 예약 기능
- 마이페이지
- 사물함 시간 및 사용자 정보 수정 기능(관리자용)

### ERD

![image](https://github.com/jaepyo-Lee/lockmanager/assets/74135929/1c84cfd1-ebaa-4e0a-8e98-f72bb09d7ac7)

### 아키텍쳐

![image](https://github.com/jaepyo-Lee/lockmanager/assets/74135929/002b296d-b21a-4fa8-88bc-31e3da2aa4c3)

### 느낀점

- 예약 시스템인만큼 동시성에 대해 많은 고민을 하였다. DB에 직접 Lock을 걸어 동시성을 제어하려했지만 데드락 관리가 어려워, Redisson를 이용하는 방식으로 진행하였다.Redisson을 이용시 서로 다른 메서드에서 같은 사물함에 대해 같은 key를 이용하여 락을 처리해줘야하기에 가능하면 한가지 메서드를 재사용하고자 하였고, 추후 DB락 및 Redisson에 대해 더 공부하여 리팩토링 할 예정이다.
- 사물함 예약 뿐만 아닌 추후 더 많은 기능과 트래픽을 기대하고 프로젝트를 시작했기 때문에 기존 사용하던 MVC패턴이 아닌 유연성과 확장성이 좋은 헥사고날 패턴을 이용하여 진행하였다. 현재 패턴이 완벽하다 볼수 없다 생각하여 추후 리팩토링 할 예정이다.

</div>

</details>
