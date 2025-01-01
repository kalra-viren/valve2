package com.example.valve.Request_flow;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//public class MultipartRequest extends Request<NetworkResponse> {
//
//    private final Response.Listener<NetworkResponse> mListener;
//    private final Map<String, File> mFilePart;
//    private final Map<String, String> mStringPart;
//
//    public MultipartRequest(String url, Response.ErrorListener errorListener,
//                            Response.Listener<NetworkResponse> listener, Map<String, File> filePart, Map<String, String> stringPart) {
//        super(Method.POST, url, errorListener);
//        this.mListener = listener;
//        this.mFilePart = filePart;
//        this.mStringPart = stringPart;
//    }
//
//    @Override
//    public Map<String, String> getHeaders() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "multipart/form-data;boundary=" + "boundary");
//        return headers;
//    }
//
//    @Override
//    public byte[] getBody() {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            for (Map.Entry<String, File> entry : mFilePart.entrySet()) {
//                writeFilePart(bos, entry.getKey(), entry.getValue());
//            }
//            for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
//                writeStringPart(bos, entry.getKey(), entry.getValue());
//            }
//            return bos.toByteArray();
//        } catch (IOException e) {
//            // Handle exception (e.g., log it)
//            return null; // or handle it as appropriate for your application
//        }
//    }
//
//    private void writeFilePart(ByteArrayOutputStream bos, String paramName, File file) throws IOException {
//        String header = "--boundary\r\nContent-Disposition: form-data; name=\"" + paramName + "\"; filename=\""
//                + file.getName() + "\"\r\n\r\n";
//        bos.write(header.getBytes());
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//            bos.write(buffer, 0, bytesRead);
//        }
//        bos.write("\r\n".getBytes());
//        fileInputStream.close();
//    }
//
//    private void writeStringPart(ByteArrayOutputStream bos, String paramName, String value) throws IOException {
//        String header = "--boundary\r\nContent-Disposition: form-data; name=\"" + paramName + "\"\r\n\r\n";
//        bos.write(header.getBytes());
//        bos.write(value.getBytes());
//        bos.write("\r\n".getBytes());
//    }
//
//    @Override
//    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
//        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
//    }
//
//    @Override
//    protected void deliverResponse(NetworkResponse response) {
//        mListener.onResponse(response);
//    }
//}


//++++++++++++++++++++++++++++++++++++++++++++++++==========================++++++++++++++++++++++++++++++++++++++++++++++++++++++





public class VolleyMultipartRequest extends Request<NetworkResponse> {


    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    private Response.Listener<NetworkResponse> mListener;
    private Response.ErrorListener mErrorListener;
    private Map<String, String> mHeaders;


    public VolleyMultipartRequest(int method, String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return (mHeaders != null) ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // populate text payload
            Map<String, String> params = getParams();
            if (params != null && params.size() > 0) {
                textParse(dos, params, getParamsEncoding());
            }

            // populate data byte payload
            Map<String, DataPart> data = getByteData();
            if (data != null && data.size() > 0) {
                dataParse(dos, data);
            }

            // close multipart form data after text and file data
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Custom method handle data payload.
     *
     * @return Map data part label with data byte
     * @throws AuthFailureError
     */
    protected Map<String, DataPart> getByteData() throws AuthFailureError {
        return null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(
                    response,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    /**
     * Parse string map into data output stream by key and value.
     *
     * @param dataOutputStream data output stream handle string parsing
     * @param params           string inputs collection
     * @param encoding         encode the inputs, default UTF-8
     * @throws IOException
     */
    private void textParse(DataOutputStream dataOutputStream, Map<String, String> params, String encoding) throws IOException {
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buildTextPart(dataOutputStream, entry.getKey(), entry.getValue());
            }
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + encoding, uee);
        }
    }

    /**
     * Parse data into data output stream.
     *
     * @param dataOutputStream data output stream handle file attachment
     * @param data             loop through data
     * @throws IOException
     */
    private void dataParse(DataOutputStream dataOutputStream, Map<String, DataPart> data) throws IOException {
        for (Map.Entry<String, DataPart> entry : data.entrySet()) {
            buildDataPart(dataOutputStream, entry.getValue(), entry.getKey());
        }
    }

    /**
     * Write string data into header and data output stream.
     *
     * @param dataOutputStream data output stream handle string parsing
     * @param parameterName    name of input
     * @param parameterValue   value of input
     * @throws IOException
     */
    private void buildTextPart(DataOutputStream dataOutputStream, String parameterName, String parameterValue) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + parameterName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(parameterValue + lineEnd);
    }

    /**
     * Write data file into header and data output stream.
     *
     * @param dataOutputStream data output stream handle data parsing
     * @param dataFile         data byte as DataPart from collection
     * @param inputName        name of data input
     * @throws IOException
     */
    private void buildDataPart(DataOutputStream dataOutputStream, DataPart dataFile, String inputName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                inputName + "\"; filename=\"" + dataFile.getFileName() + "\"" + lineEnd);
        if (dataFile.getType() != null && !dataFile.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataFile.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataFile.getContent());
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

    public static class DataPart {
        private String fileName;
        private byte[] content;
        private String type;

        public DataPart() {
        }

        public DataPart(String name, byte[] data) {
            fileName = name;
            content = data;
        }

        String getFileName() {
            return fileName;
        }

        byte[] getContent() {
            return content;
        }

        String getType() {
            return type;
        }

    }
}
