package com.billow.base.workflow.diagram;

import org.activiti.image.impl.DefaultProcessDiagramCanvas;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

/**
 * 自定义流程画布
 *
 * @author LiuYongTao
 * @date 2019/8/27 9:12
 */
public class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas {

    // 增加红色高亮，用于显示最后一个节点
    private static Color HIGHLIGHT_COLOR_RED = Color.red;

    static {
        // 修改默认的高亮色为绿色
        HIGHLIGHT_COLOR = Color.green;
    }

    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType,
                                      String activityFontName, String labelFontName, String annotationFontName,
                                      ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName,
                annotationFontName, customClassLoader);
    }

    /**
     * 用红色显示高亮
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return void
     * @author LiuYongTao
     * @date 2019/8/27 9:12
     */
    public void drawHighLightRed(int x, int y, int width, int height) {
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(HIGHLIGHT_COLOR_RED);
        g.setStroke(THICK_TASK_BORDER_STROKE);

        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);

        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

}