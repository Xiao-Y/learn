package com.billow.job.dao.specification;

import com.billow.common.jpa.utils.QueryUtils;
import com.billow.job.pojo.po.ScheduleJobPo;
import com.billow.job.pojo.vo.ScheduleJobVo;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 */
public class ScheduleJobSpec implements Specification<ScheduleJobPo> {

    private ScheduleJobPo scheduleJobPo;

    public ScheduleJobSpec(ScheduleJobVo scheduleJobVo) {
        this.scheduleJobPo = ConvertUtils.convert(scheduleJobVo, ScheduleJobPo.class);
    }

    @Override
    public Predicate toPredicate(Root<ScheduleJobPo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> all = new ArrayList<>();

        String jobName = scheduleJobPo.getJobName();
        if (ToolsUtils.isNotEmpty(jobName)) {
            scheduleJobPo.setJobName(null);
            Predicate predicate = criteriaBuilder.like(root.get("jobName").as(String.class), QueryUtils.aLike(jobName));
            all.add(predicate);
        }

        String jobGroup = scheduleJobPo.getJobGroup();
        if (ToolsUtils.isNotEmpty(jobGroup)) {
            scheduleJobPo.setJobGroup(null);
            Predicate predicate = criteriaBuilder.like(root.get("jobGroup").as(String.class), QueryUtils.aLike(jobGroup));
            all.add(predicate);
        }

        String methodName = scheduleJobPo.getMethodName();
        if (ToolsUtils.isNotEmpty(methodName)) {
            scheduleJobPo.setMethodName(null);
            Predicate predicate = criteriaBuilder.like(root.get("methodName").as(String.class), QueryUtils.aLike(methodName));
            all.add(predicate);
        }

        List<Predicate> predicateList = QueryUtils.getPredicates(root, criteriaBuilder, scheduleJobPo);
        if (ToolsUtils.isNotEmpty(predicateList)) {
            all.addAll(predicateList);
        }

        Predicate[] predicates = new Predicate[all.size()];
        all.toArray(predicates);
        return criteriaBuilder.and(predicates);

    }
}
