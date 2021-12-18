drop table if exists qrtz_fired_triggers;
drop table if exists qrtz_paused_trigger_grps;
drop table if exists qrtz_scheduler_state;
drop table if exists qrtz_locks;
drop table if exists qrtz_simple_triggers;
drop table if exists qrtz_simprop_triggers;
drop table if exists qrtz_cron_triggers;
drop table if exists qrtz_blob_triggers;
drop table if exists qrtz_triggers;
drop table if exists qrtz_job_details;
drop table if exists qrtz_calendars;


create table qrtz_job_details
  (
    sched_name varchar(120) NOT NULL,
    job_name  varchar(200) NOT NULL,
    job_group varchar(200) NOT NULL,
    description varchar(250) NULL,
    job_class_name   varchar(250) NOT NULL,
    is_durable varchar(1) NOT NULL,
    is_nonconcurrent varchar(1) NOT NULL,
    is_update_data varchar(1) NOT NULL,
    requests_recovery varchar(1) NOT NULL,
    job_data blob NULL,
    primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers
  (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    job_name  varchar(200) NOT NULL,
    job_group varchar(200) NOT NULL,
    description varchar(250) NULL,
    next_fire_time bigint(13) NULL,
    prev_fire_time bigint(13) NULL,
    priority integer NULL,
    trigger_state varchar(16) NOT NULL,
    trigger_type varchar(8) NOT NULL,
    start_time bigint(13) NOT NULL,
    end_time bigint(13) NULL,
    calendar_name varchar(200) NULL,
    misfire_instr smallint(2) NULL,
    job_data blob NULL,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group)
        references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers
  (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    repeat_count bigint(7) NOT NULL,
    repeat_interval bigint(12) NOT NULL,
    times_triggered bigint(10) NOT NULL,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_cron_triggers
  (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    cron_expression varchar(200) NOT NULL,
    time_zone_id varchar(80),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_simprop_triggers
  (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    str_prop_1 varchar(512) NULL,
    str_prop_2 varchar(512) NULL,
    str_prop_3 varchar(512) NULL,
    int_prop_1 int NULL,
    int_prop_2 int NULL,
    long_prop_1 bigint NULL,
    long_prop_2 bigint NULL,
    dec_prop_1 numeric(13,4) NULL,
    dec_prop_2 numeric(13,4) NULL,
    bool_prop_1 varchar(1) NULL,
    bool_prop_2 varchar(1) NULL,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_blob_triggers
  (
    sched_name varchar(120) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    blob_data blob NULL,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
        references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars
  (
    sched_name varchar(120) NOT NULL,
    calendar_name  varchar(200) NOT NULL,
    calendar blob NOT NULL,
    primary key (sched_name,calendar_name)
);

create table qrtz_paused_trigger_grps
  (
    sched_name varchar(120) NOT NULL,
    trigger_group  varchar(200) NOT NULL,
    primary key (sched_name,trigger_group)
);

create table qrtz_fired_triggers
  (
    sched_name varchar(120) NOT NULL,
    entry_id varchar(95) NOT NULL,
    trigger_name varchar(200) NOT NULL,
    trigger_group varchar(200) NOT NULL,
    instance_name varchar(200) NOT NULL,
    fired_time bigint(13) NOT NULL,
    sched_time bigint(13) NOT NULL,
    priority integer NOT NULL,
    state varchar(16) NOT NULL,
    job_name varchar(200) NULL,
    job_group varchar(200) NULL,
    is_nonconcurrent varchar(1) NULL,
    requests_recovery varchar(1) NULL,
    primary key (sched_name,entry_id)
);

create table qrtz_scheduler_state
  (
    sched_name varchar(120) NOT NULL,
    instance_name varchar(200) NOT NULL,
    last_checkin_time bigint(13) NOT NULL,
    checkin_interval bigint(13) NOT NULL,
    primary key (sched_name,instance_name)
);

create table qrtz_locks
  (
    sched_name varchar(120) NOT NULL,
    lock_name  varchar(40) NOT NULL,
    primary key (sched_name,lock_name)
);


commit;