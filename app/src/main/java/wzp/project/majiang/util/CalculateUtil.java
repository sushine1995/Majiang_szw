package wzp.project.majiang.util;

import wzp.project.majiang.R;
import wzp.project.majiang.constant.ProjectConstants;

public class CalculateUtil {

	/**
	 * 对浮点数进行保留多少位小数的操作，并对末位进行四舍五入操作
	 * @param decimal 需要进行格式化操作的浮点数
	 * @param num 保留小数的位数
	 * @return
	 */
	public static double formatDecimal(double decimal, int num) {
		if (num < 0) {
			throw new IllegalArgumentException("num必须为非负整数");
		}
		double pow = Math.pow(10, num);
		return (Math.round(decimal * pow) / pow);
	}
	
	/**
	 * byte类型转换为无符号整型数
	 * 
	 * @param bt
	 * @return
	 */
	public static int byteToInt(byte bt) {
		return bt & 0xff;
	}

	/**
	 * 根据麻将编码获取对应图片的id号
	 *
	 * @param majiangCode
	 * @return
	 */
	public static int getMajiangImage(int majiangCode) {
		int imageId = R.drawable.unknown;

		switch (majiangCode) {
			// 一条到九条
			case 0x04:
			case 0x05:
			case 0x06:
			case 0x07:
				imageId = R.drawable.tiao1;
				break;

			case 0x08:
			case 0x09:
			case 0x0a:
			case 0x0b:
				imageId = R.drawable.tiao2;
				break;

			case 0x0c:
			case 0x0d:
			case 0x0e:
			case 0x0f:
				imageId = R.drawable.tiao3;
				break;

			case 0x10:
			case 0x11:
			case 0x12:
			case 0x13:
				imageId = R.drawable.tiao4;
				break;

			case 0x14:
			case 0x15:
			case 0x16:
			case 0x17:
				imageId = R.drawable.tiao5;
				break;

			case 0x18:
			case 0x19:
			case 0x1a:
			case 0x1b:
				imageId = R.drawable.tiao6;
				break;

			case 0x1c:
			case 0x1d:
			case 0x1e:
			case 0x1f:
				imageId = R.drawable.tiao7;
				break;

			case 0x20:
			case 0x21:
			case 0x22:
			case 0x23:
				imageId = R.drawable.tiao8;
				break;

			case 0x24:
			case 0x25:
			case 0x26:
			case 0x27:
				imageId = R.drawable.tiao9;
				break;

			// 一饼到九饼
			case 0x2c:
			case 0x2d:
			case 0x2e:
			case 0x2f:
				imageId = R.drawable.bing1;
				break;

			case 0x30:
			case 0x31:
			case 0x32:
			case 0x33:
				imageId = R.drawable.bing2;
				break;

			case 0x34:
			case 0x35:
			case 0x36:
			case 0x37:
				imageId = R.drawable.bing3;
				break;

			case 0x38:
			case 0x39:
			case 0x3a:
			case 0x3b:
				imageId = R.drawable.bing4;
				break;

			case 0x3c:
			case 0x3d:
			case 0x3e:
			case 0x3f:
				imageId = R.drawable.bing5;
				break;

			case 0x40:
			case 0x41:
			case 0x42:
			case 0x43:
				imageId = R.drawable.bing6;
				break;

			case 0x44:
			case 0x45:
			case 0x46:
			case 0x47:
				imageId = R.drawable.bing7;
				break;

			case 0x48:
			case 0x49:
			case 0x4a:
			case 0x4b:
				imageId = R.drawable.bing8;
				break;

			case 0x4c:
			case 0x4d:
			case 0x4e:
			case 0x4f:
				imageId = R.drawable.bing9;
				break;

			// 一万到四万
			case 0x54:
			case 0x55:
			case 0x56:
			case 0x57:
				imageId = R.drawable.wan1;
				break;

			case 0x58:
			case 0x59:
			case 0x5a:
			case 0x5b:
				imageId = R.drawable.wan2;
				break;

			case 0x5c:
			case 0x5d:
			case 0x5e:
			case 0x5f:
				imageId = R.drawable.wan3;
				break;

			case 0x60:
			case 0x61:
			case 0x62:
			case 0x63:
				imageId = R.drawable.wan4;
				break;

			case 0x64:
			case 0x65:
			case 0x66:
			case 0x67:
				imageId = R.drawable.wan5;
				break;

			case 0x68:
			case 0x69:
			case 0x6a:
			case 0x6b:
				imageId = R.drawable.wan6;
				break;

			case 0x6c:
			case 0x6d:
			case 0x6e:
			case 0x6f:
				imageId = R.drawable.wan7;
				break;

			case 0x70:
			case 0x71:
			case 0x72:
			case 0x73:
				imageId = R.drawable.wan8;
				break;

			case 0x74:
			case 0x75:
			case 0x76:
			case 0x77:
				imageId = R.drawable.wan9;
				break;

			// 东南西北中发白
			case 0x7c:
			case 0x7d:
			case 0x7e:
			case 0x7f:
				imageId = R.drawable.dongfeng;
				break;

			case 0x80:
			case 0x81:
			case 0x82:
			case 0x83:
				imageId = R.drawable.nanfeng;
				break;

			case 0x84:
			case 0x85:
			case 0x86:
			case 0x87:
				imageId = R.drawable.xifeng;
				break;

			case 0x88:
			case 0x89:
			case 0x8a:
			case 0x8b:
				imageId = R.drawable.beifeng;
				break;

			case 0x8c:
			case 0x8d:
			case 0x8e:
			case 0x8f:
				imageId = R.drawable.zhong;
				break;

			case 0x90:
			case 0x91:
			case 0x92:
			case 0x93:
				imageId = R.drawable.fa;
				break;

			case 0x94:
			case 0x95:
			case 0x96:
			case 0x97:
				imageId = R.drawable.bai;
				break;

			// 春夏秋冬 梅兰菊竹
			case 0x98:
				imageId = R.drawable.chun;
				break;
			case 0x99:
				imageId = R.drawable.xia;
				break;
			case 0x9a:
				imageId = R.drawable.qiu;
				break;
			case 0x9b:
				imageId = R.drawable.dong;
				break;
			case 0x9c:
				imageId = R.drawable.mei;
				break;
			case 0x9d:
				imageId = R.drawable.lan;
				break;
			case 0x9e:
				imageId = R.drawable.ju;
				break;
			case 0x9f:
				imageId = R.drawable.zhu;
				break;

			case 0x00:
				imageId = -1;
				break;
		}

		return  imageId;
	}

	public static void analyseMessage(byte[] msgArr) {
		for (int i = 1; i < msgArr.length - 2; i = i + 2) {
			msgArr[i] = (byte) (msgArr[i] ^ ProjectConstants.SECRET_ARR[byteToInt(msgArr[i - 1])] ^ msgArr[i - 1]);
		}
	}
}
