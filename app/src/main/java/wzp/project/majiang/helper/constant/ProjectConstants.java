package wzp.project.majiang.helper.constant;

public interface ProjectConstants {

	String EXTRA_DEVICE_ADDRESS = "device_address";

	String CIPHER = "#*#*59#"; // 使用蓝牙功能的暗号
	
	int DATA_LENGTH = 41; // 报文长度
	int CRC_HIGH = DATA_LENGTH - 2; // CRC校验高位
	int CRC_LOW = DATA_LENGTH - 1; // CRC校验低位

	int SEND_MSG_LENGTH = 41;

	int MAX_MAJIANG_NUM = 36;
}
