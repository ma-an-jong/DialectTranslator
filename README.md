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

- 분류 결과

![image](https://user-images.githubusercontent.com/84767373/207541222-a58a06fb-404f-4cab-a8fe-2e32088397eb.png)

## 시스템 구조도

![image](https://user-images.githubusercontent.com/84767373/207541257-c12b4a78-f950-4668-af5e-e80c692c7c80.png)

---
