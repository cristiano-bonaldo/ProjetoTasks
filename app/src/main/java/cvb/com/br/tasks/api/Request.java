package cvb.com.br.tasks.api;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

import cvb.com.br.tasks.exception.InternetNotAvailableException;
import cvb.com.br.tasks.model.RequestParameter;
import cvb.com.br.tasks.model.RequestResponse;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.NetworkUtils;

public class Request {

    private Context ctx;

    public Request(Context ctx) {
        this.ctx = ctx;
    }

    public RequestResponse execute(RequestParameter parameters) throws InternetNotAvailableException {

        if (!NetworkUtils.isConnectionNetworkAvailable(ctx))
            throw new InternetNotAvailableException();

        RequestResponse resp;
        InputStream is;
        HttpURLConnection conn;

        try {
            URL url;
            if (parameters.method.equalsIgnoreCase(Constant.OPERATION_METHOD.GET))
                url = new URL(parameters.url + getQuery(parameters.params, parameters.method));
            else
                url = new URL(parameters.url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod(parameters.method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);

            if (parameters.headerParams != null) {
                Iterator it = parameters.headerParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    conn.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                    it.remove();
                }
            }

            if (!parameters.method.equalsIgnoreCase(Constant.OPERATION_METHOD.GET)) {
                String query = getQuery(parameters.params, parameters.method);
                byte[] postDataBytes = query.getBytes("UTF-8");

                int postDataBytesLength = postDataBytes.length;

                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytesLength));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);
            }

            conn.connect();
            if (conn.getResponseCode() == Constant.STATUS_CODE.SUCCESS) {
                is = conn.getInputStream();
                resp = new RequestResponse(getStringFromInputStream(is), conn.getResponseCode());
            }
            else {
                is = conn.getErrorStream();
                resp = new RequestResponse(getStringFromInputStream(is), conn.getResponseCode());
            }

            is.close();
            conn.disconnect();
        }
        catch (Exception E) {
            resp = new RequestResponse("", Constant.STATUS_CODE.NOT_FOUND);
        }

        return resp;
    }

    private String getStringFromInputStream(InputStream is) {
        if (is == null)
            return null;

        StringBuilder builder = new StringBuilder();

        String line;
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader    br  = new BufferedReader(isr);

            while ((line = br.readLine()) != null)
                builder.append(line);

            isr.close();

            br.close();
        }
        catch (Exception E) {
            return "";
        }

        return builder.toString();
    }

    private String getQuery(AbstractMap<String, String> params, String method) throws UnsupportedEncodingException {
        if (params == null)
            return "";

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> param : params.entrySet()) {
            if (first) {
                if (method.equalsIgnoreCase(Constant.OPERATION_METHOD.GET)) {
                    builder.append("?");
                }
                first = false;
            }
            else {
                builder.append("&");
            }

            builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(param.getValue(), "UTF-8"));
        }

        return builder.toString();
    }

}
