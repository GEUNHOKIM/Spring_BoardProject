# Spring을 통한 게시판 CRUD 만들어 보기

## 1. 기능 정의
**1.전체 게시글 목록 조회**
+ 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
+ 작성 날짜 기준 내림차순으로 정렬하기

**2.게시글 작성**
+ 제목, 작성자명, 비밀번호, 작성 내용을 저장
+ 저장된 게시글을 Client 로 반환하기

**3.선택한 게시글 조회**
+ 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기

**4.선택한 게시글 수정**
+ 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인
+ 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

**5.선택한 게시글 삭제** 
+ 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 
+ 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

# 2. Use Case Diagram
![UseCase](https://user-images.githubusercontent.com/95588392/217296892-7b3ac700-0efe-4734-b430-23589f86cb8d.png)

# 3. API명세서
![api명세서](https://user-images.githubusercontent.com/95588392/217297014-454becf1-7fcf-4bd4-b8be-d84aa7352d04.png)

# 4. ❓ Why : 질문과 답변
**1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)**
+ **수정 API**에서는 **id**를 **@PathVariable**로 받으므로 **param**방식을 사용했고, 수정내용을 포함 하고 있는 **{title,content,username}**은 **body**를 사용했습니다. 
+ **삭제 API**에서는 **id**를 **@PathVariable**로 받으므로 **param**방식을 사용했습니다.

**2. 어떤 상황에 어떤 방식의 request를 써야하나요?**
+ **param**
  + 주소에 포함된 변수를 담는다
  + 데이터를 식별할때 적절하다.
    + ex) /api/board/1 : 1번 board를 가져온다
  + 서버에서 @PathVariable로 칭한다 
+ **query**
  + 엔드포인트에서 물음표(?) 뒤에 key=value 형태로 변수를 담는다
    ex) /api/board/?name="철수"&age=28
  **body**
  + URL에 보이지 않는 오브젝트의 데이터들을 담는다.
  + 객체 자체를 바로 보낼때 적합하다

**3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?**
 + Resorce인 Borad를 중심으로 설계를 했고, URI만 보더라도 리소스를 추론할 수 있도록 설계했습니다. 

**4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)**

**5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!**
