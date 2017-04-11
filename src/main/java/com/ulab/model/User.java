
package com.ulab.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
@TableBind(tableName = "lhjx_specific_code",pkName="id")
public class User extends Model<User> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final User dao = new User();
}
