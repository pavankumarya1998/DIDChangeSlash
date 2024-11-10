package methodsDIDChange;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MFAAPIEnableDisable {
	@SuppressWarnings("deprecation")
	public static void MFAEnable(String URL) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\n    \"userName\": \"pavanAdmin\",\n    \"password\": \"Pavan^1998\",\n    \"isEnabled\": \"1\"\n}");
		Request request = new Request.Builder().url(URL + "/slashRtc/webApis/manageMfaAuthFlag").method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();

		System.out.println(response + "MFA Enabled Successfully..");

	}

	@SuppressWarnings("deprecation")
	public static void MFADisable(String URL) throws IOException {

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\n    \"userName\": \"pavanAdmin\",\n    \"password\": \"Pavan^1998\",\n    \"isEnabled\": \"0\"\n}");
		Request request = new Request.Builder().url(URL + "/slashRtc/webApis/manageMfaAuthFlag").method("POST", body)
				.addHeader("Content-Type", "application/json").build();
		Response response = client.newCall(request).execute();

		System.out.println(response + "MFA Disable Successfully..");

	}

}
