/*
 * ********************************************************************************
 * COPYRIGHT
 *               PAX TECHNOLOGY, Inc. PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or
 *   nondisclosure agreement with PAX  Technology, Inc. and may not be copied
 *   or disclosed except in accordance with the terms in that agreement.
 *
 *      Copyright (C) 2017 PAX Technology, Inc. All rights reserved.
 * ********************************************************************************
 */
package com.pax.market.api.sdk.java.api.terminalGroupApk;

import com.google.gson.Gson;
import com.pax.market.api.sdk.java.api.BaseThirdPartySysApi;
import com.pax.market.api.sdk.java.api.base.dto.EmptyResponse;
import com.pax.market.api.sdk.java.api.base.dto.Result;
import com.pax.market.api.sdk.java.api.base.request.SdkRequest;
import com.pax.market.api.sdk.java.api.client.ThirdPartySysApiClient;
import com.pax.market.api.sdk.java.api.constant.Constants;
import com.pax.market.api.sdk.java.api.terminalGroupApk.dto.CreateTerminalGroupApkRequest;
import com.pax.market.api.sdk.java.api.terminalGroupApk.dto.SimpleTerminalGroupApkDTO;
import com.pax.market.api.sdk.java.api.terminalGroupApk.dto.TerminalGroupApkResponse;
import com.pax.market.api.sdk.java.api.util.EnhancedJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

/**
 * @Description
 * @Author: Shawn
 * @Date: 2019/11/29 11:05
 * @Version 1.0
 */
public class TerminalGroupApkApi extends BaseThirdPartySysApi {
    private static final Logger logger = LoggerFactory.getLogger(TerminalGroupApkApi.class);

    public TerminalGroupApkApi(String baseUrl, String apiKey, String apiSecret) {
        super(baseUrl, apiKey, apiSecret);
    }

    public TerminalGroupApkApi(String baseUrl, String apiKey, String apiSecret, Locale locale) {
        super(baseUrl, apiKey, apiSecret, locale);
    }

    private static final String GET_TERMINAL_GROUP_APK_URL = "/v1/3rdsys/terminalGroupApks/{groupApkId}";
    private static final String CREATE_TERMINAL_GROUP_APK_URL = "/v1/3rdsys/terminalGroupApks";
    private static final String SUSPEND_TERMINAL_GROUP_APK_URL = "/v1/3rdsys/terminalGroupApks/{groupApkId}/suspend";
    private static final String DELETE_TERMINAL_GROUP_APK_URL = "/v1/3rdsys/terminalGroupApks/{groupApkId}";

    public Result<SimpleTerminalGroupApkDTO> getTerminalGroupApk(Long groupApkId){
        validateTerminalGroupApkId(groupApkId);
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(GET_TERMINAL_GROUP_APK_URL.replace("{groupApkId}", groupApkId.toString()+""));
        request.setRequestMethod(SdkRequest.RequestMethod.GET);
        TerminalGroupApkResponse resp = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupApkResponse.class);
        Result<SimpleTerminalGroupApkDTO> result = new Result<SimpleTerminalGroupApkDTO>(resp);
        return result;
    }

    public Result<SimpleTerminalGroupApkDTO> createAndActiveGroupApk(CreateTerminalGroupApkRequest createRequest){
        List<String> validationErrs = validateCreate(createRequest, "parameter.terminalGroupApkCreateRequest.null");
        if(validationErrs.size()>0) {
            return new Result<SimpleTerminalGroupApkDTO>(validationErrs);
        }
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(CREATE_TERMINAL_GROUP_APK_URL);
        request.setRequestMethod(SdkRequest.RequestMethod.POST);
        request.addHeader(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        request.setRequestBody(new Gson().toJson(createRequest, CreateTerminalGroupApkRequest.class));
        TerminalGroupApkResponse terminalGroupApkResponse = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupApkResponse.class);
        Result<SimpleTerminalGroupApkDTO> result = new Result<SimpleTerminalGroupApkDTO>(terminalGroupApkResponse);
        return result;
    }


    public Result<SimpleTerminalGroupApkDTO> suspendTerminalGroupApk(Long groupApkId){
        validateTerminalGroupApkId(groupApkId);
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(SUSPEND_TERMINAL_GROUP_APK_URL.replace("{groupApkId}", groupApkId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.POST);

        TerminalGroupApkResponse resp = EnhancedJsonUtils.fromJson(client.execute(request), TerminalGroupApkResponse.class);
        Result<SimpleTerminalGroupApkDTO> result = new Result<SimpleTerminalGroupApkDTO>(resp);
        return result;
    }

    public Result<String> deleteTerminalGroupApk(Long groupApkId){
        validateTerminalGroupApkId(groupApkId);
        ThirdPartySysApiClient client = new ThirdPartySysApiClient(getBaseUrl(), getApiKey(), getApiSecret());
        SdkRequest request = createSdkRequest(DELETE_TERMINAL_GROUP_APK_URL.replace("{groupApkId}", groupApkId.toString()));
        request.setRequestMethod(SdkRequest.RequestMethod.DELETE);
        EmptyResponse emptyResponse =  EnhancedJsonUtils.fromJson(client.execute(request), EmptyResponse.class);
        return  new Result<String>(emptyResponse);
    }


    private  Result<SimpleTerminalGroupApkDTO> validateTerminalGroupApkId(Long groupApkId) {
        logger.debug("groupApkId="+groupApkId);
        List<String> validationErrs = validateId(groupApkId, "parameter.terminalGroupApkId.invalid");
        if(validationErrs.size()>0) {
            return new Result<SimpleTerminalGroupApkDTO>(validationErrs);
        }else return null;
    }


}
