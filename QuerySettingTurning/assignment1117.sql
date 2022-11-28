use miraen;

select * from ticket tk;
select * from ticketcontent tkc;
select * from user;
	#where email is null;
	# where status like 2;
select * from admin
	where status like 2;

select * from ticket tk
	where status = 12;

#select tk.* from ticket tk
#  inner join user u on tk.userId = u.userId
#  inner join ticketcontent tkc on tkc.ticketId = tk.ticketId
#  inner join admin ad on tkc.writerId = ad.adminId
#    Where u.status like 2 or
#    u.email is null or
#    ad.status like 2 or
#    tk.managerId is null;

select * from ticket ;
select * from user;
select * from ticketContent;
select * from admin;

SELECT tk.*, u.seqNo, u.status
  FROM ticket tk
  LEFT OUTER JOIN user  u 
  ON tk.userId = u.userId
  WHERE u.status = 2;


SELECT tk.*
  FROM ticket tk
  INNER JOIN  ticketcontent tkc
  ON  tk.ticketId = tkc.ticketId
  WHERE tkc.ticketId = 'c49cc835-68d9-4dd5-a9cf-2a0e5fd102db';
    
SELECT * FROM ticket where ticketId = 'c49cc835-68d9-4dd5-a9cf-2a0e5fd102db'; # u0000000121
SELECT * FROM user WHERE userId = 'u0000000121'; # x
SELECT * FROM ticketcontent WHERE ticketId = 'c49cc835-68d9-4dd5-a9cf-2a0e5fd102db'; #Vitruv_KYR
SELECT * FROM admin WHERE adminId = 'Vitruv_KYR'; #status : 2
 SELECT distinct ticketId, writerId FROM  ticketcontent;
#, u.seqNo,u.status as userStatus, tkc.contentNo, tkc.writerId , adm.status as admStatus
# distinct -> 중복제거
# group by, disctinct 비교
# Answer 1
SELECT tk.ticketId FROM ticket tk
	INNER  JOIN  (SELECT distinct ticketId, writerId FROM  ticketcontent) tkc ON  tk.ticketId = tkc.ticketId
    LEFT OUTER JOIN user  u ON tk.userId = u.userId
    LEFT OUTER JOIN admin adm ON tkc.writerId = adm.adminId
    WHERE (u.seqNo is null OR u.status = 2)
    AND (adm.seqNo is null OR adm.status = 2);
 
# Answer 2의 값을 확인하기 위한 Query
select ticketId, status from ticket WHERE ticketId IN ('f5d9f621-56be-45c7-a081-ee72127d4466',  'c49cc835-68d9-4dd5-a9cf-2a0e5fd102db');

# Answer 2
UPDATE ticket t
JOIN (
	#, u.seqNo,u.status as userStatus, tkc.contentNo, tkc.writerId , adm.status as admStatus
	SELECT distinct tk.ticketId AS ticketId	FROM ticket tk
		INNER JOIN  ticketcontent tkc ON  tk.ticketId = tkc.ticketId
		LEFT OUTER JOIN user u ON tk.userId = u.userId
        LEFT OUTER JOIN admin adm ON tkc.writerId = adm.adminId
		WHERE (u.seqNo is null OR u.status = 2)
		AND (adm.seqNo is null OR adm.status = 2)
) val ON t.ticketId = val.ticketId
SET status = 12;

#update ticket tk
#	inner join user u on tk.userId = u.userId
#    inner join ticketcontent tkc on tkc.ticketId = tk.ticketId
#    inner join admin ad on tkc.writerId = ad.adminId
#    set tk.status = '12'
#    Where u.status like 2 or
#    u.email is null or
#    ad.status like 2 or
#    tk.managerId is null;

#UPDATE ticket SET status = 13
#WHERE ticketId
#	IN (
#    #, u.seqNo,u.status as userStatus, tkc.contentNo, tkc.writerId , adm.status as admStatus 
#    SELECT distinct tk.ticketId AS ticketId FROM ticket tk
#	INNER JOIN  ticketcontent tkc ON  tk.ticketId = tkc.ticketId
#    LEFT OUTER JOIN user u ON tk.userId = u.userId
#    LEFT OUTER JOIN admin adm ON tkc.writerId = adm.adminId
#    WHERE (u.seqNo is null OR u.status = 2)
#    AND (adm.seqNo is null OR adm.status = 2)
#	);    
    


