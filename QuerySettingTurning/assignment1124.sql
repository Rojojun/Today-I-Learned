use miraen;

/*
1. TABLE: Usr u 에서 아래 조건의 row를 검색하시오
 - u.mobile 이 전화번호 형식이 아닌 User
 (i.e.
  - 모든 문자가 숫자로만 이루어져 있고 길이가 9자리 ~ 11자리 이거나
 
  - [-] 가 2개 있고
  처음 [-] 앞에는 2자리~3자리 숫자가 있고,
  처음 [-] 과 두번째 [-] 사이에는 3자리~4자리 숫자가 있고,
  마지막은 3자리~4자리 숫자로 끝남
 )
*/
/*case문 으로 9자리 -11자리 나누기*/
SELECT seqNo, userId, mobile
FROM User
WHERE LENGTH(mobile) > 0 AND
mobile NOT REGEXP '^[[:digit:]]{9,11}+$' AND
mobile NOT REGEXP '^[[:digit:]]{2,3}-[[:digit:]]{3,4}-[[:digit:]]{3,4}+$'
;