CREATE OR REPLACE VIEW `learn`.`v_mytasklist` AS SELECT
	`rt`.`EXECUTION_ID_` AS `executionId`,
	`rt`.`ASSIGNEE_` AS `assignee`,
	'' AS `groupId`,
	`rt`.`NAME_` AS `taskName`,
	`rt`.`ID_` AS `taskId`,
	'0' AS `claimStatus`,
	`rt`.`SUSPENSION_STATE_` AS `suspensionStatus`,
	`sai`.`id` AS `id`,
	`sai`.`proc_def_id` AS `procDefId`,
	`sai`.`proc_inst_id` AS `procInstId`,
	`sai`.`is_end` AS `isEnd`,
	`sai`.`apply_type` AS `applyType`,
	`sai`.`apply_user_code` AS `applyUserCode`,
	`sai`.`vo_clazz` AS `voClazz`,
	`sai`.`valid_ind` AS `validInd`,
	`sai`.`create_time` AS `createTime`,
	`sai`.`creator_code` AS `creatorCode`,
	`sai`.`update_time` AS `updateTime`,
	`sai`.`updater_code` AS `updaterCode`
FROM
	( `sys_apply_info` `sai` LEFT JOIN `ACT_RU_TASK` `rt` ON ( ( `sai`.`proc_inst_id` = `rt`.`PROC_INST_ID_` ) ) )
WHERE
	( ( `sai`.`is_end` = FALSE ) AND ( `rt`.`ASSIGNEE_` IS NOT NULL ) ) UNION ALL
SELECT
	`rt`.`EXECUTION_ID_` AS `executionId`,
	`I`.`USER_ID_` AS `assignee`,
	`I`.`GROUP_ID_` AS `groupId`,
	`rt`.`NAME_` AS `taskName`,
	`rt`.`ID_` AS `taskId`,
	'1' AS `claimStatus`,
	`rt`.`SUSPENSION_STATE_` AS `suspensionStatus`,
	`sai`.`id` AS `id`,
	`sai`.`proc_def_id` AS `procDefId`,
	`sai`.`proc_inst_id` AS `procInstId`,
	`sai`.`is_end` AS `isEnd`,
	`sai`.`apply_type` AS `applyType`,
	`sai`.`apply_user_code` AS `applyUserCode`,
	`sai`.`vo_clazz` AS `voClazz`,
	`sai`.`valid_ind` AS `validInd`,
	`sai`.`create_time` AS `createTime`,
	`sai`.`creator_code` AS `creatorCode`,
	`sai`.`update_time` AS `updateTime`,
	`sai`.`updater_code` AS `updaterCode`
FROM
	(
	( `sys_apply_info` `sai` LEFT JOIN `ACT_RU_TASK` `rt` ON ( ( `sai`.`proc_inst_id` = `rt`.`PROC_INST_ID_` ) ) )
	LEFT JOIN `ACT_RU_IDENTITYLINK` `I` ON ( ( `I`.`TASK_ID_` = `rt`.`ID_` ) )
	)
WHERE
	( ( `sai`.`is_end` = FALSE ) AND isnull( `rt`.`ASSIGNEE_` ) AND ( `I`.`TYPE_` = 'candidate' ) );