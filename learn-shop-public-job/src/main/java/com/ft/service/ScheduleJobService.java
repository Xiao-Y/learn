package com.ft.service;

import com.ft.model.expand.ScheduleJobDto;

import java.util.List;

/**
 * 自动任务
 * 
 * @author liuyongtao
 * 
 * @date 2017年5月12日 上午8:43:34
 */
public interface ScheduleJobService{

	/**
	 * 查询所有自动任务
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @param scheduleJobDto
	 * @return
	 * 
	 * @date 2017年5月12日 上午8:43:46
	 */
	List<ScheduleJobDto> selectAll(ScheduleJobDto scheduleJobDto);

	/**
	 * 启用、禁用自动任务
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @param jobId
	 *            自动任务id
	 * @param jobStatus
	 *            任务状态，1-启用，0-禁用
	 * @return
	 * 
	 * @date 2017年5月11日 下午2:58:16
	 */
	void updateJobStatus(ScheduleJobDto dto);

	ScheduleJobDto selectByPrimaryKey(ScheduleJobDto dto);

	void updateByPrimaryKeySelective(ScheduleJobDto dto);

	void deleteByPrimaryKey(ScheduleJobDto dto);

	void insert(ScheduleJobDto scheduleJobDto);
}
