/*
 * Copyright 1996-2003 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package java.awt.event;

import java.awt.Component;
import java.awt.Event;
import java.awt.Rectangle;

/**
 * The component-level paint event.
 * This event is a special type which is used to ensure that
 * paint/update method calls are serialized along with the other
 * events delivered from the event queue.  This event is not
 * designed to be used with the Event Listener model; programs
 * should continue to override paint/update methods in order
 * render themselves properly.
 *
 * @author Amy Fowler
 * @version 1.27, 05/05/07
 * @since 1.1
 */
public class PaintEvent extends ComponentEvent {

    /**
     * Marks the first integer id for the range of paint event ids.
     */    
    public static final int PAINT_FIRST		= 800;

    /**
     * Marks the last integer id for the range of paint event ids.
     */
    public static final int PAINT_LAST		= 801;

    /**
     * The paint event type.  
     */
    public static final int PAINT = PAINT_FIRST;

    /**
     * The update event type.  
     */
    public static final int UPDATE = PAINT_FIRST + 1; //801

    /**
     * This is the rectangle that represents the area on the source
     * component that requires a repaint.
     * This rectangle should be non null.
     *
     * @serial
     * @see java.awt.Rectangle
     * @see #setUpdateRect(Rectangle)
     * @see #getUpdateRect()
     */
    Rectangle updateRect;

    /*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = 1267492026433337593L;

    /**
     * Constructs a <code>PaintEvent</code> object with the specified
     * source component and type.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source     the object where the event originated
     * @param id         the event type
     * @param updateRect the rectangle area which needs to be repainted
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public PaintEvent(Component source, int id, Rectangle updateRect) {
        super(source, id);
        this.updateRect = updateRect;
    }

    /**
     * Returns the rectangle representing the area which needs to be
     * repainted in response to this event.
     */
    public Rectangle getUpdateRect() {
        return updateRect;
    }

    /**
     * Sets the rectangle representing the area which needs to be
     * repainted in response to this event.
     * @param updateRect the rectangle area which needs to be repainted
     */
    public void setUpdateRect(Rectangle updateRect) {
        this.updateRect = updateRect;
    }

    public String paramString() {
        String typeStr;
        switch(id) {
          case PAINT:
              typeStr = "PAINT";
              break;
          case UPDATE:
              typeStr = "UPDATE";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr + ",updateRect="+(updateRect != null ? updateRect.toString() : "null");
    }
}
