package online.smyhw.localnet.plugins.ClientDataTools;

import java.util.Iterator;
import java.util.Map;

import online.smyhw.localnet.LN;
import online.smyhw.localnet.LNlib;
import online.smyhw.localnet.message;
import online.smyhw.localnet.command.cmdManager;
import online.smyhw.localnet.data.data;
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
		if(User!=LN.local_sl) {User.sendMsg("抱歉，权限不足");}
		if(CommandFJ.js(cmd)<4 || CommandFJ.fj(cmd, 1).equals("help"))
		{
			if(CommandFJ.js(cmd)<4) {User.sendMsg("参数不足...");}
			User.sendMsg("======ClientDataTools======");
			User.sendMsg("指令例如:cdt temp <终端ID> <下列指令> #读取Temp信息");
			User.sendMsg("指令例如:cdt bin <下列指令> #读取持久性信息");
			User.sendMsg("show #查看目标终端的所有ClientData");
			User.sendMsg("del <plugin_ID> <key> #删除指定数据");
			User.sendMsg("add <plugin_ID> <key> <value> #添加指定数据");
			return;
		}
		String type = CommandFJ.fj(cmd, 1);
		data DoMap=null;
		Client_sl DoClient = LNlib.Find_Client(CommandFJ.fj(cmd, 2));
		if(DoClient==null) 
		{User.sendMsg("终端<"+CommandFJ.fj(cmd, 2)+"不存在");return;}
		if(CommandFJ.fj(cmd, 1).equals("bin")) {DoMap = DoClient.ClientData;}
		if(CommandFJ.fj(cmd, 1).equals("temp")) {DoMap = DoClient.TempClientData;}
		if(DoMap==null) {User.sendMsg("类型只能为<bin>或<temp>,而你的输入为<"+CommandFJ.fj(cmd, 1)+">");return;}
		
		String gg = CommandFJ.fj(cmd, 3);
		switch(gg)
		{
		case "show":
		{
			Iterator iter = DoMap.GetClone().entrySet().iterator();
			User.sendMsg("终端实例<"+CommandFJ.fj(cmd, 2)+">的ClientData列表");
			while (iter.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				Object val = entry.getValue();
				User.sendMsg("KEY=<"+key+">;VALUE=<"+val.toString()+">");
			}
			break;
		}
		case "del":
		{
			if(CommandFJ.js(cmd)<6) {User.sendMsg("参数不足");return;}
			String PluginID = CommandFJ.fj(cmd, 4);
			String KEY = CommandFJ.fj(cmd, 5);
			if(CommandFJ.fj(cmd, 1).equals("bin")) 
			{
				DoClient.PutClientData(PluginID,KEY,null);
			}
			else
			{
				DoClient.PutTempClientData(PluginID,KEY,null);
			}
			User.sendMsg("成功将终端<"+CommandFJ.fj(cmd, 2)+">的ClientData<"+PluginID+"><"+KEY+">删除");
			break;
		}
		case "add":
		{
			if(CommandFJ.js(cmd)<7) {User.sendMsg("参数不足");return;}
			String PluginID = CommandFJ.fj(cmd, 4);
			String KEY = CommandFJ.fj(cmd, 5);
			String VALUE = CommandFJ.fj(cmd, 6);
			if(CommandFJ.fj(cmd, 1).equals("bin")) 
			{
				DoClient.PutClientData(PluginID,KEY,VALUE);
			}
			else
			{
				DoClient.PutTempClientData(PluginID,KEY,VALUE);
			}
			User.sendMsg("成功将终端<"+CommandFJ.fj(cmd, 2)+">的ClientData<"+PluginID+"><"+KEY+">设置为<"+VALUE+">");
			break;
		}
		default:
		{
			User.sendMsg("未知的选项<"+gg+">");
		}
		}
		return;
	}
}
