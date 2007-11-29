/*
 * Copyright 1999-2004 Sun Microsystems, Inc.  All Rights Reserved.
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

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;

/**
 * An event which indicates a change to the <code>Component</code>
 * hierarchy to which a <code>Component</code> belongs.
 * <ul>
 * <li>Hierarchy Change Events (HierarchyListener)
 *     <ul>
 *     <li> addition of an ancestor
 *     <li> removal of an ancestor
 *     <li> hierarchy made displayable
 *     <li> hierarchy made undisplayable
 *     <li> hierarchy shown on the screen (both visible and displayable)
 *     <li> hierarchy hidden on the screen (either invisible or undisplayable)
 *     </ul>
 * <li>Ancestor Reshape Events (HierarchyBoundsListener)
 *     <ul>
 *     <li> an ancestor was resized
 *     <li> an ancestor was moved
 *     </ul>
 * </ul>
 * <p>
 * Hierarchy events are provided for notification purposes ONLY.
 * The AWT will automatically handle changes to the hierarchy internally so
 * that GUI layout and displayability works properly regardless of whether a
 * program is receiving these events or not.
 * <p>
 * This event is generated by a Container object (such as a Panel) when the
 * Container is added, removed, moved, or resized, and passed down the
 * hierarchy. It is also generated by a Component object when that object's
 * <code>addNotify</code>, <code>removeNotify</code>, <code>show</code>, or
 * <code>hide</code> method is called. ANCESTOR_MOVED and ANCESTOR_RESIZED
 * events are dispatched to every <code>HierarchyBoundsListener</code> or
 * <code>HierarchyBoundsAdapter</code> object which registered to receive
 * such events using the Component's <code>addHierarchyBoundsListener</code>
 * method. (<code>HierarchyBoundsAdapter</code> objects implement the <code>
 * HierarchyBoundsListener</code> interface.) HIERARCHY_CHANGED events are
 * dispatched to every <code>HierarchyListener</code> object which registered
 * to receive such events using the Component's <code>addHierarchyListener
 * </code> method. Each such listener object gets this <code>HierarchyEvent
 * </code> when the event occurs.
 *
 * @author	David Mendenhall
 * @version	1.20, 05/05/07
 * @see		HierarchyListener
 * @see		HierarchyBoundsAdapter
 * @see		HierarchyBoundsListener
 * @since 	1.3
 */
public class HierarchyEvent extends AWTEvent {
    /*
     * serialVersionUID
     */
    private static final long serialVersionUID = -5337576970038043990L;

    /**
     * Marks the first integer id for the range of hierarchy event ids.
     */
    public static final int HIERARCHY_FIRST = 1400; // 1300 used by sun.awt.windows.ModalityEvent

    /**
     * The event id indicating that modification was made to the
     * entire hierarchy tree.
     */
    public static final int HIERARCHY_CHANGED = HIERARCHY_FIRST;

    /**
     * The event id indicating an ancestor-Container was moved.
     */
    public static final int ANCESTOR_MOVED = 1 + HIERARCHY_FIRST;

    /**
     * The event id indicating an ancestor-Container was resized.
     */
    public static final int ANCESTOR_RESIZED = 2 + HIERARCHY_FIRST;

    /**
     * Marks the last integer id for the range of ancestor event ids.
     */
    public static final int HIERARCHY_LAST = ANCESTOR_RESIZED;

    /**
     * Indicates that the <code>HIERARCHY_CHANGED</code> event
     * was generated by a reparenting operation.
     */
    public static final int PARENT_CHANGED = 0x1;

    /**
     * Indicates that the <code>HIERARCHY_CHANGED</code> event
     * was generated due to a change in the displayability
     * of the hierarchy.  To discern the
     * current displayability of the hierarchy, call
     * <code>Component.isDisplayable</code>. Displayability changes occur
     * in response to explicit or implicit calls to
     * <code>Component.addNotify</code> and
     * <code>Component.removeNotify</code>.
     *
     * @see java.awt.Component#isDisplayable()
     * @see java.awt.Component#addNotify()
     * @see java.awt.Component#removeNotify()
     */
    public static final int DISPLAYABILITY_CHANGED = 0x2;

    /**
     * Indicates that the <code>HIERARCHY_CHANGED</code> event
     * was generated due to a change in the showing state
     * of the hierarchy. To discern the
     * current showing state of the hierarchy, call
     * <code>Component.isShowing</code>. Showing state changes occur
     * when either the displayability or visibility of the
     * hierarchy occurs. Visibility changes occur in response to explicit
     * or implicit calls to <code>Component.show</code> and
     * <code>Component.hide</code>.
     *
     * @see java.awt.Component#isShowing()
     * @see java.awt.Component#addNotify()
     * @see java.awt.Component#removeNotify()
     * @see java.awt.Component#show()
     * @see java.awt.Component#hide()
     */
    public static final int SHOWING_CHANGED = 0x4;

    Component changed;
    Container changedParent;
    long      changeFlags;

    /**
     * Constructs an <code>HierarchyEvent</code> object to identify a
     * change in the <code>Component</code> hierarchy.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source          the <code>Component</code> object that
     *                        originated the event
     * @param id              an integer indicating the type of event
     * @param changed         the <code>Component</code> at the top of
     *                        the hierarchy which was changed
     * @param changedParent   the parent of <code>changed</code>; this
     *                        may be the parent before or after the
     *                        change, depending on the type of change
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public HierarchyEvent(Component source, int id, Component changed,
			  Container changedParent) {
        super(source, id);
	this.changed = changed;
	this.changedParent = changedParent;
    }

    /**
     * Constructs an <code>HierarchyEvent</code> object to identify
     * a change in the <code>Component</code> hierarchy.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * @param source          the <code>Component</code> object that
     *                        originated the event
     * @param id              an integer indicating the type of event
     * @param changed         the <code>Component</code> at the top
     *                        of the hierarchy which was changed
     * @param changedParent   the parent of <code>changed</code>; this
     *                        may be the parent before or after the
     *                        change, depending on the type of change
     * @param changeFlags     a bitmask which indicates the type(s) of
     *                        <code>HIERARCHY_CHANGED</code> events
     *                        represented in this event object
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public HierarchyEvent(Component source, int id, Component changed,
			  Container changedParent, long changeFlags) {
        super(source, id);
	this.changed = changed;
	this.changedParent = changedParent;
	this.changeFlags = changeFlags;
    }

    /**
     * Returns the originator of the event.
     *
     * @return the <code>Component</code> object that originated 
     * the event, or <code>null</code> if the object is not a 
     * <code>Component</code>.  
     */
    public Component getComponent() {
        return (source instanceof Component) ? (Component)source : null;
    }

    /**
     * Returns the Component at the top of the hierarchy which was
     * changed.
     *
     * @return the changed Component
     */
    public Component getChanged() {
        return changed;
    }

    /**
     * Returns the parent of the Component returned by <code>
     * getChanged()</code>. For a HIERARCHY_CHANGED event where the
     * change was of type PARENT_CHANGED via a call to <code>
     * Container.add</code>, the parent returned is the parent
     * after the add operation. For a HIERARCHY_CHANGED event where
     * the change was of type PARENT_CHANGED via a call to <code>
     * Container.remove</code>, the parent returned is the parent
     * before the remove operation. For all other events and types,
     * the parent returned is the parent during the operation.
     *
     * @return the parent of the changed Component
     */
    public Container getChangedParent() {
        return changedParent;
    }

    /**
     * Returns a bitmask which indicates the type(s) of
     * HIERARCHY_CHANGED events represented in this event object.
     * The bits have been bitwise-ored together.
     *
     * @return the bitmask, or 0 if this is not an HIERARCHY_CHANGED
     * event
     */
    public long getChangeFlags() {
        return changeFlags;
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
	switch(id) {
	  case ANCESTOR_MOVED:
	      typeStr = "ANCESTOR_MOVED ("+changed+","+changedParent+")";
	      break;
	  case ANCESTOR_RESIZED:
	      typeStr = "ANCESTOR_RESIZED ("+changed+","+changedParent+")";
	      break;
	  case HIERARCHY_CHANGED: {
	      typeStr = "HIERARCHY_CHANGED (";
	      boolean first = true;
	      if ((changeFlags & PARENT_CHANGED) != 0) {
		  first = false;
		  typeStr += "PARENT_CHANGED";
	      }
	      if ((changeFlags & DISPLAYABILITY_CHANGED) != 0) {
		  if (first) {
		      first = false;
		  } else {
		      typeStr += ",";
		  }
		  typeStr += "DISPLAYABILITY_CHANGED";
	      }
	      if ((changeFlags & SHOWING_CHANGED) != 0) {
		  if (first) {
		      first = false;
		  } else {
		      typeStr += ",";
		  }
		  typeStr += "SHOWING_CHANGED";
	      }
	      if (!first) {
		  typeStr += ",";
	      }
	      typeStr += changed + "," + changedParent + ")";
	      break;
	  }
	  default:
	      typeStr = "unknown type";
	}
	return typeStr;
    }
}
