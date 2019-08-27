package com.billow.base.workflow.diagram;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成流程图片.
 * <p>
 * 如果要更改图片生成的逻辑，覆盖默认的内部方法即可
 *
 * @author LiuYongTao
 * @date 2019/8/27 9:11
 */
public class CustomProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CustomProcessDiagramGenerator.class);

    public CustomProcessDiagramGenerator() {
        this(1.0);
    }

    public CustomProcessDiagramGenerator(final double scaleFactor) {
        super(scaleFactor);
    }

    /**
     * 绘制流程图，当前节点红色高亮.
     *
     * @param bpmnModel
     * @param imageType
     * @param highLightedActivities
     * @param highLightedFlows
     * @param currentActivities
     * @return
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType,
                                       List<String> highLightedActivities, List<String> highLightedFlows,
                                       List<String> currentActivities) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, "宋体",
                "宋体", "宋体", null, 1.0, currentActivities);
    }

    /**
     * 绘制流程图，当前节点红色高亮.
     *
     * @param bpmnModel
     * @param imageType
     * @param highLightedActivities
     * @param highLightedFlows
     * @param activityFontName
     * @param labelFontName
     * @param annotationFontName
     * @param currentActivities
     * @return java.io.InputStream
     * @author LiuYongTao
     * @date 2019/8/27 9:11
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType,
                                       List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName,
                                       String labelFontName, String annotationFontName, List<String> currentActivities) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows,
                activityFontName, labelFontName, annotationFontName, null, 1.0, currentActivities);
    }

    /**
     * 绘制流程图，当前节点红色高亮.
     *
     * @param bpmnModel
     * @param imageType
     * @param highLightedActivities
     * @param highLightedFlows
     * @param scaleFactor
     * @param currentActivities
     * @return
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType,
                                       List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName,
                                       String labelFontName, String annotationFontName, ClassLoader customClassLoader,
                                       double scaleFactor, List<String> currentActivities) {
        DefaultProcessDiagramCanvas processDiagramCanvas =
                generateProcessDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows,
                        activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor);
        // 绘制当前节点
        drawCurrentActivity(bpmnModel, currentActivities, processDiagramCanvas);

        return processDiagramCanvas.generateImage(imageType);
    }

    /**
     * @param bpmnModel
     * @param imageType
     * @param activityFontName
     * @param labelFontName
     * @param annotationFontName
     * @param customClassLoader
     * @return org.activiti.image.impl.DefaultProcessDiagramCanvas
     * @author LiuYongTao
     * @date 2019/8/27 9:10
     */
    protected static DefaultProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
                                                                          String activityFontName, String labelFontName,
                                                                          String annotationFontName, ClassLoader customClassLoader) {
        DefaultProcessDiagramCanvas processDiagramCanvas =
                DefaultProcessDiagramGenerator.initProcessDiagramCanvas(bpmnModel, imageType,
                        activityFontName, labelFontName, annotationFontName, customClassLoader);
        try {
            int canvasWidth = (Integer) getFieldValue(processDiagramCanvas, "canvasWidth");
            int canvasHeight = (Integer) getFieldValue(processDiagramCanvas, "canvasHeight");
            int minX = (Integer) getFieldValue(processDiagramCanvas, "minX");
            int minY = (Integer) getFieldValue(processDiagramCanvas, "minY");
            return new CustomProcessDiagramCanvas(canvasWidth, canvasHeight, minX, minY, imageType,
                    activityFontName, labelFontName, annotationFontName, customClassLoader);
        } catch (IllegalAccessException | NumberFormatException | NoSuchFieldException | SecurityException e) {
            return processDiagramCanvas;
        }
    }

    /**
     * 反射获取指定属性的值
     *
     * @param processDiagramCanvas
     * @param fieldName
     * @return java.lang.Object
     * @author LiuYongTao
     * @date 2019/8/27 9:06
     */
    private static Object getFieldValue(DefaultProcessDiagramCanvas processDiagramCanvas, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = processDiagramCanvas.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(processDiagramCanvas);
    }

    /**
     * 生成流程图，返回流程图画布
     *
     * @param bpmnModel
     * @param imageType
     * @param highLightedActivities
     * @param highLightedFlows
     * @param activityFontName
     * @param labelFontName
     * @param annotationFontName
     * @param customClassLoader
     * @param scaleFactor
     * @return org.activiti.image.impl.DefaultProcessDiagramCanvas
     * @author LiuYongTao
     * @date 2019/8/27 9:05
     */
    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType,
                                                                 List<String> highLightedActivities, List<String> highLightedFlows,
                                                                 String activityFontName, String labelFontName, String annotationFontName,
                                                                 ClassLoader customClassLoader, double scaleFactor) {
        super.prepareBpmnModel(bpmnModel);
        DefaultProcessDiagramCanvas processDiagramCanvas =
                initProcessDiagramCanvas(bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);

        // Draw pool shape, if process is participant in collaboration
        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
        }

        // Draw lanes
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane lane : process.getLanes()) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
            }
        }

        // Draw activities and their sequence-flows
        for (Process process : bpmnModel.getProcesses()) {
            for (FlowNode flowNode : process.findFlowElementsOfType(FlowNode.class)) {
                drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities,
                        highLightedFlows, scaleFactor);
            }
        }

        // Draw artifacts
        for (Process process : bpmnModel.getProcesses()) {
            for (Artifact artifact : process.getArtifacts()) {
                drawArtifact(processDiagramCanvas, bpmnModel, artifact);
            }
            List<SubProcess> subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
            if (subProcesses != null) {
                for (SubProcess subProcess : subProcesses) {
                    for (Artifact subProcessArtifact : subProcess.getArtifacts()) {
                        drawArtifact(processDiagramCanvas, bpmnModel, subProcessArtifact);
                    }
                }
            }
        }
        return processDiagramCanvas;
    }

    /**
     * 绘制当前节点
     *
     * @param bpmnModel
     * @param currentActivities
     * @param processDiagramCanvas
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 9:05
     */
    private void drawCurrentActivity(BpmnModel bpmnModel, List<String> currentActivities, DefaultProcessDiagramCanvas processDiagramCanvas) {
        logger.debug("drawCurrentActivity: {}", currentActivities);
        if (currentActivities == null || currentActivities.size() == 0) {
            return;
        }
        for (Process process : bpmnModel.getProcesses()) {
            for (FlowNode flowNode : process.findFlowElementsOfType(FlowNode.class)) {
                if (currentActivities.contains(flowNode.getId())) {
                    // 当前节点红色显示
                    GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
                    ((CustomProcessDiagramCanvas) processDiagramCanvas).drawHighLightRed(
                            (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
                            (int) graphicInfo.getHeight());
                }
            }
        }
    }
}