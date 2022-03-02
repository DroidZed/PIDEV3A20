package tn.nebulagaming.services;

import java.sql.Connection;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author ASUS
 */
public abstract class ServiceMotherClass {

protected static final int SESSION_USER = GlobalConfig
					.getInstance().getSession();
					

protected static final Connection conn = GlobalConfig
					.getInstance().getCONNECTION();
					
protected String TABLE_NAME;

}
