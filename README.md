# 🏠 스마트경로당 (Smart Senior Center)

> 6-70대 노년층을 위한 직관적인 약속 관리 서비스 플랫폼

디지털 환경에 익숙하지 않은 노년층 사용자들이 쉽고 편리하게 약속을 관리할 수 있도록 설계된 웹 서비스입니다.

<br/>

## 📋 목차
- [프로젝트 소개](#-프로젝트-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [데이터베이스 설계](#-데이터베이스-설계)
- [실행 방법](#-실행-방법)

<br/>

## 🎯 프로젝트 소개

### 개발 배경
디지털 기기 사용에 어려움을 겪는 노년층을 위해, 간편하고 직관적인 UI로 약속 일정을 관리할 수 있는 서비스를 제공합니다.

### 주요 특징
- 🎨 **간편하고 직관적인 UI**: 대형 버튼과 명확한 색상 구분
- 📱 **쉬운 약속 관리**: 복잡한 절차 없이 클릭 몇 번으로 약속 생성
- 🧠 **치매 예방 효과**: 정기적인 일정 관리를 통한 인지 능력 향상
- 🤖 **AI 챗봇 지원**: ChatGPT 기반 질의응답 서비스
- 🎮 **미니게임**: 사용자 참여를 유도하는 재미 요소

### 개발 기간
2023년 2월 ~ 2024년 1월 (12개월)

### 팀 구성
6인 팀 (백엔드 3명, 프론트엔드 3명)

<br/>

## ✨ 주요 기능

### 1️⃣ 회원 관리
- 회원가입 및 로그인
- JWT 기반 인증 시스템
- 마이페이지 (프로필 조회 및 수정)
- 비밀번호, 전화번호 변경 기능

### 2️⃣ 약속 관리
- **약속 생성**: 날짜, 시간, 장소 선택하여 약속 만들기
- **약속 참여**: 생성된 약속에 참여 신청
- **인원 제한**: 최대 인원 설정 및 현재 인원 관리
- **약속 상태 관리**
  - 🟢 확정 (최대 인원 충족 시)
  - 🔴 파토 (약속 삭제 시)
- **약속 삭제**: 생성자만 삭제 권한 보유
- **멤버 조회**: 확정된 약속의 참여자 전화번호 조회

### 3️⃣ 알림 시스템
- 약속 확정/파토 알림
- 일정 리마인더

### 4️⃣ 부가 기능
- **AI 챗봇**: OpenAI ChatGPT API 기반 질의응답
  - 주변 병원 위치, 영업시간 등 정보 제공
- **미니게임**
  - 랜덤 숫자 맞추기
  - 거꾸로 가위바위보

<br/>

## 🛠 기술 스택

### Backend
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

### Frontend
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

### Tools & External API
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)

<br/>

## 🏗 시스템 아키텍처

```mermaid
graph TB
    subgraph Client["Client Layer"]
        UI[React Frontend<br/>Figma UI Design]
    end
    
    subgraph Server["Application Server"]
        API[Spring Boot<br/>RESTful API]
        Auth[JWT Authentication]
        Controller[Controllers]
        Service[Service Layer]
    end
    
    subgraph External["External Services"]
        ChatGPT[OpenAI ChatGPT API<br/>챗봇 서비스]
    end
    
    subgraph Database["Database Layer"]
        MySQL[(MySQL Database)]
        subgraph Tables["Tables"]
            T1[프로필 Profile]
            T2[약속 Appointment]
            T3[알림 Notification]
            T4[참여자 Participant]
            T5[스케줄 Schedule]
        end
    end
    
    UI -->|HTTP Request| API
    API -->|JWT Token| Auth
    Auth -->|Validate| UI
    API --> Controller
    Controller --> Service
    Service -->|Query/Update| MySQL
    MySQL --> Tables
    T1 -.->|FK| T2
    T1 -.->|FK| T3
    T1 -.->|FK| T4
    Service -->|API Call| ChatGPT
    ChatGPT -->|Response| Service
    
    style Client fill:#e1f5ff
    style Server fill:#fff4e1
    style Database fill:#f0f0f0
    style External fill:#ffe1f5
```

<br/>

## 🗄 데이터베이스 설계

### 주요 테이블 구조

#### 📌 Profile (프로필)
```sql
- user_id (PK)
- username
- password
- phone_number
- created_at
```

#### 📌 Appointment (약속)
```sql
- appointment_id (PK)
- creator_id (FK → Profile.user_id)
- title
- location
- date_time
- max_participants (최대 인원)
- current_participants (현재 인원)
- status (모집중/확정/파토)
```

#### 📌 Participant (참여자)
```sql
- participant_id (PK)
- appointment_id (FK → Appointment.appointment_id)
- user_id (FK → Profile.user_id)
- joined_at
```

#### 📌 Notification (알림)
```sql
- notification_id (PK)
- user_id (FK → Profile.user_id)
- message
- is_read
- created_at
```

#### 📌 Schedule (스케줄)
```sql
- schedule_id (PK)
- user_id (FK → Profile.user_id)
- appointment_id (FK → Appointment.appointment_id)
- reminder_time
```

### 테이블 관계
- `Appointment`, `Notification`, `Participant` 테이블은 `Profile` 테이블의 `user_id`를 외래키로 참조
- `Participant` 테이블은 `Appointment` 테이블의 `appointment_id`를 외래키로 참조

<br/>

## 🚀 실행 방법

### 필수 요구사항
- Java 11 이상
- MySQL 8.0 이상
- Node.js 14 이상

### 1. Backend 실행

```bash
# 프로젝트 클론
git clone https://github.com/your-username/smart-senior-center.git
cd smart-senior-center/backend

# application.properties 설정
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_senior
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_jwt_secret_key
openai.api.key=your_openai_api_key

# 프로젝트 빌드 및 실행
./gradlew build
./gradlew bootRun
```

### 2. Frontend 실행

```bash
cd frontend

# 의존성 설치
npm install

# 환경 변수 설정 (.env 파일 생성)
REACT_APP_API_URL=http://localhost:8080

# 개발 서버 실행
npm start
```

### 3. 접속
브라우저에서 `http://localhost:3000` 접속

<br/>

## 📱 주요 화면

### 홈 화면
- 서비스 소개 (Introduce 메뉴)
- 약속 잡기 버튼
- 게임 메뉴

### 약속 관리
- 약속 생성 폼 (날짜, 시간, 장소 선택)
- 약속 리스트 (참여 가능한 약속 조회)
- 참여자 조회 (확정된 약속의 멤버 전화번호)

### 마이페이지
- 프로필 정보 조회
- 내가 만든 약속
- 참여한 약속
- 프로필 수정 (비밀번호, 전화번호)

### 챗봇
- 주변 정보 질의응답
- 병원, 약국 등 위치 및 영업시간 조회

### 미니게임
- 랜덤 숫자 맞추기 게임
- 거꾸로 가위바위보 게임

<br/>

## 🎓 배운 점

### Backend 개발 경험
- JWT를 활용한 인증 시스템 구현
- RESTful API 설계 및 구현
- MySQL 데이터베이스 설계 및 관계 관리
- 외래키를 활용한 효율적인 데이터 조회
- Postman을 통한 API 테스트 및 문서화

### 협업 경험
- 프론트엔드 팀과의 API 명세 협의
- Git을 활용한 버전 관리
- 코드 리뷰 및 피드백

<br/>

## 📝 라이선스

This project is licensed under the MIT License.

<br/>

---

<div align="center">

⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!

**Made with ❤️ by 육하원칙 팀**

</div>
