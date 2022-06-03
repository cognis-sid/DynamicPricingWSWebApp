create or replace PACKAGE BODY PCR_REQUEST_QUOTE AS
/*-----------------------------------------------------------------------------------------------------------
-  for PCR_REQUEST_QUOTE
-------------------------------------------------------------------------------------------------------------
COPYRIGHT RCL PUBLIC CO., LTD. 2010
-------------------------------------------------------------------------------------------------------------
AUTHOR  Cognis 25/09/2021
- CHANGE LOG ------------------------------------------------------------------------------------------------
## DD/MM/YY           -USER-                       -TASKREF-           -SHORT DESCRIPTION
01 25/09/2021         Sushil                            
-----------------------------------------------------------------------------------------------------------*/

   PROCEDURE PCR_REQUEST_QUOTE_USER_ASSIGNMENT (
                                          P_I_V_USER_ID                       IN VARCHAR2,

                                          P_I_V_ROLE_NAME		              IN VARCHAR2, 
                                          P_I_V_PRO_NAME                      IN VARCHAR2, 
                                           P_I_V_LOGIN_ID                       IN VARCHAR2,  
                                          P_O_V_MAPPING_LIST                  OUT VARCHAR2
	) AS
        COUNT_NUMBER   NUMBER(10);
ltab_lname dbms_utility.lname_array;
   ln_len     BINARY_INTEGER;
    BEGIN
P_O_V_MAPPING_LIST:='SUCESS';

        select count(USER_ID) into COUNT_NUMBER   from RCLTBLS.ESM_USER_LOGIN   where USER_ID =''||P_I_V_USER_ID||'' ;
       -- dbms_output.put_line(COUNT_NUMBER||'''%'||P_I_V_USER_ID||'%''');
        IF COUNT_NUMBER=0  THEN
            P_O_V_MAPPING_LIST :='User does not exist';
            return ;
        END IF;

        select count(USER_ID) into COUNT_NUMBER   from QUOTE_USER_ASSIGNMENT   where USER_ID =''||P_I_V_USER_ID||'' ;
        --dbms_output.put_line(COUNT_NUMBER||'''%'||P_I_V_USER_ID||'%''');
        IF COUNT_NUMBER <>0  THEN
            P_O_V_MAPPING_LIST :='User all ready exist';
            return ;
        END IF;


        INSERT INTO QUOTE_USER_ASSIGNMENT (USER_ID,ROLE_NAME,RECORD_ADD_USER,RECORD_ADD_DATE) VALUES(P_I_V_USER_ID,P_I_V_ROLE_NAME,P_I_V_LOGIN_ID,SYSDATE);

         for rowData in
    ( select regexp_substr(P_I_V_PRO_NAME,'[^,]+',1,level) element
        from dual
     connect by level <= length(regexp_replace(P_I_V_PRO_NAME,'[^,]+')) + 1
    )
    loop
   -- dbms_output.put_line(rowData.element);
    --insert into sushiltest values(rowData.element);commit;

    INSERT INTO QUOTE_USER_ASSIGNMENT_ROLE (USER_ID_FK,PROPERTY_NAME,RECORD_ADD_USER,RECORD_ADD_DATE) VALUES(P_I_V_USER_ID,rowData.element,P_I_V_LOGIN_ID,SYSDATE);
    commit;
    --  pipe row(to_number(r.element, '999999.99'));
    end loop;       
    END PCR_REQUEST_QUOTE_USER_ASSIGNMENT;

    ---- Start SEarch


     PROCEDURE PCR_REQUEST_QUOTE_SEARCH_USER(
                                          P_I_V_USER_ID                       IN VARCHAR2,                                          
                                          P_I_V_ROLE_NAME		              IN VARCHAR2, 
                                          P_I_V_EMAIL                      IN VARCHAR2, 
                                          P_I_V_PHONE                       IN VARCHAR2, 
                                          P_I_V_LOCATION                       IN VARCHAR2, 
                                           P_I_V_FROM_DATE                      IN VARCHAR2, 
                                            P_I_V_TO_DATE                       IN VARCHAR2,  

                                          P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR
    )AS
        V_SQL   VARCHAR2(1000);
        V_SQL_CNDTN VARCHAR2(1000);
    BEGIN
    V_SQL   := 'select (select max(PROPERTY_NAME) from QUOTE_USER_ASSIGNMENT_ROLE where USER_ID_FK=u.USER_ID ) as FSC,  u.USER_ID as USER_ID,u.USER_NAME,u.EMAIL_ID1 ,u.COMPANY_NAME,u.COUNTRY,u.PHONE,ROLE_NAME,RECORD_ADD_USER,RECORD_ADD_DATE,RECORD_CHANGE_USER,RECORD_CHANGE_DATE from QUOTE_USER_ASSIGNMENT assin,V_QUOTE_ESM_USER u where assin.USER_ID=u.USER_ID ';
    
    
     IF(P_I_V_USER_ID IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER(u.USER_ID) like ' || 'UPPER(''%'||P_I_V_USER_ID||'%'')'  ;     ---- USER_ID
       END IF;

     IF(P_I_V_EMAIL IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND u.EMAIL_ID1 like ' || '''%'||P_I_V_EMAIL||'%'''  ;     ---- USER_ID
       END IF;

        IF(P_I_V_ROLE_NAME IS NOT NULL  AND P_I_V_ROLE_NAME <>'-1')
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND assin.ROLE_NAME = ' || ''''||P_I_V_ROLE_NAME||''''  ;     ---- P_I_V_ROLE_NAME
       END IF;

        IF(P_I_V_PHONE IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND u.P_I_V_PHONE  like ' || '''%'||P_I_V_PHONE||'%'''  ;     ---- P_I_V_ROLE_NAME
       END IF;

        IF( P_I_V_FROM_DATE IS NOT NULL) THEN
      V_SQL_CNDTN := V_SQL_CNDTN || '  AND to_date(assin.RECORD_ADD_DATE,''YYYYMMDD'') >= to_date(' || ''''||P_I_V_FROM_DATE||''' ,''YYYYMMDD'' )'  ;   
     END IF;

       IF( P_I_V_TO_DATE IS NOT NULL) THEN        
      V_SQL_CNDTN := V_SQL_CNDTN || '  AND to_date(assin.RECORD_ADD_DATE,''YYYYMMDD'') <=  to_date(' || ''''||P_I_V_TO_DATE ||''' ,''YYYYMMDD'' )'  ;   
     END IF;   
    V_SQL := V_SQL || V_SQL_CNDTN|| ' order by assin.USER_ID ';
 --=upper(trim('|| '''' || P_I_V_USER_ID || ''''||'))'; 
   -- insert into sushiltest values(V_SQL);commit;

 OPEN P_O_V_SEARCH_LIST FOR  V_SQL;

    END PCR_REQUEST_QUOTE_SEARCH_USER;



    PROCEDURE PCR_REQUEST_DELETE_QUOTE_USER (
                                          P_I_V_USER_ID                       IN VARCHAR2                                     

                                ) AS
    BEGIN
        DELETE FROM QUOTE_USER_ASSIGNMENT_ROLE WHERE USER_ID_FK=P_I_V_USER_ID;
        DELETE FROM QUOTE_USER_ASSIGNMENT WHERE USER_ID =P_I_V_USER_ID;

    END PCR_REQUEST_DELETE_QUOTE_USER;

    -- THIS FOR CREATE REQUEST



 PROCEDURE PCR_REQUEST_QUOTE_REQUEST(
                                          P_I_V_POL                       IN VARCHAR2,                                          
                                          P_I_V_POD		              IN VARCHAR2, 
                                          P_I_V_SHIPPER                      IN VARCHAR2, 
                                          P_I_V_VESSEL                       IN VARCHAR2, 
                                          P_I_V_VOYAGE                       IN VARCHAR2, 
                                           P_I_V_VOLUME                      IN VARCHAR2, 
                                            P_I_V_FREE_TIME                       IN VARCHAR2, 
                                           P_I_V_TAGET_RATE                       IN VARCHAR2, 
                                           P_I_V_TARGET_ETD                      IN VARCHAR2, 
                                           P_I_V_REQUEST_ID                       IN VARCHAR2,
                                           P_I_V_USER_ID                       IN VARCHAR2   ,
                                           P_I_V_REMARKS  IN VARCHAR2   ,
                                           P_I_V_CMODITY  IN VARCHAR2   ,
                                           P_I_V_WEIGHT  IN VARCHAR2   ,
                                           P_I_V_CONTRACT_PARTY  IN VARCHAR2 
    )AS  
    L_SEQ NUMBER DEFAULT 0;
    L_TARGET_DATE DATE;
    BEGIN 

       SELECT QUOTE_REQUEST_SEQ.NEXTVAL INTO L_SEQ FROM DUAL;
       IF( P_I_V_TARGET_ETD IS NOT NULL) THEN        
      L_TARGET_DATE := to_date(P_I_V_TARGET_ETD,'YYYYMMDD') ;   
     END IF;   
       INSERT INTO QUOTE_REQUEST(
    QUOTE_REQUEST_PK,
    POL,
    POD,
    SHIPPER,
    VESSEL ,
    VOYOGE,
    VOLUME ,
    FREE_TIME ,
    TARGET_RATE ,
    TARGET_ETD  ,
    REQUEST_ID ,   
    RECORD_ADD_USER ,
	RECORD_ADD_DATE ,
    RECORD_STATUS,
    REMARKS,CMODITY,WEIGHT ,CONTRACT_PARTY
    ) 
    VALUES(L_SEQ,P_I_V_POL,P_I_V_POD,P_I_V_SHIPPER,P_I_V_VESSEL,P_I_V_VOYAGE,P_I_V_VOLUME,
    P_I_V_FREE_TIME,P_I_V_TAGET_RATE,L_TARGET_DATE,'QR'||L_SEQ,P_I_V_USER_ID,SYSDATE,'S',P_I_V_REMARKS,P_I_V_CMODITY,P_I_V_WEIGHT,P_I_V_CONTRACT_PARTY);
    COMMIT;


    END PCR_REQUEST_QUOTE_REQUEST;


     PROCEDURE PCR_REQUEST_QUOTE_REQUEST_SEARCH(
                                    P_I_V_POL                       IN VARCHAR2,                                          
                                    P_I_V_POD		              IN VARCHAR2, 
                                    P_I_V_SHIPPER                      IN VARCHAR2, 
                                    P_I_V_VESSEL                       IN VARCHAR2, 
                                    P_I_V_VOYAGE                       IN VARCHAR2, 
                                    P_I_V_VOLUME                      IN VARCHAR2, 
                                    P_I_V_FREE_TIME                       IN VARCHAR2,                                     
                                    P_I_V_FROM_DATE                      IN VARCHAR2, 
                                    P_I_V_TO_DATE                       IN VARCHAR2,  
                                    P_I_V_REQUEST_ID                       IN VARCHAR2,
                                    P_I_V_USER_ID                       IN VARCHAR2 ,
                                    P_I_V_LOGIN_ID                       IN VARCHAR2, 
                                    P_I_V_ROLE_NAME                       IN VARCHAR2, 
                                    P_I_V_STATUS_ID                       IN VARCHAR2,
                                    P_I_V_APPROVE_ID                       IN VARCHAR2,
                                    P_I_V_APPROVE_BY_ID                       IN VARCHAR2,
                                    P_I_V_TARGETED_DATE                       IN VARCHAR2,
                                    P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR
    ) AS

         V_SQL   VARCHAR2(1000);
        V_SQL_CNDTN VARCHAR2(1000);
    BEGIN
    V_SQL   := '  Select REASON_OF_REJECT, QUOTE_REQUEST_PK,    POL,    POD,    SHIPPER,    VESSEL ,    VOYOGE,    VOLUME ,    FREE_TIME ,    TARGET_RATE ,    TARGET_ETD  ,    REQUEST_ID ,       RECORD_ADD_USER ,
	RECORD_ADD_DATE ,    APPOVE_BY,    APPROVE_ID,    RECORD_STATUS ,RECORD_CHANGE_DATE ,REMARKS,CMODITY,WEIGHT,CONTRACT_PARTY  from QUOTE_REQUEST     where 1=1 ';

     IF(P_I_V_USER_ID IS NOT NULL )     THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND RECORD_ADD_USER  like ' || '''%'||P_I_V_USER_ID||'%'''  ;     ---- USER_ID
       END IF;

     IF(P_I_V_POL IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND POL like ' || '''%'||P_I_V_POL||'%'''  ;     ---- USER_ID
       END IF;

        IF(P_I_V_POD IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND POD like ' || '''%'||P_I_V_POD||'%'''  ;     ---- P_I_V_ROLE_NAME
       END IF;

        IF(P_I_V_SHIPPER IS NOT NULL )
        THEN                  
         V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER(SHIPPER ) like ' || 'UPPER(''%'||P_I_V_SHIPPER||'%'')'  ;     ---- P_I_V_ROLE_NAME
       END IF;

        IF( P_I_V_TARGETED_DATE IS NOT NULL) THEN
      V_SQL_CNDTN := V_SQL_CNDTN || '  AND  TO_DATE( to_char(TARGET_ETD,''YYYYMMDD''),''YYYYMMDD'') = to_date(' || ''''||P_I_V_TARGETED_DATE||''' ,''YYYYMMDD'' )'  ;   
     END IF;

        IF( P_I_V_FROM_DATE IS NOT NULL) THEN
      V_SQL_CNDTN := V_SQL_CNDTN || '  AND  TO_DATE( to_char(record_add_date,''YYYYMMDD''),''YYYYMMDD'') >= to_date(' || ''''||P_I_V_FROM_DATE||''' ,''YYYYMMDD'' )'  ;   
     END IF;

       IF( P_I_V_TO_DATE IS NOT NULL) THEN        
      V_SQL_CNDTN := V_SQL_CNDTN || '  AND TO_DATE( to_char(record_add_date,''YYYYMMDD''),''YYYYMMDD'') <=  to_date(' || ''''||P_I_V_TO_DATE ||''' ,''YYYYMMDD'' )'  ;   
     END IF;   

     -- This code for base on seaech
      IF( P_I_V_ROLE_NAME ='1' OR P_I_V_ROLE_NAME ='2') THEN        
        V_SQL_CNDTN := V_SQL_CNDTN || ' AND RECORD_ADD_USER like ' || '''%'||P_I_V_LOGIN_ID||'%'''  ;   
     END IF;  

     IF( P_I_V_ROLE_NAME ='3') THEN        
        V_SQL_CNDTN := V_SQL_CNDTN || ' AND RECORD_ADD_USER in (select distinct USER_ID_FK from QUOTE_USER_ASSIGNMENT_ROLE where PROPERTY_NAME in (
                select PROPERTY_NAME from QUOTE_USER_ASSIGNMENT_ROLE where USER_ID_FK like ' || '''%'||P_I_V_LOGIN_ID||'%''))';

     END IF;

     IF( P_I_V_REQUEST_ID IS NOT NULL) THEN        
       V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER( REQUEST_ID) like ' || 'UPPER(''%'||P_I_V_REQUEST_ID||'%'')'  ; 
     END IF;

      IF( P_I_V_APPROVE_ID IS NOT NULL) THEN        
       V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER( APPROVE_ID) like ' || 'UPPER(''%'||P_I_V_APPROVE_ID||'%'')'  ; 
     END IF;

     IF( P_I_V_APPROVE_BY_ID IS NOT NULL) THEN        
       V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER( APPOVE_BY) like ' || 'UPPER(''%'||P_I_V_APPROVE_BY_ID||'%'')'  ; 
     END IF;

     IF( P_I_V_STATUS_ID IS NOT NULL) THEN        
       V_SQL_CNDTN := V_SQL_CNDTN || ' AND UPPER( RECORD_STATUS) like ' || 'UPPER(''%'||P_I_V_STATUS_ID||'%'')'  ; 
     END IF;

    V_SQL := V_SQL || V_SQL_CNDTN || '  ORDER by record_add_date desc';

 --=upper(trim('|| '''' || P_I_V_USER_ID || ''''||'))'; 
    --insert into sushiltest values(V_SQL);commit;

    OPEN P_O_V_SEARCH_LIST FOR  V_SQL;
    END PCR_REQUEST_QUOTE_REQUEST_SEARCH;


    PROCEDURE PCR_REQUEST_QUOTE_REQUEST_ACTION (
                                          P_I_V_REQUEST_PK                       IN VARCHAR2,
                                          P_I_ACTION_TYPE                       IN VARCHAR2,
                                          P_I_V_USER_ID                       IN VARCHAR2,
                                          P_I_V_REASON                       IN VARCHAR2

                                )AS 

    L_SEQ NUMBER DEFAULT 0;

    BEGIN 


        IF P_I_ACTION_TYPE ='APPROVE' THEN
            SELECT QUOTE_REQUEST_APPROVED_SEQ.NEXTVAL INTO L_SEQ FROM DUAL;

            UPDATE QUOTE_REQUEST SET APPROVE_ID='AP'||L_SEQ,RECORD_STATUS='A', RECORD_CHANGE_USER=P_I_V_USER_ID,RECORD_CHANGE_DATE=SYSDATE,APPOVE_BY=P_I_V_USER_ID WHERE QUOTE_REQUEST_PK=P_I_V_REQUEST_PK;
            commit;
        END IF;
        IF P_I_ACTION_TYPE='REJECT' THEN
            UPDATE QUOTE_REQUEST SET APPROVE_ID=NULL, RECORD_STATUS='R', RECORD_CHANGE_USER=P_I_V_USER_ID,RECORD_CHANGE_DATE=SYSDATE,REJECTED_BY=P_I_V_USER_ID ,
            REASON_OF_REJECT =P_I_V_REASON
                WHERE QUOTE_REQUEST_PK=P_I_V_REQUEST_PK;
            commit;
        END IF;

    END PCR_REQUEST_QUOTE_REQUEST_ACTION;





    /*****
        This are for LOGIN 
    *****/

    PROCEDURE PCR_REQUEST_QUOTE_LOGIN_TOKEN (
                                          P_I_V_USER_ID                       IN VARCHAR2,
                                          P_I_TOKEN                       IN VARCHAR2,
                                          P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR

                                ) AS 
    BEGIN
         OPEN P_O_V_SEARCH_LIST FOR  'SELECT TOKEN  FROM QUOTE_USER_ASSIGNMENT WHERE USER_ID='|| ''''||P_I_V_USER_ID||'''  ';
    END PCR_REQUEST_QUOTE_LOGIN_TOKEN;


    PROCEDURE PCR_REQUEST_QUOTE_EMAIL_CONFIG (

                                          P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR

                                ) AS
        V_SQL VARCHAR2(1000);
    BEGIN
    V_SQL :='SELECT 
		(SELECT  CONFIG_VALUE  FROM VASAPPS.VCM_CONFIG_MST WHERE STATUS = ''A'' AND CONFIG_CD = ''MAIL_SERVER_IP'') AS MAIL_SERVER_IP,
        (SELECT  CONFIG_VALUE  FROM VASAPPS.VCM_CONFIG_MST WHERE STATUS = ''A'' AND CONFIG_CD = ''MAIL_SERVER_USER_ID'') AS MAIL_SERVER_USER_ID,
        (SELECT  CONFIG_VALUE  FROM VASAPPS.VCM_CONFIG_MST WHERE STATUS = ''A'' AND CONFIG_CD =  ''MAIL_SERVER_PASSWORD'') AS MAIL_SERVER_PASSWORD,
        (SELECT  CONFIG_VALUE  FROM VASAPPS.VCM_CONFIG_MST WHERE STATUS = ''A'' AND CONFIG_CD = ''MAIL_SENDER_EMAIL'') AS MAIL_SENDER_EMAIL         
		FROM DUAL';

         OPEN P_O_V_SEARCH_LIST FOR  V_SQL;
    END PCR_REQUEST_QUOTE_EMAIL_CONFIG;

    PROCEDURE PCR_REQUEST_QUOTE_LOGIN (
                                          P_I_V_USER_ID                       IN VARCHAR2,
                                          P_I_PASSWORD                       IN VARCHAR2,
                                           P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR

                                ) AS 
      V_SQL   VARCHAR2(1000);
    BEGIN

    update QUOTE_USER_ASSIGNMENT set TOKEN=P_I_PASSWORD where USER_ID=P_I_V_USER_ID;commit;
    V_SQL :='select  (select max(PROPERTY_NAME) from QUOTE_USER_ASSIGNMENT_ROLE where USER_ID_FK= ' || ''''||P_I_V_USER_ID||''') as PROPERTY_NAME,  USER_NAME, login.USER_ID,login.EMAIL_ID1 from RCLTBLS.ESM_USER_LOGIN login, QUOTE_USER_ASSIGNMENT role where login.USER_ID=role.USER_ID 
    and (login.USER_ID= ' || ''''||P_I_V_USER_ID||'''  ) ';
  --  V_SQL :=V_SQL|| ' and pwd.PASSWORD=  ' || ''''||P_I_PASSWORD||'''';
    V_SQL :=V_SQL|| ' AND  role.ROLE_NAME in (1,2)'; 


    OPEN P_O_V_SEARCH_LIST FOR  V_SQL;

    END PCR_REQUEST_QUOTE_LOGIN;

    PROCEDURE PCR_REQUEST_QUOTE_DELETE (
                                          P_I_V_REQUEST_ID                       IN VARCHAR2  ,
                                          P_I_V_USER_ID                       IN VARCHAR2

                                )AS
    BEGIN
        DELETE FROM QUOTE_REQUEST WHERE QUOTE_REQUEST_PK=P_I_V_REQUEST_ID AND RECORD_STATUS='S' AND RECORD_ADD_USER=P_I_V_USER_ID;
        commit;
    END PCR_REQUEST_QUOTE_DELETE;


    PROCEDURE PCR_REQUEST_QUOTE_GET_FSC (
                                          P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR
                                ) AS
    BEGIN 
         OPEN P_O_V_SEARCH_LIST FOR  'Select PK_FSC_CODE,FSC_DESCRIPTION from rcltbls.cam_fsc order by PK_FSC_CODE';
    END PCR_REQUEST_QUOTE_GET_FSC;    

    PROCEDURE PCR_REQUEST_QUOTE_GET_PORT (
                                          P_O_V_SEARCH_LIST                 OUT SYS_REFCURSOR
                            ) AS

    BEGIN 
         OPEN P_O_V_SEARCH_LIST FOR  'SELECT PORT_CODE,DESCRIPTION FROM VRL_BKG_PORT';
    END PCR_REQUEST_QUOTE_GET_PORT; 
END PCR_REQUEST_QUOTE;