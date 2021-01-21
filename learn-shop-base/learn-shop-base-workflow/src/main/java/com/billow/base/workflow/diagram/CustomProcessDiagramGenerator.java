package com.billow.base.workflow.diagram;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

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

    // 中文显示的是口口口，设置字体就好了
    private static final String FONT = "黑体";
    private static final Double scaleFactor = 1.0;
    private static final String imageType = "png";


    public CustomProcessDiagramGenerator() {
        this(scaleFactor);
    }

    public CustomProcessDiagramGenerator(final double scaleFactor) {
        super(scaleFactor);
    }

    @Override
    public InputStream generatePngDiagram(BpmnModel bpmnModel) {
        return generateDiagram(bpmnModel, imageType, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    public InputStream generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities,
                                       List<String> highLightedFlows, List<String> currentActivities) {
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, currentActivities);
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
        return generateDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, FONT, FONT, FONT, currentActivities);
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
                activityFontName, labelFontName, annotationFontName, null, scaleFactor, currentActivities);
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

    @Override
    protected void drawActivity(DefaultProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode, List<String> highLightedActivities, List<String> highLightedFlows, double scaleFactor) {
        ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
        if (drawInstruction != null) {

            drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

            // Gather info on the multi instance marker
            boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
            if (flowNode instanceof Activity) {
                Activity activity = (Activity) flowNode;
                MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
                if (multiInstanceLoopCharacteristics != null) {
                    multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
                    multiInstanceParallel = !multiInstanceSequential;
                }
            }

            // Gather info on the collapsed marker
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            if (flowNode instanceof SubProcess) {
                collapsed = graphicInfo.getExpanded() != null && !graphicInfo.getExpanded();
            } else if (flowNode instanceof CallActivity) {
                collapsed = true;
            }

            if (scaleFactor == 1.0) {
                // Actually draw the markers
                processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(),
                        multiInstanceSequential, multiInstanceParallel, collapsed);
            }

            // Draw highlighted activities
            if (highLightedActivities.contains(flowNode.getId())) {
                drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
            }

        }

        // Outgoing transitions of activity
        for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
            boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
            String defaultFlow = null;
            if (flowNode instanceof Activity) {
                defaultFlow = ((Activity) flowNode).getDefaultFlow();
            } else if (flowNode instanceof Gateway) {
                defaultFlow = ((Gateway) flowNode).getDefaultFlow();
            }

            boolean isDefault = false;
            if (defaultFlow != null && defaultFlow.equalsIgnoreCase(sequenceFlow.getId())) {
                isDefault = true;
            }
            boolean drawConditionalIndicator = sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);

            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();
            FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
            FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
            if (graphicInfoList != null && graphicInfoList.size() > 0) {
                graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);
                int xPoints[] = new int[graphicInfoList.size()];
                int yPoints[] = new int[graphicInfoList.size()];

                for (int i = 1; i < graphicInfoList.size(); i++) {
                    GraphicInfo graphicInfo = graphicInfoList.get(i);
                    GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

                    if (i == 1) {
                        xPoints[0] = (int) previousGraphicInfo.getX();
                        yPoints[0] = (int) previousGraphicInfo.getY();
                    }
                    xPoints[i] = (int) graphicInfo.getX();
                    yPoints[i] = (int) graphicInfo.getY();

                }

                processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault, highLighted, scaleFactor);

                // Draw sequenceflow label
                GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
                if (labelGraphicInfo != null) {
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
                } else {// 解决连线上不显示名称
                    GraphicInfo lineCenter = getLineCenter(graphicInfoList);
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false);
                }
            }
        }

        // Nested elements
        if (flowNode instanceof FlowElementsContainer) {
            for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
                if (nestedFlowElement instanceof FlowNode) {
                    drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement,
                            highLightedActivities, highLightedFlows, scaleFactor);
                }
            }
        }
    }

    private static void drawHighLight(DefaultProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
        processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());

    }
}