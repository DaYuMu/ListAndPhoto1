package com.nlwl.listandphoto.PostRequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nlwl.listandphoto.R;
import com.nlwl.listandphoto.util.InputStreamToFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PostRequestActivity extends AppCompatActivity {

	//  用户id不为空      配件申请接口
	String url = "http://192.168.1.3:8080/erp/phone-wh-addCaiGou_phone.do?" +
			"userId=2985cbd8-008a-4a78-a753-d0827271d817&PeiJianName_phone=%E9%A3%8E%E6%A0%BC" +
			"&PeiJianModel_phone=%E5%8F%91%E8%A1%A8&PeiJianCount_phone=嘿嘿" +
			"&describe_phone=%E6%88%BF%E4%BA%A7%E5%B1%80&addflag=edit" +
			"&pjpid_phone=c089a663-9ae0-4ff3-a184-325dca24394c";


	//  用户id为空    配件申请接口
	String urlurl = "http://192.168.1.3:8080/erp/phone-wh-addCaiGou_phone.do?" +
			"userId=&PeiJianName_phone=%E9%A3%8E%E6%A0%BC" +
			"&PeiJianModel_phone=%E5%8F%91%E8%A1%A8&PeiJianCount_phone=嘿嘿" +
			"&describe_phone=%E6%88%BF%E4%BA%A7%E5%B1%80&addflag=edit" +
			"&pjpid_phone=c089a663-9ae0-4ff3-a184-325dca24394c";


	//   保养工单中物业确认接口
	String keepUrl =
			"http://192.168.1.3:8080/erp/phone-kwk-qrWorkOrder" +
					".do?appKwkId=6572cb5c-3000-4980-aa05-638f18ecd296userId=2985cbd8-008a-4a78-a753-d0827271d817&wuye_password=&remark" +
					"=&appKeepItems=bde2d1aa-6a97-40d7-b139-a2b39df4aa62," +
					"5d032582-3846-4e16-909e-eceeb08ca727,352f1ac4-b0e6-4ff0-9980-1f81da8a218b," +
					"6453d9fc-3a1b-4451-86dd-11273687d55f,516c7af4-a34d-4227-8433-d80e79e312c8," +
					"743129e9-8eed-4c93-a60e-56bae5f3e81d,9946fcda-1987-493c-af57-091b545776a2," +
					"d392ea7d-24fb-4103-90a0-cb9b48b63fe2,5d85bb70-d3a7-403b-9c5e-b0be90b500da," +
					"3cb32c48-387c-4354-9a36-9900c8be6f52," +
					"bfee52ea-9ada-48a7-82eb-5627ba459277&gz_describe=%E9%97%A8%E9%97%AD%E5%90%88" +
					"%E4%B8%8D%E7%B4%A7%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-&gz_jiejuefangan" +
					"=%E8%B0%83%E6%95%B4%E9%97%A8%E9%97%B4%E9%9A%99%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C" +
					"-%2C-%2C-&gz_status=2,0,0,0,0,0,0,0,0,0," +
					"0&gz_project=ddae3911-ed5f-4c51-bcba-5aa23d7e1dfe,-,-,-,-,-,-,-,-,-," +
					"-&gz_class=ea4af254-de61-41b0-9fea-548dfab12a2b,-,-,-,-,-,-,-,-,-," +
					"-&gzreasonid=b10feb0c-a18f-4422-b0cf-0867acc9603b,-,-,-,-,-,-,-,-,-," +
					"-&ele_runTime=565&ele_runCount=54545";

	String keepUrlUrl = "http://192.168.1.3:8080/erp/phone-kwk-qrWorkOrder" +
			".do?userId=2985cbd8-008a-4a78-a753-d0827271d817&appKwkId=6ce41cf4-c478-4ade-942b" +
			"-035185aa9df0&wuye_password=&remark=&appKeepItems=73c5a4e2-909d-4f9f-8a06" +
			"-5bebc393d1d2,fe561177-9e75-4246-9691-cbfd815aaf52," +
			"bc2f6f6c-cee1-4d0f-8e5d-52bce8a0f55b,8f15ee08-2f19-4c4c-b9de-a10611c91518," +
			"92ed4b10-88df-4b4b-84b6-30ead32ee400,e1612d99-d5bc-4108-9668-94b3b4251a98," +
			"a70c6513-33e5-43f5-8bb1-18df61b347e2,90a0d1e9-8d6f-4bc6-8a06-6e0279e361f8," +
			"5e52c354-f8df-4227-bca1-cba30de73670,a022532f-3d85-4201-910a-03e43b14f298," +
			"f9a067d3-80a4-45bd-be46-eca65b1a7227,023fefbd-3788-4001-ab5c-555299777c96," +
			"0ce6455f-6cf9-4781-a0ef-27b567046689,42d3e96a-c28e-4cd2-8e0e-fc64632fe878," +
			"60403011-f267-4778-8839-0439243aaa71,052f072e-8e12-4088-a611-39b5549ffb3a," +
			"d82fd5ab-7286-4133-9196-d9a0c64ac083,9419f22b-bf24-48d2-9f69-cf2b1dcc66d5," +
			"132bf642-335a-4daf-9040-23ee00b6aaf5,c8bd9208-5989-4efb-a434-6fee85cc9b91," +
			"38dfcd51-6da0-45ba-8125-d0f39d810477,312ad53e-4899-42db-a6cc-303172c08de2," +
			"bd056c59-efbf-43bc-b21f-815552d0fe99,bd503df6-b64e-4aa9-b1ba-644f84cf51f8," +
			"42f16748-88f4-45ef-bb87-6afe12687bdd,99c4ebc1-53ce-4d53-be11-f216c9345612," +
			"75c95ac8-8ecb-4d11-b986-e94c81bbeab9,faa280ab-c042-424c-9ebf-8bed1b9bee81," +
			"47d2c763-0492-4fd6-8543-fd7484b2ee20,d4decac8-42d8-49bf-8379-1d74d2896e0e," +
			"e65f6d21-737e-4aec-9110-6f7539adb457&gz_describe=-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C" +
			"-%2C" +
			"-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C" +
			"-%2C" +
			"-&gz_jiejuefangan=-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C" +
			"-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-%2C-&gz_status=0,0,0,0,0,0,0," +
			"0," +
			"0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0&gz_project=-,-,-,-,-,-,-,-,-,-,-,-,-," +
			"-," +
			"-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-&gz_class=-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-," +
			"-," +
			"-,-,-,-,-,-,-,-,-,-&gzreasonid=-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-,-," +
			"-," +
			"-,-,-,-&ele_runTime=656&ele_runCount=65656";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_request);

		PostRequest();

	}


	RequestParams params;//  android-async-http-1.4.9.jar

	private void PostRequest() {

		/*存放图片名称和图片*/
		params = new RequestParams();


		InputStream fs = getResources().openRawResource(R.raw.jsp);

		InputStream fs1 = getResources().openRawResource(R.raw.docx);
		InputStream fs2 = getResources().openRawResource(R.raw.html);
		InputStream fs3 = getResources().openRawResource(R.raw.pptx);
		InputStream fs4 = getResources().openRawResource(R.raw.txt);

		Log.e("inputStream", fs4.toString());

		InputStreamToFile inputStreamToFile = new InputStreamToFile();

		File file = inputStreamToFile.createFileWithByte(fs4);
		Log.e("file.length", file.length()+"");

		try {
			params.put("listFile", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		params.put("listFileName",file.toString());


				/*进行网络的请求*/
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(keepUrlUrl, params, new AsyncHttpResponseHandler
				() {
			@Override
			public void onSuccess(int i, cz.msebera.android.httpclient.Header[]
					headers,byte[] bytes) {

				Log.e("compressImage", params.toString());
				Log.e("success_bytes",bytes.toString()+"---");
				if (bytes != null) {
					String str = new String(bytes);
					try {
						JSONObject json = new JSONObject(str);
						Log.e("json", json.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int i, cz.msebera.android.httpclient.Header[]
					headers,
								  byte[] bytes,
								  Throwable throwable) {
				Toast.makeText(PostRequestActivity.this, "请求失败", Toast
						.LENGTH_SHORT)
						.show();
			}
		});


	}
}
