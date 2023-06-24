package hcmuaf.edu.vn.bookingtravel.api.base.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import hcmuaf.edu.vn.bookingtravel.api.base.controller.ClientException;
import hcmuaf.edu.vn.bookingtravel.api.client.model.ResultGHN;
import hcmuaf.edu.vn.bookingtravel.api.client.model.bank.ResultBank;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BaseClient {
    protected String service;


    public BaseClient(String service) {
        this.service = service;
    }

    private String getServiceFullPath(String path) throws ClientException {
        if (null == service || service.length() == 0) {
            throw new ClientException("invalid_client", "Chưa cấu hình service path: " + getClass().getSimpleName() + ".url", "service path " + getClass().getSimpleName() + ".url" + " is not set");
        }
        return service + "/" + path;
    }

    protected <T> T get(String path, Class<T> classOfT) throws ClientException {
        String content = HttpsUtils.get(getServiceFullPath(path));
        return fromJson(content, classOfT);
    }

    protected <T> T get(String path, Class<T> classOfT, String token, String code) throws ClientException {
        String content = HttpsUtils.get(getServiceFullPath(path), token, code);
        return fromJson(content, classOfT);
    }

    protected <T> T get(String path, Class<T> classOfT, String token) throws ClientException {
        String content = HttpsUtils.get(getServiceFullPath(path), token);
        return fromJson(content, classOfT);
    }
    protected <T> T getCasso(String path, Class<T> classOfT, String authed) throws ClientException {
        String content = HttpsUtils.getCasso(getServiceFullPath(path), authed);
        return fromJson(content, classOfT);
    }
    protected <T> List<T> getList(String path, Class<T> classOfT) throws ClientException {
        String content = HttpsUtils.get(getServiceFullPath(path));
        return fromJsonArray(content, classOfT);
    }


    protected <T> List<T> getList(String path, Class<T> classOfT, String token) throws ClientException {
        String content = HttpsUtils.get(getServiceFullPath(path), token);
        return fromJsonArray(content, classOfT);
    }

    protected <T> T postCasso(String path, Class<T> classOfT, String authed, Object postData) throws ClientException {
        String body = null;
        if (null != postData) body = toJson(postData);
        String content = HttpsUtils.postCasso(getServiceFullPath(path), authed, body);
        return fromJson(content, classOfT);
    }

    protected <T> T post(String path, Object postData, Class<T> classOfT, String token) throws ClientException {
        String body = null;
        if (null != postData) body = toJson(postData);
        String content = HttpsUtils.post(getServiceFullPath(path), body, token);
        return fromJson(content, classOfT);
    }

    protected <T> T post(String path, Object postData, Class<T> classOfT, String token, String requestId) throws ClientException {
        String body = null;
        if (null != postData) body = toJson(postData);
        String content = HttpsUtils.post(getServiceFullPath(path), body, token, requestId);
        return fromJson(content, classOfT);
    }

    protected <T> List<T> postList(String path, Object postData, Class<T> classOfT) throws ClientException {
        String body = null;
        if (null != postData) body = toJson(postData);
        String content = HttpsUtils.post(getServiceFullPath(path), body);
        return fromJsonArray(content, classOfT);
    }

    protected <T> List<T> postList(String path, Object postData, Class<T> classOfT, String token) throws ClientException {
        String body = null;
        if (null != postData) body = toJson(postData);
        String content = HttpsUtils.post(getServiceFullPath(path), body, token);
        return fromJsonArray(content, classOfT);
    }

    protected <T> T fromJson(String content, Class<T> classOfT) {
        Gson gson = getGson();
        return gson.fromJson(content, classOfT);
    }

    protected <T> List<T> fromJsonArray(String content, Class<T> classOfT) {
        Gson gson = getGson();
        Type typeOfT = TypeToken.getParameterized(List.class, classOfT).getType();
        return Arrays.asList(gson.fromJson(content, typeOfT));
    }

    protected String toJson(Object object) {
        Gson gson = getGson();
        return gson.toJson(object);
    }

    protected <T> ResultGHN<T> getResponsePackList(String path, Class<T> classOfT, String token) throws ClientException {
        String content = HttpsUtils.get(this.getServiceFullPath(path), token);
        return this.fromPackList(content, classOfT);
    }

    protected <T> ResultGHN<T> fromPackList(String content, Class<T> classOfT) {
        Gson gson = getGson();
        Type typeOfT = TypeToken.getParameterized(ResultGHN.class, new Type[]{classOfT}).getType();
        return gson.fromJson(content, typeOfT);
    }

    protected <T> ResultBank<T> getResponseBankList(String path, Class<T> classOfT) throws ClientException {
        String content = HttpsUtils.get(this.getServiceFullPath(path));
        return this.fromBankList(content, classOfT);
    }

    protected <T> ResultBank<T> fromBankList(String content, Class<T> classOfT) {
        Gson gson = getGson();
        Type typeOfT = TypeToken.getParameterized(ResultBank.class, new Type[]{classOfT}).getType();
        return gson.fromJson(content, typeOfT);
    }

    protected Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
    }
}
