/* [DB숙제] #2
TABLE: Eval ev에서 아래 조건의 row를 검색하시오
- 생성된 기간이 2022.1.~ 현재까지 이면서
-  submt을 한 eval (ev.submitDate != null) 을
- 월별로 eval 수와 사용자 수(distinct userId) 를 집계하시오
*/

/*
YEAR, MONTH를 활용해서, datetime type의 데이터를 연/월/일/시/분/초 등등 상세하게 나눌 수 있다.
*/

# Answer. addDate
SELECT YEAR(addDate) AS 'year', MONTH(addDate) AS 'month', COUNT(DISTINCT userId) as 'userIdCnt', COUNT(*) AS 'cnt_eval'
FROM eval ev
WHERE addDate >= '2022-01-01' AND submitDate IS NOT NULL
GROUP BY YEAR(addDate),  MONTH(addDate);

/*
SUBSTRING을 활용하여 출력 정하기 (컬럼, 시작값(INT), 종료값(INT))
- 시작값에서 종료값까지 컬럼의 문자열 데이터를 잘라옴
*/

SELECT
SUBSTRING(ev.addDate,1,7) AS 'date', COUNT(DISTINCT ev.userId) AS 'cnt_user', COUNT(*) AS 'cnt_eval'
FROM Eval ev
WHERE ev.addDate >= '2022-01-01 00:00:00' AND ev.submitDate IS NOT NULL
GROUP BY SUBSTRING(ev.addDate,1,7)
ORDER BY SUBSTRING(ev.addDate,1,7)
;