#1. q.conceptCount (4) 와 Questionconcept qc 중 c.vType = 1 (신규) 인 qc의 합계가 다른 q

select q.questionId
 from question q
 left join (
				select qc.questionId, count(vType) as type1Cnt
				from QuestionConcept qc
				left join concept c
				on qc.conceptId =  c.conceptId
				where c.vType = 1
				group by qc.questionId
			) qcc
on q.questionId = qcc.questionId
where q.conceptCount !=  ifnull(qcc.type1Cnt, 0)
	;

/*
1. Question 에서 Select 해옴
2. LEFT JOIN -> 서브쿼리
3. 서브쿼리 : QuestionConcept에서 SELECT 해옴
4. LEFT JOIN -> Concept on concpetId 끼리 JOIN
*/

#2. q.conceptV2Count 와 Questionconcept qc 중 c.vType = 2 (old)인 qc의 합계가 다른 q

select q.questionId
 from question q
 left  join (
				select qc.questionId, count(vType) as type2Cnt
				from QuestionConcept qc
				left join concept c
				on qc.conceptId =  c.conceptId
				where c.vType = 2
				group by qc.questionId
			) qcc
on q.questionId = qcc.questionId
where q.conceptV2Count !=  ifnull(qcc.type2Cnt, 0)
;

select
    q.questionId, q.name, q.type, q.Type, q.conceptCount, q.conceptStr, q.conceptV2Count, q.conceptV2Str, q.ptnConceptId, 
    qc.conceptId, c.conceptAlias, c.name, c.vType, c.criteria, c.status, 
    count(qc.seqNo) as qc_cnt
from Question q 
    left join QuestionConcept qc on qc.questionId = q.questionId
    left join Concept c on c.conceptId = qc.conceptId
where c.vType = 1
group by q.questionId
having qc_cnt != q.conceptCount
order by q.questionId
;


select * from Question q 
	left join QuestionConcept qc on qc.questionId = q.questionId
where q.questionId = 'q0000000002';


SELECT q.questionId,
q.conceptCount, COUNT(IF(c.vType = 1, qc.conceptId, NULL)) as qc_count_v1,
q.conceptV2Count, COUNT(IF(c.vType = 2, qc.conceptId, null)) as qc_count_v2
FROM Question q
    LEFT JOIN QuestionConcept qc on q.questionId = qc.questionId
    LEFT JOIN Concept c on qc.conceptId = c.conceptId
GROUP BY q.questionId
HAVING q.conceptCount != qc_count_v1 OR q.conceptV2Count != qc_count_v2
;

SELECT q.questionId, q.conceptCount, q.conceptV2Count,
/* conceptStr의 갯수 확인용*/
(LENGTH(q.conceptStr) - LENGTH(REPLACE(q.conceptStr, ' ', '')) + 1) AS qc_split_count_v1,
(LENGTH(q.conceptV2Str) - LENGTH(REPLACE(q.conceptV2Str, ' ', '')) + 1) AS qc_split_count_v2,
/* conceptStr의 qc 포함 확인용*/
SUM(IF(c.vType = 1 AND INSTR(q.conceptStr, qc.conceptId) > 0, 1, 0)) AS qc_concat_count_v1,
SUM(IF(c.vType = 2 AND INSTR(q.conceptV2Str, qc.conceptId) > 0, 1, 0)) AS qc_concat_count_v2
FROM Question q
    JOIN QuestionConcept qc on q.questionId = qc.questionId
    JOIN Concept c on qc.conceptId = c.conceptId
GROUP BY q.questionId
HAVING
/* conceptStr의 갯수 확인용*/
q.conceptCount != qc_split_count_v1 OR q.conceptV2Count != qc_split_count_v2
/* conceptStr의 qc 포함 확인용*/
OR q.conceptCount != qc_concat_count_v1 OR q.conceptV2Count != qc_concat_count_v2  
;