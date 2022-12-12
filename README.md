# passionPay 서버 리팩토링
passionPay 프로젝트의 서버 코드를 리팩토링 하는 프로젝트입니다.

- 원본 URL : https://github.com/passionPay/server

## passionPay 프로젝트 소개
중, 고등학생들을 위한 스터디 플래너 앱 개발을 위한 프로젝트입니다.

### 앱 소개

1. 메인 페이지
<img width="262" alt="스크린샷 2022-12-12 오후 10 17 03" src="https://user-images.githubusercontent.com/62409503/207054738-fd3b62b2-d415-457c-8f13-df7e014b59d5.png">

2. 오늘의 플래너
<img width="224" alt="스크린샷 2022-12-12 오후 10 25 39" src="https://user-images.githubusercontent.com/62409503/207056357-d9f6a8af-3f35-492b-91c7-d10ed6d1afb3.png">

3. 스터디 그룹
<img width="246" alt="스크린샷 2022-12-12 오후 10 26 18" src="https://user-images.githubusercontent.com/62409503/207056424-dd308091-1225-4918-bc52-ef4616efa539.png">


### 기능 상세 설명
1. 지정한 과목별로 태스크 생성이 가능하며, 태스크 수행 시간에 따라 커스터마이징이 가능한 타임스탬프가 생성됩니다.
2. 소속 학교별로 커뮤니티를 형성하여 정보 공유가 가능합니다.
3. 친구들과 함께 스터디 그룹을 형성하여 그룹 미션을 진행할 수 있고, 이에 대한 인증이 가능합니다.

### 진행 기간
2022.02.03 ~ 2022.02.28

### 참여 인원
FE 2명, BE 2명

### 기술 스택
- Java
- Sprint Boot
- JPA

## 리팩토링 진행 사항
이슈 생성을 통해 세부적인 진행 상황을 관리하고 있습니다.

### DB 스키마
<img width="878" alt="스크린샷 2022-12-12 오후 9 58 47" src="https://user-images.githubusercontent.com/62409503/207051272-8611faba-0310-48b4-9fef-8e63212faa16.png">

### 클래스 다이어그램
