# Practice
해결한 문제 저장소

## 좋아요에 대한 동시성 해결 : [like](like)
다음과 같은 엔티티가 존재한다
- [Feed.java](like%2Fsrc%2Fmain%2Fjava%2Fme%2Fdevksh930%2Fpractice%2Flike%2Fdomain%2FFeed.java) : 피드에 대한 내용을 저장
- [FeedCounter.java](like%2Fsrc%2Fmain%2Fjava%2Fme%2Fdevksh930%2Fpractice%2Flike%2Fdomain%2FFeedCounter.java) : 피드에 대한 각종 카운트를 저장
- [FeedLike.java](like%2Fsrc%2Fmain%2Fjava%2Fme%2Fdevksh930%2Fpractice%2Flike%2Fdomain%2FFeedLike.java) : 피드에 대해 좋아요를 누른 사람의 횟수

요구사항
- 좋아요는 한 개의 `Feed` 당 한 명만 가능하다.
- 좋아요시 `FeedLike`에 `userId`와 `feedId` 를 저장한다
- 저장후 `FeedCounter`에 있는 `feedLikecount`를 1증가 한다.

문제 사항
- `FeedCounter`의 갯수와 `FeedLike`의 갯수가 맞지 않음
- [`FeddLike` 검사  -> `FeedLike` 저장 → `FeedCounter` 조회-> 영속성을 통한 `FeedCounter` 업데이트] 트랜잭션 경합으로 인한 `FeedCounter` **쓰기스큐발생** [개선전 로직](like%2Fsrc%2Fmain%2Fjava%2Fme%2Fdevksh930%2Fpractice%2Flike%2Fservice%2FAfterFeedLikeService.java)
- jpa의 영속성 의한 `DirtyRead` 발생(T1에서 읽은 값이 T2,T3에서 먼저 변경되었을경우 영속성으로 인한 이슈)
- [해당 문제를 재현 하는 테스트 코드](like%2Fsrc%2Ftest%2Fjava%2Fme%2Fdevksh930%2Fpractice%2Flike%2Fservice%2FAfterFeedLikeServiceTest.java)

개선방법
- [`FeedLike` 저장 -> `FeedCounter` 업데이트] 로 개선
- `FeedLike`의 `userId`와 `feedId`를 복합 유니크 키로 설정을 통한 중복 입력방지, 검사쿼리 개선 
- `isolationLevel` `READ_COMMITED`로 변경으로 인한 `DirtyRead` 해결 
- `READ_COMMITTED` 격리 수준에서는 PantomRead 문제가 발생 -> 조회해서 사용하지 않고 바로 업데이트
- `FeedCounter` 조회 하지 않고 바로 업데이트 하기 -> 영속성 사용시 발생하는 `PantomRead` 해결
- `FeedCounter`를 업데이트한 row의 개수가 적으면 Exception 발생시켜 RollBack(존재 하지 않는 게시물에 대한 요청일 경우)
