/* @(#)SeparatorLineFigure.java
 * Copyright © The authors and contributors of JHotDraw. MIT License.
 */

package org.jhotdraw.samples.pert.figures;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Geom;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;
/**
 * A horizontal line with a preferred size of 1,1.
 *
 * @author  Werner Randelshofer
 * @version $Id$
 */
public class SeparatorLineFigure 
extends RectangleFigure {
    private static final long serialVersionUID = 1L;

    /** Creates a new instance. */
    public SeparatorLineFigure() {
    }

    @Override
    public Dimension2DDouble getPreferredSize() {
        double width = Math.ceil(STROKE_WIDTH.get(this));
        return new Dimension2DDouble(width, width);
    }

    @Override
    protected void drawFill(Graphics2D g) {
        // no fill
    }
    @Override
    protected void drawStroke(Graphics2D g) {
        Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
        double grow = AttributeKeys.getPerpendicularDrawGrowth(this);
       Geom.grow(r, grow, grow);

        g.draw(new Line2D.Double(r.x,r.y,r.x+r.width-1,r.y));
    }
}