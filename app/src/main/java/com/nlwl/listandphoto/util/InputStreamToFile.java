package com.nlwl.listandphoto.util;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017-10-13.
 */

public class InputStreamToFile {


	public InputStreamToFile() {
	}

	/**
	 * 根据byte数组生成文件
	 * @param bytes
	 * 生成文件用到的byte数组
	 */
	String fileName = "jspjjj.jsp";
	public File createFileWithByte(InputStream fs) {
		// TODO Auto-generated method stub
		/**
		 * 创建File对象，其中包含文件所在的目录以及文件的命名
		 */

		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
		int rc = 0;
		try {
			while ((rc = fs.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = swapStream.toByteArray(); //in_b为转换之后的结果




		File file = new File(Environment.getExternalStorageDirectory(),
				fileName);
		// 创建FileOutputStream对象
		FileOutputStream outputStream = null;
		// 创建BufferedOutputStream对象
		BufferedOutputStream bufferedOutputStream = null;
		try {
			// 如果文件存在则删除
			if (file.exists()) {
				file.delete();
			}
			// 在文件系统中根据路径创建一个新的空文件
			file.createNewFile();
			// 获取FileOutputStream对象
			outputStream = new FileOutputStream(file);
			// 获取BufferedOutputStream对象
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			// 往文件所在的缓冲输出流中写byte数据
			bufferedOutputStream.write(bytes);
			// 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
			bufferedOutputStream.flush();
//			return file;
		} catch (Exception e) {
			// 打印异常信息
			e.printStackTrace();
		} finally {
			// 关闭创建的流对象
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return file;
	}

}
