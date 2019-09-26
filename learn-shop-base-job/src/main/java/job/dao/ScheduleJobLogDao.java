package job.dao;

import job.pojo.po.ScheduleJobLogPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScheduleJobLogDao extends JpaRepository<ScheduleJobLogPo, Long>, JpaSpecificationExecutor<ScheduleJobLogPo> {
}
