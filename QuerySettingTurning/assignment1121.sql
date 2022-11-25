/* [DB숙제] #3(miraen)

1. TABLE: Section s 에서 아래 조건의 row를 검색하시오
 - 중1(상) 과목(curriculumId: ma2015, sectionId: s1300000000)에 포함되는 모든 s
 
 - 결과:
 s.sectionId, s.name, s.level
 
2. 위에서 검색한 section 중 연결된 개념이 있는 경우에만 개념(vType:1, criteria:CW)과 패턴(criteria: PTN)으로 나누어 나열하시오(group_concat 사용하여 한줄로 표시)
 - 사용하는 TABLE: Section s, SectionConcept sc, Concept c

*/

select s.seqNo, s.sectionId, s.sectionId, s.name, sc.criteria from section s
left join sectionConcept sc on s.sectionId = sc.sectionId;

-- 컨셉, 유형

select * from sectionConcept;
select * from concept;

select sectionId, name, level from section
group by sectionId;

select s.sectionId, s.name, s.level, sc.criteria from section s
left join sectionconcept sc on s.sectionId = sc.sectionId
where s.curriculumId like 'ma2015' and
sc.criteria is not null and
s.sectionId like 's13%'
group by s.sectionId;

select CASE WHEN criteria = 'CW' then `value` ELSE 0 END AS a from sectionconcept;


set @sql = null;

SELECT
GROUP_CONCAT(DISTINCT CONCAT(
 ' MAX(CASE WHEN subjectid = ', criteria, ' THEN marks ELSE 0 END) 
 AS "', criteria, '"')
)
INTO @sql FROM sectionconcept;

SET @sql = CONCAT('SELECT name, ', @sql, 
 ' FROM student GROUP BY name');

PREPARE criteria FROM @sql;
EXECUTE criteria;
DEALLOCATE PREPARE criteria;

SELECT seqNo,   max(case when criteria = 'cw' then value else 0 end) jan,   max(case when criteria = 'ptn' then value else 0 end) feb
 FROM sectionconcept;
