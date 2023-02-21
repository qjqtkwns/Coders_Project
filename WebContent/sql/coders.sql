-- ���� ���̺�
    create table user_member(
        user_id varchar2(30) primary key,           -- ���� ���̵�
        user_name varchar2(30),                     -- ���� �г���
        user_date date,                             -- ���� ��������
        user_profile varchar2(1000),                -- ���� �ڱ�Ұ�
        user_homepage varchar2(200),                 -- ���� Ȩ������ url
        user_pwd varchar2(1000),
        user_file varchar2(300)
    );

-- Q&A �Խ��� ���̺�  
    create table qna (
        qna_num number(5) primary key,      -- qna �Խ��� ��ȣ
        qna_writer references user_member(user_id) on delete cascade,   -- qna �Խ��� �ۼ���
        qna_title varchar2(200) not null,   -- qna �Խ��� ����
        qna_cont clob not null,   -- qna �Խ��� ����
        qna_date date,                      -- qna �Խ��� �ۼ�����
        qna_update date,                    -- qna �Խ��� ��������
        qna_file varchar2(300),             -- qna �Խ��� ÷������
        qna_hit number(5),                  -- qna �Խ��� ��ȸ��
        qna_tag varchar2(20),                -- qna �Խ��� �±�(���Ӹ�)
        qna_code clob,
        qna_reply number(10)
    );

-- ���͵� ���� �Խ��� ���̺�
    create table study_group(
        study_num number(5) primary key,        -- ���͵� �Խñ� ��ȣ  
        study_writer references user_member(user_id) on delete cascade,   -- ���͵� �Խñ� �ۼ���
        study_title varchar2(200) not null,     -- ���͵� �Խñ� ����
        study_cont clob not null,     -- ���͵� �Խñ� ����
        study_date date,                        -- ���͵� �Խñ� �ۼ�����
        study_update date,                      -- ���͵� �Խñ� ��������
        study_people number(2),                 -- ���͵� �Խñ� �����ο�
        study_status varchar2(20),              -- ���͵� �Խñ� ������Ȳ
        study_start date,                       -- ���͵� ���� ���� ����
        study_end date,                         -- ���͵� ���� ���� ����
        study_file varchar2(300),               -- ���͵� �Խñ� ÷������
        study_hit number(5),                     -- ���͵� �Խñ� ��ȸ��
        study_reply number(10)
    );

-- Q&A �亯 ���̺� (
    create table qna_comment(
        qcomment_num number(5) primary key,      -- qna ��� ��ȣ
        qna_num references qna(qna_num) on delete cascade,  -- qna �Խñ� ��ȣ
        qcomment_writer references user_member(user_id) on delete cascade,    -- qna ��� �ۼ���
        qcomment_cont clob not null,   -- qna ��� ����
        qcomment_date date,                      -- qna ��� �ۼ�����
        qcomment_update date,                    -- qna ��� ��������
        qcomment_good number(5),                 -- qna ��� ���ƿ� ����
        qcommnet_file varchar2(300),
        qcomment_bad number(5),
        qcommnet_code clob              -- qna ��� ÷������
);



-- ���͵� ���� �亯 ���̺�
    create table study_comment(
        scomment_num number(5) primary key,     -- ���͵� ��� ��ȣ
        study_num references study_group(study_num) on delete cascade,    -- ���͵� �Խñ� ��ȣ
        scomment_writer references user_member(user_id) on delete cascade, -- ���͵� ��� �ۼ���
        scomment_cont varchar2(1000) not null,      -- ���͵� �Խñ� ����
        scomment_date date,                         -- ���͵� �Խñ� �ۼ�����
        scomment_update date                        -- ���͵� �Խñ� ��������
    );
    
-- ���ƿ� ���̺�
   create table good(
        good_num number(5) primary key,             -- ���ƿ� ��ȣ
        qna_num references qna(qna_num) on delete cascade,   -- qna �亯 ��ȣ
        user_id references user_member(user_id) on delete cascade	-- ���� ���̵�
    );
    
      create table bad(
        bad_num number(5) primary key,             -- ���ƿ� ��ȣ
        qna_num references qna(qna_num) on delete cascade,   -- qna �亯 ��ȣ
        user_id references user_member(user_id) on delete cascade	-- ���� ���̵�
    );
    
    -----
    
    <?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--><!-- The contents of this file will be loaded for each web application --><Context>
	<Resource
        name="jdbc/myoracle"
        auth="Container"
        type="javax.sql.DataSource"
        url="jdbc:oracle:thin:@localhost:1521:XE"
        driverClassName="oracle.jdbc.driver.OracleDriver"
        username="web"
        password="1234"
        maxActive="200"
        maxIdle="100"
    />

    <!-- Default set of monitored resources. If one of these changes, the    -->
    <!-- web application will be reloaded.                                   -->
    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <WatchedResource>WEB-INF/tomcat-web.xml</WatchedResource>
    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

    <!-- Uncomment this to disable session persistence across Tomcat restarts -->
    <!--
    <Manager pathname="" />
    -->
    
</Context>
