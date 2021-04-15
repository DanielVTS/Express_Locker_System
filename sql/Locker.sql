DROP TABLE IF EXISTS els_locker.LOCKER_BASIC_INFORMATION;
CREATE TABLE els_locker.LOCKER_BASIC_INFORMATION
(
    LOCKER_ID   INT DEFAULT UNIQUE_ROWID() PRIMARY KEY ,
    PROVINCE    VARCHAR(20),
    CITY        VARCHAR(20),
    LOCKER_NAME VARCHAR(200),
    TOTAL_BOX   INT,
    USED_BOX    INT,
    CREATE_TIME TIMESTAMPTZ(0),
    UPDATE_TIME TIMESTAMPTZ(0),
    STATUS_TIME TIMESTAMPTZ(0),
    ROW_NUM     INT,
    COLUMN_NUM  INT,
    REMARK      VARCHAR(200)
);

INSERT INTO els_locker.LOCKER_BASIC_INFORMATION(
                                     PROVINCE,
                                     CITY,
                                     LOCKER_NAME,
                                     TOTAL_BOX,
                                     USED_BOX,
                                     CREATE_TIME,
                                     UPDATE_TIME,
                                     STATUS_TIME,
                                     ROW_NUM,
                                     COLUMN_NUM,
                                     REMARK
) VALUES (
          '广东省',
          '湛江市',
          '测试快递柜1',
          16,
          0,
          localtimestamp(),
          localtimestamp(),
          localtimestamp(),
          4,
          4,
          '人工录入测试数据'
         );
DROP TABLE IF EXISTS els_locker.LOCKER_BOX_INFORMATION;
CREATE TABLE els_locker.LOCKER_BOX_INFORMATION
(
    LOCKER_BOX_ID UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    LOCKER_ID     INT,
    LOCKER_COLUMN INT,
    LOCKER_ROW    INT,
    DOOR_STATUS   INT,
    BOX_IS_EMPTY  INT,
    BOX_TYPE      INT,
    STATUS        INT,
    CREATE_TIME   TIMESTAMPTZ(0),
    UPDATE_TIME   TIMESTAMPTZ(0),
    STATUS_TIME   TIMESTAMPTZ(0),
    REMARK        VARCHAR(200)
);

INSERT INTO els_locker.LOCKER_BOX_INFORMATION(LOCKER_ID,
                                   LOCKER_COLUMN,
                                   LOCKER_ROW,
                                   DOOR_STATUS,
                                   BOX_IS_EMPTY,
                                   BOX_TYPE,
                                   STATUS,
                                   CREATE_TIME,
                                   UPDATE_TIME,
                                   STATUS_TIME,
                                   REMARK)
VALUES ((SELECT LOCKER_ID FROM els_locker.LOCKER_BASIC_INFORMATION LIMIT 1),
        1,
        1,
        0,
        0,
        1,
        1,
        localtimestamp(),
        localtimestamp(),
        localtimestamp(),
        '人工录入测试数据');