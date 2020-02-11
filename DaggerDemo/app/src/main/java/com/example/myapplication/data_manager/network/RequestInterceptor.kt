package com.example.githubtrendingrepositoriesdemo.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

class RequestInterceptor : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val defaultRequest = chain.request()
        val defaultUrl = defaultRequest.url()
        val requestBuilder = defaultRequest.newBuilder().url(defaultUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}
