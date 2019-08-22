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
        ScheduleJobPo convert = ConvertUtils.convert(scheduleJobPo, ScheduleJobPo.class);
        List<Predicate> all = new ArrayList<>();

        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "jobName");
        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "jobGroup");
        QueryUtils.getPredicateALike(all, root, criteriaBuilder, convert, "methodName");

        List<Predicate> predicateList = QueryUtils.getPredicateEqual(root, criteriaBuilder, convert);
        if (ToolsUtils.isNotEmpty(predicateList)) {
            all.addAll(predicateList);
        }

        return criteriaBuilder.and(QueryUtils.converListToArray(all));
    }
}
