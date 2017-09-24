package wzp.project.majiang.helper.constant;

import android.os.Environment;

public interface ProjectConstants {

	String EXTRA_DEVICE_ADDRESS = "device_address";

	String CIPHER = "#*#*59#"; // 使用蓝牙功能的暗号
	
	int DATA_LENGTH = 41; // 报文长度
	int CRC_HIGH = DATA_LENGTH - 2; // CRC校验高位
	int CRC_LOW = DATA_LENGTH - 1; // CRC校验低位

	int SEND_MSG_LENGTH = 41;

	int MAX_MAJIANG_NUM = 36;


	String baseFilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Majiang";

	String QQ_FILE_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/tencent/QQfile_recv"; // QQ接收的文件默认保存的位置

	String WECHAT_FILE_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/tencent/MicroMsg/Download"; // 微信接收的文件默认保存的位置

	String lettNumbUnde = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";			// 字符(中英文)、数字、下划线
}
