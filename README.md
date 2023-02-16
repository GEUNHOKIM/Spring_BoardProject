# Spring을 통한 게시판 CRUD 만들어 보기

## 1. 기능 정의

**1.회원 가입**
+ username은  최소 4자 이상, 10자 이하이며 알파벳 소문자(a ~ z), 숫자(0 ~ 9)로 구성되어야 한다.
+ password는  최소 8자 이상, 15자 이하이며 알파벳 대소문자(a ~ z, A ~ Z), 숫자(0 ~ 9)로 구성되어야 한다
+ DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기

**2.로그인**
+ username, password를 Client에서 전달받기
+ DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
+ 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기

**3.전체 게시글 목록 조회**
+ 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
+ 작성 날짜 기준 내림차순으로 정렬하기

**4.게시글 작성**
+ 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
+ 제목, 작성자명(username), 작성 내용을 저장
+ 저장된 게시글을 Client 로 반환하기

**5.선택한 게시글 조회**
+ 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
(검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)

**6.선택한 게시글 수정**
+ 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
+ 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

**7.선택한 게시글 삭제** 
+ 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능 
+ 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기

# 2. Use Case Diagram
![UseCase](https://user-images.githubusercontent.com/95588392/217296892-7b3ac700-0efe-4734-b430-23589f86cb8d.png)

# 3. ERD
![Board_Project_ERD](https://user-images.githubusercontent.com/95588392/219228501-55deac37-f9eb-4667-9f72-dafd0387c372.png)

# 4. API명세서
![1](https://user-images.githubusercontent.com/95588392/219228352-2cc43d73-c0ec-4c4f-8db2-b2c0f46bef3f.png)
![2](https://user-images.githubusercontent.com/95588392/219228359-f306b58d-1081-4ef0-b08d-b28885afb771.png)

# 5. ❓ Why : 질문과 답변
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
 + 그렇지 않은 부분은 Dto를 Service에서 처리하는 로직에 있어서 Service는 데이터를 저장하는 테이블을 구성하는 역할을 하는데,
   같은 클래스에서 Dto를 받아와서 생성자를 처리하는 부분까지 역할을 단일책임의 원칙에 어긋나지 않을까 하는 생각이 듭니다.
   그래서 Dto를 받아서 처리하는 로직을 따로 빼서 다른 클래스로 만들었으면 어떨까 생각합니다.

**4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)**
  + Controller 
    + 클라이언트로부터의 요청을 URL에 맞게 Mapping을 통해서 Service로 전달 되도록 했습니다.
  + Service 
    + Controller를 통해서 전달 받은 요청을 비즈니스 로직을 통해서 Repository에 전달 되도록 했습니다.
  + Repository
    + Service에서 전달 받은 비즈니스 로직을 통해서 DB에 접근해서 데이터를 처리하도록 했습니다. 

**5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!**
  + API 명세서 예시를 참고하여 {기능, Method, URL, Request, Response}로 구성을 하였습니다.
