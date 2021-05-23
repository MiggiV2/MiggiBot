package de.mymiggi.r6.stats.wrapper.actions;

import java.io.IOException;

import com.google.gson.Gson;

import de.mymiggi.r6.stats.wrapper.RankedRegions;
import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import de.mymiggi.r6.stats.wrapper.entitys.rank.RankedResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRankAction
{
	public RankedResponse run(LoginResponse loginResponse, String playerID, RankedRegions region) throws IOException
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.addHeader("expiration", loginResponse.getExpiration())
			.addHeader("Ubi-AppId", "3587dcbb-7f81-457c-9781-0e3f29f6f56a")
			.addHeader("Ubi-SessionId", loginResponse.getSessionId())
			.addHeader("Authorization", "ubi_v1 t=" + loginResponse.getTicket())
			.url(String.format("https://public-ubiservices.ubi.com/v1/spaces/5172a557-50b5-4665-b7db-e3f2e8c5041d/sandboxes/OSBOR_PC_LNCH_A/r6karma/players?board_id=pvp_ranked&season_id=-1&region_id=%s&profile_ids=%s", region.getCode(), playerID))
			.build();
		Response response = client.newCall(request).execute();
		String responseJSON = response.body().string();
		return new Gson().fromJson(responseJSON, RankedResponse.class);
	}
}
