CREATE TABLE appointment
(
    appointment_id      BIGINT NOT NULL,
    accepted_by_teacher BIT(1) NOT NULL,
    accepted_by_student BIT(1) NOT NULL,
    teacher_id          VARCHAR(255) NULL,
    student_id          VARCHAR(255) NULL,
    classroom_id        VARCHAR(255) NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (appointment_id)
);

CREATE TABLE classroom
(
    name          VARCHAR(255) NOT NULL,
    abteilung     INT          NOT NULL,
    class_teacher VARCHAR(255) NULL,
    CONSTRAINT pk_classroom PRIMARY KEY (name)
);

CREATE TABLE missed_exam
(
    id          BIGINT NOT NULL,
    student_id  VARCHAR(255) NULL,
    teacher_id  VARCHAR(255) NULL,
    classrom_id VARCHAR(255) NULL,
    missed_at   datetime NULL,
    CONSTRAINT pk_missedexam PRIMARY KEY (id)
);

CREATE TABLE notice
(
    id                     BIGINT NOT NULL,
    exam_id                BIGINT NOT NULL,
    student_id             VARCHAR(255) NULL,
    approved_by_teacher_id VARCHAR(255) NULL,
    created_at             datetime NULL,
    CONSTRAINT pk_notice PRIMARY KEY (id)
);

CREATE TABLE session
(
    session_token VARCHAR(255) NOT NULL,
    teacher       VARCHAR(255) NULL,
    student       VARCHAR(255) NULL,
    created       datetime NULL,
    updated       datetime NULL,
    CONSTRAINT pk_session PRIMARY KEY (session_token)
);

CREATE TABLE student
(
    id           VARCHAR(255) NOT NULL,
    vorname      VARCHAR(255) NULL,
    nachname     VARCHAR(255) NULL,
    email        VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    classroom_id VARCHAR(255) NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);

CREATE TABLE teacher
(
    id        VARCHAR(255) NOT NULL,
    vorname   VARCHAR(255) NULL,
    nachname  VARCHAR(255) NULL,
    email     VARCHAR(255) NULL,
    password  VARCHAR(255) NULL,
    abteilung INT          NOT NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (id)
);

CREATE TABLE teacher_classroom_map
(
    id           BIGINT NOT NULL,
    teacher_id   VARCHAR(255) NULL,
    classroom_id VARCHAR(255) NULL,
    is_new       BIT(1) NOT NULL,
    CONSTRAINT pk_teacherclassroommap PRIMARY KEY (id)
);