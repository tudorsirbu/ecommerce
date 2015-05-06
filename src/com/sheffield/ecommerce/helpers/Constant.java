package com.sheffield.ecommerce.helpers;

import com.sheffield.ecommerce.models.User;

/**
 * Java Bean class which provides constants to the view pages 
 *
 */

public class Constant {
	/**
	 * Get the author role id
	 * @return The author role id
	 */
	public int getAuthor() {
		return User.AUTHOR;
	}
	
	/**
	 * Get the editor role id
	 * @return The editor role id
	 */
	public int getEditor() {
		return User.EDITOR;
	}
}
