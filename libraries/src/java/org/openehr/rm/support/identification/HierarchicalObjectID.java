/*
 * component:   "openEHR Reference Implementation"
 * description: "Class HierarchicalObjectID"
 * keywords:    "common"
 *
 * author:      "Rong Chen <rong@acode.se>"
 * support:     "Acode HB <support@acode.se>"
 * copyright:   "Copyright (c) 2004 Acode HB, Sweden"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/TRUNK/libraries/src/java/org/openehr/rm/support/identification/HierarchicalObjectID.java $"
 * revision:    "$LastChangedRevision: 2 $"
 * last_change: "$LastChangedDate: 2005-10-12 22:20:08 +0100 (Wed, 12 Oct 2005) $"
 */
package org.openehr.rm.support.identification;

import org.apache.commons.lang.StringUtils;

/**
 * Hierarhical object identifiers.
 * <p/>
 * The syntax of the value attribute is as follows:
 * <blockquote>root::extension</blockquote>
 * with the extension bit optional
 * </p>
 * <p>Instances of this class are immutable.</p>
 *
 * @author Yin Su Lim
 * @version 1.0
 */
public class HierarchicalObjectID extends ObjectID {

    /**
     * Create HierarchicalObjectID by string value
     *
     * @param value
     * @throws IllegalArgumentException if value is null
     *                                  
     */
    public HierarchicalObjectID(String value) {
        super(value);
        loadValue(value);
    }

	/**
     * Constructs a HierarchicalObjectID by root and extension
     *
     * @param root
     * @param extension
     * @throws IllegalArgumentException if root is null
     */
    public HierarchicalObjectID(String root, String extension) {
    		super(extension == null ? root : root + "::" + extension);
    }
 
	/**
     * Constructs a HierarchicalObjectID by root (UID) and extension
     *
     * @param root
     * @param extension
     * @throws IllegalArgumentException if root is null 
     */
    public HierarchicalObjectID(UID root, String extension) {
    		if (root != null) { // must catch null root, or will get nullpointerexception
    			if(StringUtils.isEmpty(extension)) {
    				super.setValue(root.getValue());
    			} else {
    				super.setValue(root.getValue() + "::" + extension);
    			}
    			this.root = root;
    			this.extension = extension;
    		} else {
    			throw new IllegalArgumentException("null root");
    		}
    }
 
    /** 
     * Load value into root and extension
     * @param value
     */
    private void loadValue(String value) {
        int doubleColons = value.indexOf("::");
        // Check for root segment
        if (doubleColons == 0) {
			throw new IllegalArgumentException("bad format, missing root");
		}
        //the patterns below are for sorting only, the correct syntax
        //checking is handled by the UID sublcasses.
        if (value.matches("(\\d)+(-(\\d)+)*")) { //pattern for UUID
        		root = new UUID(value.substring(0, doubleColons));
        } else if (value.matches("(\\d)+(\\.(\\d)+)*")) { //for ISO_OID
        	 	root = new ISO_OID(value.substring(0, doubleColons));
        } else if (value.matches("(\\w)+(\\.(\\w)+)*")){ //for InternetID, 
        		root = new InternetID(value.substring(0, doubleColons));
        } else {
        		throw new IllegalArgumentException("wrong format");
        }
        if( 0 < doubleColons && doubleColons < (value.length() - 2)) {
        		extension = value.substring(doubleColons + 2);
        }           
    }

    /**
     * The identifier of the conceptual namespace in which the object exists, 
     * within the identification scheme
     * 
     * @return root
     */
    public UID root() {
    		return root;
    }
    
    /**
     * True if there is an extension part
     * 
     */
    public boolean hasExtension() {
    		return extension != null;
    }
    
    /**
     * A local identifier of the object within the context of the 
     * root identifier
     * 
     * @return extension
     */
    public String extension() {
    		return extension;
    }
    
    // POJO start
    HierarchicalObjectID() {
    }

    protected void setValue(String value) {
        loadValue(value);
        super.setValue(value);
    }
    // POJO end

    /* fields */
    private UID root;   // mandatory
    private String extension;
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is HierarchicalObjectID.java
 *
 *  The Initial Developer of the Original Code is Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */