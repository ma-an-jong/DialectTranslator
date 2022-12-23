# DialectTranslator

프로젝트 명: 동네 사람들 ( 한국어 사투리 번역기 )

---

프로젝트 진행 기간 : 2022.07.01 ~ 2022.12.12

---

수행 역할: 

- 자연어 처리 모델 개발
    - KoBART를 이용한 사투리 번역 기능 구현
    - KoBERT를 이용한 Multi-Classification 구현
- API 서버 구현
    - 자연어 처리 모델의 번역 및 분류 기능을 수행하는 API 서버 구축
- 서버 어플리케이션 구현
    - https를 이용한 통신 구현
    - kafka를 이용한 통신 구현 → 최종적으로 사용 x
- 웹 서버 운영
    - React - SpringBoot 빌드를 통해 정적 웹 페이지 리소스 및 자바 아카이브 생성
    - 포트 포워딩
    - DNS 설정

---

## 기능 소개

### 사투리 번역 기능

- 기능 구조도
    
![image](https://user-images.githubusercontent.com/84767373/207541144-6f04cfa6-31cf-41a2-81d5-0e1b220133d3.png)

1. 사투리 문장을 tokenizer가 토큰화 하여 각 토큰에 인덱스를 부여한다. 
2. 부여한 인덱스를 이용하여 벡터로 변환한다. 따라서 벡터의 크기는 (batch_size, seq_len, emb_size)
3. 이 벡터를 타켓 언어를 위한 인코더의 입력으로 번역용 벡터로 변경한다.
4. 번역용 벡터와 이전 디코더의 출력을 이용하여 디코더는 번역 결과를 출력한다.

- 번역 결과
    
![image](https://user-images.githubusercontent.com/84767373/207541154-a6ab0292-fcff-4780-9b35-1b77f55e4485.png)

'무싱 거옌 고릅 디까'라는 제주어를 '뭐라고 말했어요'라는 표준어로 번역한 모습

### 지역 분류 기능

- 기능 구조도
    
![image](https://user-images.githubusercontent.com/84767373/207541182-682a49b5-a31c-4cd9-8165-f5a4c9eb09ba.png)
    

- [ ]  기능 설명

- 분류 결과

![image](https://user-images.githubusercontent.com/84767373/207541222-a58a06fb-404f-4cab-a8fe-2e32088397eb.png)

## 시스템 구조도

![image](https://user-images.githubusercontent.com/84767373/207541257-c12b4a78-f950-4668-af5e-e80c692c7c80.png)

## **프로젝트를 하면서 어려웠던 점**

- 성능 개선에 대한 어려움
    
    자연어 처리 모델을 Fine-Tuning 하여 번역을 올바르게 진행하게 만들었다. 그러나 데이터 편향적인 번역 결과와 학습되지 않은 사투리로 인한 정확하지 않은 일부 결과는 실제 사용할 수 있는 정도의 퍼포먼스를 보여주지 못하였다. 따라서 성능 개선 대안을 찾기위해 회의를 통해 네이버 사전과 같은 웹상에 존재하는 한국어 사투리 사전을 크롤링 하여 사투리 사전을 만들기로 하였고 해당하는 단어가 존재하면 사투리를 표준어로 치환하는 작업을 하기로 하였다. 그러나 한 지역에서 사투리가 표준어와 같은 글자로 사용되는 경우가 존재하여 문제가 발생하였다. 예를 들어   전라도 사투리 중 ‘참’이라는 사투리가 존재하는데 이것은 ‘근처’라는 뜻이었다. 따라서 ‘참 오늘 몇시에 만나기로 했지?’ 의 결과로 ‘근처 오늘 몇시에 만나기로 했지?’ 라는 사용자가 오해할 수 있는 결과가 생성되었다. 
    
- 데이터에 의존적인 번역 결과
    
    번역의 결과가 데이터에 너무 의존된 결과였던것 같다. 비교적 최근에 이를 해소하는 방법을 알았는데 적용해보지 못한게 너무 아쉬웠다. 
    
    링크: [https://www.youtube.com/watch?v=Y5CnKPyubTc](https://www.youtube.com/watch?v=Y5CnKPyubTc)
    
    - [ ]  영상 내용 요약하여 작성하기
- BLEU Score가 의미가 있나
    
    BLEU Score가 소스 언어와 타깃 언어가 동일해서 그런지 스코어가 상당히 높게 나왔다. 영어 - 한국어 번역의 경우 30점대가 나오는데 제일 낮은데 69점이였다. 그만큼 성능에 대한 기대도 많이 하였는데 점수 만큼 높지 못한것 같았다. 또한 사람들이 BLEU에 대한 지표를 보고 직관적인 성능을 알지 못 하였다. 사투리 번역을 진행하는 모델을 개발해서 BLEU로 나타낸 자료가 없어서 비교 대상을 구할 수 없던것이 아쉬웠다. 하지만 이번 프로젝트를 통해 BLEU Score를 남겼고 다른 사람들이 비슷한 프로젝트를 진행 한다면 우리의 BLEU Score와 비교할 수 있었으면 좋겠다.
    
- http에서 https로 변경으로 인한 파급 효과
    
    처음엔 http로 개발을 진행하였다. 그런데 녹음 기능이나 파일 업로드 기능을 구현하려면 보안 문제로 인해 웹 브라우저와 https를 이용하여 통신을 해야했다. 그런데 https를 사용하는 서버는 http를 이용한 통신을 할 수 없었고 이는 시스템 전체에 문제를 발생시켰다. 특히 내가 맡은 부분인 react 서버와 SpringBoot 서버 간의 통신이 불가능 했다. react 서버와 브라우저에서 https를 사용하니 react 와 SpringBoot이 통신할 때도 https를 사용해야했다. 
    
    ![Untitled](https://user-images.githubusercontent.com/84767373/207081065-9c0f9817-7f95-4b9f-8adc-22b761e018e8.png)

    선배나 친구들에게 문제점을 공유하여 여러가지 해결책을 찾아냈다.
    
    1. https를 이용하여 통신한다.
    2. kafka를 이용한 메세지 전달을 이용하여 통신한다.
    3. gradle을 이용하여 react와 spring을 같이 build하여 하나의 서버에 https를 요청한다.
    
    1번 해결책의 경우 react 프로젝트에서 발급한 key에 대한 정보를 알아야 한다는 점이 있었다. react에서 https를 위한 운용은 그렇게 어렵지 않다는것을 알았지만 이번 프로젝트를 진행하면서 발생하는 여러 문제점에 대한 학습의 목표도 있었기 때문에 2번의 해결책으로 진행하였다. 2번 해결책인 kafka의 경우에서 사용자가 데이터를 kafka broker에게 바로 전달하고 API 서버에서 consume하여 획득한 데이터를 번역하여 다시 broker에게 전달하는 방식으로 이용했는데 메세지 전달 과정에서 서로 다른 언어를 사용하기 때문에 BytesSerializer를 사용해야했고 추가로 바이트 코드의 저장 순서가 맞지 않아 생기는 문제점이 있었다. 따라서 바이트 순서를 맞추기 위해서 API 서버에서 빅엔디안 방식으로 전달된 바이트를 리틀 엔디안으로 변경하여 데이터를 처리하고 다시 빅 엔디안으로 전송하도록 하였다. 그러나 멀티 쓰레드 환경에서 broker에게 전달한 메세지는 동시성 문제를 가지고 있었다. 한 토픽은 여러 파티션으로 이루어져있는데 이 파티션에서 consume을 하는 순서가 보장되어있지 않기 때문에 순서가 뒤죽박죽일 수 있겠다는 생각을 하였습니다. 그보다도 producer에서 동시에 요청을 할 경우 접근을 제한하였기 때문에 이 해결책을 사용할 수 없었다.
    
    ![Untitled 1](https://user-images.githubusercontent.com/84767373/207081100-bc6cf88c-2814-437c-a21d-3d902b40e258.png)

    
    따라서 3번째 해결책을 이용하였습니다. gradle을 사용하여 react와 spring을 같이 빌드하여 정적 페이지를 react의 빌드 파일에서 제공하고 spring은 요청 받은 데이터를 전달하는 방식으로 운영하였다. 이렇게 하면 빌드에 시간이 오래 걸리고 수정이 잦아진다는 문제가 있었지만 불필요하게 react에서도 호스팅하고 spring으로도 호스팅하는 번거로운 일이 없었다.
    
- 학교 내부망 사용으로 인한 여러 가지 제약
    
    학교에 서버를 두고 프로젝트를 하니 보안으로 인한 포트 사용이 참 번거로웠다. 학교 내부망을 사용할 경우 포트를 80과 443만 사용하여 외부에서 접속할 수 있었다. 443은 리눅스에서 ssh 연결을 위해 사용하니 80번 하나만 남았다 API서버를 80번 포트를 통해 접속해야 하는데 jupyter notebook을 이용한 원격 접속을 하려면 80번을 계속 내렸다가 올렸다 해야하고 DB 서버와 같은 추가적인 서버 운영이 불가능 하였다. 어쩔수 없이 라즈베리 파이나 개인 노트북을 이용하여 kafka broker나 DB 서버를 운영할 수 밖에 없었다.
    

---

## **프로젝트를 하면서 깨달은 것**

- 형상 관리의 중요성
    
    https 문제를 해결하기 위해서 kafka를 이용한 버전과 https를 이용한 버전 2가지가 존재했는데 중간에 kafka를 이용한 개발이 불가능하다는것을 알고 kafka를 이용한 branch를 없애고 https를 이용한 버전의 branch로 이동하니 버전 관리 및 형상 관리가 너무 간편했다.
    
- Mock 객체를 사용하지 않은 간단한 테스트를 작성하자
    
    스프링 부트에서 핵심 기능에 관한 테스트를 작성하였다.Mock 객체를 사용하지 않아서 API서버와 통신하는 테스트가 있었다. Gradle에서 빌드를 하면 자동으로 테스트를 수행하고 테스트에 실패하면 빌드도 하지 않았다. 뭔가 API 서버에 문제가 생기거나 네트워크 에러 같은 SpringBoot 이외의 문제가 발생해도 개발 이후 빌드를 할 때 찾을 수 있었다. Mock을 사용하는 이유는 테스트에 대한 시간 소모를 줄이고 외부 요인으로 인한 시스템 장애를 배제하는것인데 최종 build를 하기 전에는 간단하게라도 Mock객체를 사용하지 않는 테스트가 있는것도 좋을것 같다고 생각했다 
    
- 프론트엔드와 협업을 위한 Server 및 Swagger API
    
    프론트엔드 개발을 하면 WAS에 요청하여 데이터를 얻어올 일이 분명히 생긴다. 그런데 대부분 임시로 데이터를 집어 넣고 그냥 웹 페이지가 잘 보이는지만 테스트를 하며 개발한다. 그리고 나중에 빌드나 배포를 할 때 axios 코드를 넣는다.
    아무리 생각해봐도 별로 좋은 코드는 아닌것 같다. 그렇다고 하루종일 내 노트북을 켜 놓을수도 없는 노릇이다. 다른 사람들은 이걸 어떻게 해결할까?? docker를 이용하면 가상화 시스템을 통해 테스트 서버를 배포하면 프론트엔드 개발자도 서버를 실행하는 명령어만 알면 손쉽게 서버를 작동시킬 수 있다. 심지어 배치파일로 만들어 두면 배치 파일로 실행만 해도 가능하다. 여기서 궁금한게 있다. docker가 없으면 어떻게 할까? 로컬에서 돌릴수 있는 jar 파일을 전달해서 실행하라는것 말고는 딱히 떠오르는게 없긴 하다.
    
- Gradle을 이용한 빌드 및 배포
    
    Gradle로 React와 SpringBoot를 동시에 빌드할 수 있다는 사실에 놀라웠다. 처음으로 jar 파일을 만들어서 서버를 실행하고 이를 기반으로 무중단 배포까지 가능하다는걸 알았다.
    사실 제대로 사용할 줄 모르는 docker를 사용하거나 개발 환경에서 데모를 하는것 보다는 이런 방식으로도 서버를 운영할 수 있다는 경험을 통해 더 성장할 수 있었다. 

---
