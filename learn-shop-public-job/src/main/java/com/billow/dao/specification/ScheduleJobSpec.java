package com.billow.dao.specification;

import com.billow.pojo.po.ScheduleJobPo;
import com.billow.pojo.vo.ScheduleJobVo;
import com.billow.tools.utlis.PageUtil;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScheduleJobSpec implements Specification<ScheduleJobPo> {

    private ScheduleJobPo scheduleJobPo;

    public ScheduleJobSpec(ScheduleJobVo scheduleJobVo) {
        this.scheduleJobPo = PageUtil.convert(scheduleJobVo, ScheduleJobPo.class);
    }

    @Override
    public Predicate toPredicate(Root<ScheduleJobPo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> list = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(scheduleJobPo.getJobName())) {
            list.add(criteriaBuilder.like(root.get("jobName").as(String.class), "%" + scheduleJobPo.getJobName() + "%"));
        }

        Predicate[] p = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(p));

    }

    public List<Predicate> genPredicates(Root<ScheduleJobPo> root, CriteriaBuilder criteriaBuilder, ScheduleJobPo scheduleJobPo) throws Exception {
        List<Predicate> list = new ArrayList<>();

        Class<? extends ScheduleJobPo> clazz = scheduleJobPo.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (ToolsUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                String fieldName = field.getName();
                Object fieldValue = field.get(scheduleJobPo);
                if (ToolsUtils.isNotEmpty(fieldValue)) {
                    list.add(criteriaBuilder.equal(root.get(fieldName).as(type), fieldValue));
                }
            }
        }
        return null;
    }
}
