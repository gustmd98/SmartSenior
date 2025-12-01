🏠 스마트경로당 (Smart Senior Center)

6-70대 노년층을 위한 직관적인 약속 관리 서비스 플랫폼

디지털 환경에 익숙하지 않은 노년층 사용자들이 쉽고 편리하게 약속을 관리할 수 있도록 설계된 웹 서비스입니다.
📋 목차

프로젝트 소개
주요 기능
기술 스택
시스템 아키텍처
ERD
실행 방법
팀원

🎯 프로젝트 소개
개발 배경
디지털 기기 사용에 어려움을 겪는 노년층을 위해, 간편하고 직관적인 UI로 약속 일정을 관리할 수 있는 서비스를 제공합니다.
주요 특징

🎨 간편하고 직관적인 UI: 대형 버튼과 명확한 색상 구분
📱 쉬운 약속 관리: 복잡한 절차 없이 클릭 몇 번으로 약속 생성
🧠 치매 예방 효과: 정기적인 일정 관리를 통한 인지 능력 향상
🤖 AI 챗봇 지원: ChatGPT 기반 질의응답 서비스
🎮 미니게임: 사용자 참여를 유도하는 재미 요소

개발 기간
2023.02 ~ 2024.01 (12개월)
팀 구성
6인 팀 (백엔드 3명, 프론트엔드 3명)
✨ 주요 기능
1. 회원 관리

회원가입 및 로그인
JWT 기반 인증 시스템
마이페이지 (프로필 조회 및 수정)
비밀번호, 전화번호 변경 기능

2. 약속 관리

약속 생성: 날짜, 시간, 장소 선택하여 약속 만들기
약속 참여: 생성된 약속에 참여 신청
인원 제한: 최대 인원 설정 및 현재 인원 관리
약속 상태 관리

🟢 확정 (최대 인원 충족 시)
🔴 파토 (약속 삭제 시)


약속 삭제: 생성자만 삭제 권한 보유
멤버 조회: 확정된 약속의 참여자 전화번호 조회

3. 알림 시스템

약속 확정/파토 알림
일정 리마인더

4. 부가 기능

AI 챗봇: OpenAI ChatGPT API 기반 질의응답

주변 병원 위치, 영업시간 등 정보 제공


미니게임

랜덤 숫자 맞추기
거꾸로 가위바위보



🛠 기술 스택
Backend
이미지 표시
이미지 표시
이미지 표시
이미지 표시
Frontend
이미지 표시
이미지 표시
Tools
이미지 표시
이미지 표시
이미지 표시
External API
이미지 표시
🏗 시스템 아키텍처
mermaidgraph TB
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
🗄 ERD
주요 테이블 구조
Profile (프로필)

user_id (PK)
username
password
phone_number
created_at

Appointment (약속)

appointment_id (PK)
creator_id (FK → Profile.user_id)
title
location
date_time
max_participants (최대 인원)
current_participants (현재 인원)
status (모집중/확정/파토)

Participant (참여자)

participant_id (PK)
appointment_id (FK → Appointment.appointment_id)
user_id (FK → Profile.user_id)
joined_at

Notification (알림)

notification_id (PK)
user_id (FK → Profile.user_id)
message
is_read
created_at

Schedule (스케줄)

schedule_id (PK)
user_id (FK → Profile.user_id)
appointment_id (FK → Appointment.appointment_id)
reminder_time

🚀 실행 방법
필수 요구사항

Java 11 이상
MySQL 8.0 이상
Node.js 14 이상

Backend 실행
bash# 프로젝트 클론
git clone [repository-url]
cd smart-senior-center

# application.properties 설정
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_senior
spring.datasource.username=your_username
spring.datasource.password=your_password

# 프로젝트 빌드 및 실행
./gradlew build
./gradlew bootRun
Frontend 실행
bashcd frontend

# 의존성 설치
npm install

# 개발 서버 실행
npm start
OpenAI API 설정
bash# application.properties에 API 키 추가
openai.api.key=your_openai_api_key
📱 주요 화면
홈 화면

서비스 소개
약속 잡기 버튼
게임 메뉴

약속 관리

약속 생성 폼
약속 리스트
참여자 조회

마이페이지

프로필 정보
내가 만든 약속
참여한 약속

👥 팀원
역할이름담당Backend[이름]JWT 인증, 약속 관리 APIBackend[이름]회원 관리, 알림 시스템Backend[이름]챗봇 API 연동, DB 설계Frontend[이름]UI/UX 설계, 페이지 구현Frontend[이름]상태 관리, API 연동Frontend[이름]미니게임, 반응형 디자인
