package online.smyhw.localnet.plugins.ClientDataTools;

import online.smyhw.localnet.LN;
import online.smyhw.localnet.message;
import online.smyhw.localnet.command.cmdManager;
import online.smyhw.localnet.data.DataManager;
import online.smyhw.localnet.data.config;
import online.smyhw.localnet.event.ChatINFO_Event;
import online.smyhw.localnet.event.EventManager;
import online.smyhw.localnet.lib.CommandFJ;
import online.smyhw.localnet.network.Client_sl;

public class lnp 
{
	public static void plugin_loaded()
	{
		message.info("ClientDataTools插件加载");
		try 
		{
			cmdManager.add_cmd("cdt", lnp.class.getMethod("cmd", new Class[]{Client_sl.class,String.class}));
		} 
		catch (Exception e) 
		{
			message.warning("ClientDataTools插件加载错误!",e);
		}
	}
	
	public static void cmd(Client_sl User,String cmd)
	{
		if(User!=LN.local_sl) {User.sendto("抱歉，权限不足");}
	}
}
